package ru.kirillyarulin.DirectoriesAndFiles.repositories;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kirillyarulin.DirectoriesAndFiles.models.Directory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Kirill Yarulin on 26.07.18
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DirectoryRepositoryIT {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DirectoryRepository directoryRepository;

    private Directory testDirectory1 = new Directory("/home/test1", 3, 2, 500);
    private Directory testDirectory2 = new Directory("/home/test2", 4, 1, 0);
    private Directory testDirectory3 = new Directory("/home/test3", 5, 5, 2);

    @Before
    public void before() {
        entityManager.persist(testDirectory1);
        entityManager.persist(testDirectory2);
        entityManager.persist(testDirectory3);
    }

    @Test
    public void findById() {
        Directory receivedDirectory = directoryRepository.findById(testDirectory1.getId()).get();
        assertEquals(testDirectory1, receivedDirectory);
    }

    @Test
    public void findByPath() {
        Directory receivedDirectory = directoryRepository.findByPath(testDirectory1.getPath()).get();

        assertEquals(testDirectory1, receivedDirectory);
    }

    @Test
    public void existById() {
        assertTrue(directoryRepository.existsById(testDirectory1.getId()));
        assertFalse(directoryRepository.existsById(99L));
    }

    @Test
    public void save() {
        Directory newDirectory = new Directory("/home/new", 1, 2, 3);
        directoryRepository.save(newDirectory);
        Directory receivedDirectory = directoryRepository.findById(newDirectory.getId()).get();
        assertEquals(newDirectory, receivedDirectory);
    }

    @Test
    public void count() {
        assertEquals(3, directoryRepository.count());
    }

    @Test
    public void deleteByEntity() {
        directoryRepository.delete(testDirectory1);
        assertEquals(2, directoryRepository.count());
        assertFalse(directoryRepository.findById(testDirectory1.getId()).isPresent());
    }

    @Test
    public void deleteById() {
        directoryRepository.deleteById(testDirectory1.getId());
        assertEquals(2, directoryRepository.count());
        assertFalse(directoryRepository.findById(testDirectory1.getId()).isPresent());
    }

    @Test
    public void deleteAll() {
        directoryRepository.deleteAll();

        assertEquals(0, directoryRepository.count());
    }

    @Test
    public void findAll() {
        List<Directory> all = directoryRepository.findAll();
        assertThat(all, Matchers.containsInAnyOrder(testDirectory1, testDirectory2, testDirectory3));
        assertEquals(3, all.size());
    }


}