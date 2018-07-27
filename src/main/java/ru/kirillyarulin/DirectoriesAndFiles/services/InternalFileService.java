package ru.kirillyarulin.DirectoriesAndFiles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kirillyarulin.DirectoriesAndFiles.models.Directory;
import ru.kirillyarulin.DirectoriesAndFiles.models.InternalFile;
import ru.kirillyarulin.DirectoriesAndFiles.repositories.InternalFileRepository;

import java.util.List;

/**
 * Created by Kirill Yarulin on 27.07.18
 */
@Service
public class InternalFileService {
    @Autowired
    private InternalFileRepository internalFileRepository;


    public List<InternalFile> findAllByParentDirectory(Directory directory) {
        if (directory == null) {
            throw new IllegalArgumentException("Directory is null");
        }

        return findAllByParentDirectory(directory.getId());
    }

    public List<InternalFile> findAllByParentDirectory(long directoryId) {
        if (directoryId < 1) {
            throw new IllegalArgumentException("The directory has the wrong id");
        }
        return internalFileRepository.findAllByParentDirectory_Id(directoryId);
    }
}
