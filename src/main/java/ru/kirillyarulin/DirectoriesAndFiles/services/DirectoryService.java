package ru.kirillyarulin.DirectoriesAndFiles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kirillyarulin.DirectoriesAndFiles.models.Directory;
import ru.kirillyarulin.DirectoriesAndFiles.models.InternalFile;
import ru.kirillyarulin.DirectoriesAndFiles.repositories.DirectoryRepository;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Kirill Yarulin on 26.07.18
 */
@Service
public class DirectoryService {

    @Autowired
    private DirectoryRepository directoryRepository;

    public List<Directory> getAllDirectories() {
        return directoryRepository.findAll();
    }

    public Directory addDirectory(String directoryPath) {
        return directoryRepository.save(getDirectoryByPath(directoryPath));
    }

    public void deleteDirectoryById(long directoryId) {
        directoryRepository.deleteById(directoryId);
    }

    public Directory getDirectoryById(long directoryId) {
        return directoryRepository.findById(directoryId)
                .orElseThrow(() -> new IllegalArgumentException("Directory with id = " + directoryId + " does not exist"));
    }

    public Directory getDirectoryByPath(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("There is no \"" + directoryPath + "\" directory");
        }

        Optional<Directory> directoryOptional = directoryRepository.findByPath(directoryPath);
        if (directoryOptional.isPresent()) {
            return directoryOptional.get();
        }

        FileVisitorImlp fileVisitor = new FileVisitorImlp();
        try {
            Files.walkFileTree(path,fileVisitor);

            Directory directory = new Directory();
            directory.setPath(directoryPath);
            directory.setNumberOfFiles(fileVisitor.numberOfFiles);
            directory.setNumberOfSubdirectories(fileVisitor.numberOfSubdirectories);
            directory.setTotalSizeOfFiles(fileVisitor.totalSizeOfFiles);

            directory.setInternalFiles(Files.list(path)
                    .map(p -> new InternalFile(p.getFileName().toString(),Files.isDirectory(p),directory,p.toFile().length()))
                    .sorted(new InternalFileComparator())
                    .collect(Collectors.toList())
            );

            return directory;
        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while retrieving the directory", e);
        }
    }

    class FileVisitorImlp implements FileVisitor<Path> {

        private long numberOfFiles = 0;
        private long numberOfSubdirectories = -1; //take into account the start catalog
        private long  totalSizeOfFiles = 0;

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            numberOfSubdirectories++;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (Files.isRegularFile(file)) {
                numberOfFiles++;
                try {
                    totalSizeOfFiles += Files.size(file);
                } catch (IOException ignored) {
                }
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.SKIP_SUBTREE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

    public static class InternalFileComparator implements Comparator<InternalFile> {

        @Override
        public int compare(InternalFile f1, InternalFile f2) {
            f1.setName(f1.getName().toLowerCase());
            f2.setName(f2.getName().toLowerCase());
            if (!f1.isDirectory() && f2.isDirectory()) {
                return 1;
            } else if (f1.isDirectory() && !f2.isDirectory()) {
                return -1;
            } if (f1.getName().matches(".*\\d.*") && f1.getName().matches(".*\\d.*")) {
                char[] name1 = f1.getName().toCharArray();
                char[] name2 = f2.getName().toCharArray();
                int maxSize = name1.length > name2.length ? name1.length : name2.length;
                for (int i = 0; i < maxSize; i++) {
                    if (i == name1.length) {
                        return -1;
                    }
                    if (i == name2.length) {
                        return 1;
                    }
                    char c1 = name1[i];
                    char c2 = name2[i];

                    if (Character.isDigit(c1) && Character.isDigit(c2)) {
                        StringBuilder sb1 = new StringBuilder(Character.toString(c1));
                        StringBuilder sb2 = new StringBuilder(Character.toString(c2));
                        int k1 = i;
                        int k2 = i;

                        while (k1+1 < name1.length && Character.isDigit(name1[k1+1])) {
                            sb1.append(Character.toString(name1[k1+1]));
                            k1++;
                        }

                        while (k2+1 < name2.length && Character.isDigit(name2[k2+1])) {
                            sb2.append(Character.toString(name2[k2+1]));
                            k2++;
                        }

                        int res1 = Integer.parseInt(sb1.toString());
                        int res2 = Integer.parseInt(sb2.toString());
                        if (res1 != res2) {
                            return Integer.compare(res1,res2);
                        } else {
                            i = k1;
                        }

                    } else if (!(c1 == c2)) {
                        return Character.compare(c1,c2);
                    }
                }
            }
            return f1.getName().compareTo(f2.getName());
        }
    }
}
