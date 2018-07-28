package ru.kirillyarulin.DirectoriesAndFiles.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kirillyarulin.DirectoriesAndFiles.models.Directory;

import java.util.List;
import java.util.Optional;

/**
 * Created by Kirill Yarulin on 26.07.18
 */
@Repository
public interface DirectoryRepository extends CrudRepository<Directory,Long> {
    Optional<Directory> findByPath(String path);
    List<Directory> findAll();
}
