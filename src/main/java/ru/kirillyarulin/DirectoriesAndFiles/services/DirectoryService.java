package ru.kirillyarulin.DirectoriesAndFiles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kirillyarulin.DirectoriesAndFiles.models.Directory;
import ru.kirillyarulin.DirectoriesAndFiles.repositories.DirectoryRepository;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
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

    public void deleteDirectory(Directory directory) {
        directoryRepository.delete(directory);
    }


    public Directory getDirectoryByPath(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("There is no \"" + directoryPath + "\" directory");
        }

        FileVisitorImlp fileVisitor = new FileVisitorImlp();
        try {
            Files.walkFileTree(path,fileVisitor);

            Directory directory = new Directory();
            directory.setPath(directoryPath);
            directory.setNumberOfFiles(fileVisitor.numberOfFiles);
            directory.setNumberOfSubdirectories(fileVisitor.numberOfSubdirectories);
            directory.setTotalSizeOfFiles(fileVisitor.totalSizeOfFiles);

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
}
