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
import ru.kirillyarulin.DirectoriesAndFiles.models.InternalFile;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Kirill Yarulin on 26.07.18
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class InternalFileRepositoryIT {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private InternalFileRepository internalFileRepository;

    private Directory testDirectory1 = new Directory("/home/test1", 3, 2, 500);
    private Directory testDirectory2 = new Directory("/home/test2", 3, 2, 500);
    private InternalFile testFile1 = new InternalFile("file1",false,testDirectory1,100);
    private InternalFile testFile2 = new InternalFile("file2",false,testDirectory1,100);
    private InternalFile testFile3 = new InternalFile("file3",true,testDirectory1);
    private InternalFile testFile4 = new InternalFile("file4",false,testDirectory2,666);

    @Before
    public void before() {
        entityManager.persist(testDirectory1);
        entityManager.persist(testDirectory2);
        entityManager.persist(testFile1);
        entityManager.persist(testFile2);
        entityManager.persist(testFile3);
        entityManager.persist(testFile4);
    }

    @Test
    public void findAllByParentDirectory() {
        List<InternalFile> allByParentDirectory = internalFileRepository.findAllByParentDirectory(testDirectory1);

        assertEquals(3,allByParentDirectory.size());
        assertTrue(allByParentDirectory.contains(testFile1));
        assertTrue(allByParentDirectory.contains(testFile2));
        assertTrue(allByParentDirectory.contains(testFile3));
        assertFalse(allByParentDirectory.contains(testFile4));
    }

    @Test
    public void findAllByParentDirectory_Id() {
        List<InternalFile> allByParentDirectory = internalFileRepository.findAllByParentDirectory_Id(testDirectory1.getId());

        assertEquals(3,allByParentDirectory.size());
        assertTrue(allByParentDirectory.contains(testFile1));
        assertTrue(allByParentDirectory.contains(testFile2));
        assertTrue(allByParentDirectory.contains(testFile3));
        assertFalse(allByParentDirectory.contains(testFile4));
    }

    @Test
    public void findById() {
        InternalFile receivedFile = internalFileRepository.findById(testFile1.getId()).get();

        assertEquals(testFile1,receivedFile);
    }

    @Test
    public void existById() {
        assertTrue(internalFileRepository.existsById(testFile1.getId()));
        assertFalse(internalFileRepository.existsById(99L));
    }

    @Test
    public void save() {
        InternalFile newFile = new InternalFile("newFile",false,testDirectory2,333);
        internalFileRepository.save(newFile);
        InternalFile receivedFile = internalFileRepository.findById(newFile.getId()).get();

        assertEquals(newFile, receivedFile);
    }

    @Test
    public void count() {
        assertEquals(4, internalFileRepository.count());
    }

    @Test
    public void deleteByEntity() {
        internalFileRepository.delete(testFile1);

        assertEquals(3,internalFileRepository.count());
        assertFalse(internalFileRepository.findById(testFile1.getId()).isPresent());
    }

    @Test
    public void deleteById() {
        internalFileRepository.deleteById(testFile1.getId());

        assertEquals(3,internalFileRepository.count());
        assertFalse(internalFileRepository.findById(testFile1.getId()).isPresent());
    }

    @Test
    public void deleteAll() {
        internalFileRepository.deleteAll();

        assertEquals(0, internalFileRepository.count());
    }

    @Test
    public void findAll() {
        Iterable<InternalFile> all = internalFileRepository.findAll();

        assertThat(all, Matchers.containsInAnyOrder(testFile1,testFile2,testFile3, testFile4));
    }
}