package ru.kirillyarulin.DirectoriesAndFiles.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kirillyarulin.DirectoriesAndFiles.models.Directory;
import ru.kirillyarulin.DirectoriesAndFiles.models.InternalFile;

import java.util.List;

/**
 * Created by Kirill Yarulin on 26.07.18
 */
@Repository
public interface InternalFileRepository extends CrudRepository<InternalFile, Long> {
    List<InternalFile> findAllByParentDirectory_Id(long directoryId);
}
