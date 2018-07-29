package ru.kirillyarulin.DirectoriesAndFiles.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kirillyarulin.DirectoriesAndFiles.models.Directory;
import ru.kirillyarulin.DirectoriesAndFiles.repositories.DirectoryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Kirill Yarulin on 27.07.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DirectoryServiceTest {

    static class DirectoryServiceTestContextConfiguration {
        @Bean
        public DirectoryService directoryService() {
            return new DirectoryService();
        }
    }

    @MockBean
    private DirectoryRepository directoryRepository;
    @Autowired
    private DirectoryService directoryService;

    @Test
    public void getAllDirectories() {
        directoryService.getAllDirectories();

        verify(directoryRepository).findAll();
    }

    @Test
    public void addDirectory() {
        try {
            Path tempDir = Files.createTempDirectory("tempDir");
            Directory directory = new Directory(tempDir.toString(), 0, 0, 0);

            directoryService.addDirectory(tempDir.toString());

            verify(directoryRepository).save(directory);
        } catch (IOException e) {
            fail("Could not create temporary directory");
        }
    }

    @Test
    public void deleteDirectoryById() {
        directoryService.deleteDirectoryById(1);
        verify(directoryRepository).deleteById(1L);
    }

    @Test
    public void getDirectoryByExistingId() {
        Directory tmpDir = new Directory();
        tmpDir.setId(1);
        when(directoryRepository.findById(1L)).thenReturn(Optional.of(tmpDir));

        assertEquals(1,  directoryService.getDirectoryById(1).getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDirectoryByNonexistentId() {
        directoryService.getDirectoryById(2);
    }

    @Test
    public void getDirectoryByPath() {
        try {
            Path tempDir = Files.createTempDirectory("tempDir");
            Path tempSubdir = Files.createTempDirectory(tempDir, "tempSubdir");
            Files.createTempFile(tempDir, "tempFile", "");
            Files.createTempFile(tempDir, "tempFile", "");
            Files.createTempDirectory(tempSubdir, "tempSubdir");
            Files.createTempFile(tempSubdir, "tempFile", "");


            Directory directoryByPath = directoryService.getDirectoryByPath(tempDir.toString());

            assertEquals(tempDir.toString(), directoryByPath.getPath());
            assertEquals(3, directoryByPath.getNumberOfFiles());
            assertEquals(2, directoryByPath.getNumberOfSubdirectories());
            assertEquals(3,directoryByPath.getInternalFiles().size());

        } catch (IOException e) {
            fail("Could not create temporary directory");
        }
    }
}