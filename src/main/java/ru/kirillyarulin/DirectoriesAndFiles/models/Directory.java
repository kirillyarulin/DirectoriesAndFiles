package ru.kirillyarulin.DirectoriesAndFiles.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Kirill Yarulin on 25.07.18
 */
@Entity
@Table(name = "DIRECTORIES")
public class Directory {
    @Id
    @GeneratedValue
    @Column(name = "directory_id")
    private long id;
    @CreationTimestamp
    @Column(name = "time_of_addition")
    private LocalDateTime timeOfAddition;
    @Column(name = "path")
    private String path;
    @Column(name = "number_of_files")
    private long numberOfFiles;
    @Column(name = "number_of_subdirectories")
    private long numberOfSubdirectories;
    @Column(name = "total_size_of_files")
    private long totalSizeOfFiles;
    @OneToMany(mappedBy ="parentDirectory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InternalFile> internalFiles;

    public Directory() {
    }

    public Directory(LocalDateTime timeOfAddition, String path, long numberOfFiles, long numberOfSubdirectories, long totalSizeOfFiles, List<InternalFile> internalFiles) {
        this.timeOfAddition = timeOfAddition;
        this.path = path;
        this.numberOfFiles = numberOfFiles;
        this.numberOfSubdirectories = numberOfSubdirectories;
        this.totalSizeOfFiles = totalSizeOfFiles;
        this.internalFiles = internalFiles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimeOfAddition() {
        return timeOfAddition;
    }

    public void setTimeOfAddition(LocalDateTime timeOfAddition) {
        this.timeOfAddition = timeOfAddition;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getNumberOfFiles() {
        return numberOfFiles;
    }

    public void setNumberOfFiles(long numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public long getNumberOfSubdirectories() {
        return numberOfSubdirectories;
    }

    public void setNumberOfSubdirectories(long numberOfSubdirectories) {
        this.numberOfSubdirectories = numberOfSubdirectories;
    }

    public long getTotalSizeOfFiles() {
        return totalSizeOfFiles;
    }

    public void setTotalSizeOfFiles(long totalSizeOfFiles) {
        this.totalSizeOfFiles = totalSizeOfFiles;
    }

    public List<InternalFile> getInternalFiles() {
        return internalFiles;
    }

    public void setInternalFiles(List<InternalFile> internalFiles) {
        this.internalFiles = internalFiles;
    }
}
