package ru.kirillyarulin.DirectoriesAndFiles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kirillyarulin.DirectoriesAndFiles.models.Directory;
import ru.kirillyarulin.DirectoriesAndFiles.repositories.DirectoryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public void addDirectory(String directoryPath) {
        directoryRepository.save(getDirectoryByPath(directoryPath));
    }

    public void deleteDirectory(Directory directory) {
        directoryRepository.delete(directory);
    }

    public long sizeOfFiles(List<Path> files) {
        return files.stream().mapToLong(x -> {
            try {
                return Files.size(x);
            } catch (IOException e) {
                return 0;
            }
        }).sum();
    }

    public Directory getDirectoryByPath(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("This directory does not exist");
        }

        try {
            // get a map containing 2 lists: with the key "false" - list of nested files,
            // with the key "true" - the list of subdirectories
            Map<Boolean, List<Path>> map = Files.walk(path)
                    .filter(x -> !x.equals(path))
                    .collect(Collectors.partitioningBy(Files::isDirectory));

            Directory directory = new Directory();
            directory.setPath(directoryPath);
            directory.setNumberOfFiles(map.get(false).size());
            directory.setNumberOfSubdirectories(map.get(true).size());
            directory.setTotalSizeOfFiles(sizeOfFiles(map.get(false)));

            return directory;

        } catch (IOException e) {
            throw new IllegalArgumentException("An error occurred while retrieving the directory", e);
        }
    }

}
