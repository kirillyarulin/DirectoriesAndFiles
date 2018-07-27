package ru.kirillyarulin.DirectoriesAndFiles.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kirillyarulin.DirectoriesAndFiles.models.Directory;
import ru.kirillyarulin.DirectoriesAndFiles.repositories.InternalFileRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by Kirill Yarulin on 27.07.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InternalFileServiceTest {

    @TestConfiguration
    static class InternalFileServiceTestContextConfiguration {
        @Bean
        public InternalFileService internalFileService() {
            return new InternalFileService();
        }
    }
    @Autowired
    private InternalFileService internalFileService;
    @MockBean
    private InternalFileRepository internalFileRepository;

    @Test(expected = IllegalArgumentException.class)
    public void findAllByParentDirectoryWithoutId() {
        internalFileService.findAllByParentDirectory(new Directory());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllByParentDirectoryWithNull() {
        internalFileService.findAllByParentDirectory(null);
    }

    @Test
    public void findAllByParentDirectory() {
        Directory directory = new Directory();
        directory.setId(1);
        internalFileService.findAllByParentDirectory(directory);

        verify(internalFileRepository).findAllByParentDirectory_Id(directory.getId());
    }

    @Test
    public void findAllByParentDirectoryId() {
        internalFileService.findAllByParentDirectory(1);

        verify(internalFileRepository).findAllByParentDirectory_Id(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllByParentDirectoryIdWithWrongId() {
        internalFileService.findAllByParentDirectory(0);
    }
}