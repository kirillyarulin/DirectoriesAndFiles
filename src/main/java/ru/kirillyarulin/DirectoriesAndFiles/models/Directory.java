package ru.kirillyarulin.DirectoriesAndFiles.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by Kirill Yarulin on 25.07.18
 */
@Entity
@Table(name = "DIRECTORIES")
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "directory_id")
    private long id;
    @NaturalId
    @Column(name = "path")
    private String path;
    @CreationTimestamp
    @Column(name = "time_of_addition")
    private LocalDateTime timeOfAddition;
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

    public Directory(String path, long numberOfFiles, long numberOfSubdirectories, long totalSizeOfFiles) {
        this.path = path;
        this.numberOfFiles = numberOfFiles;
        this.numberOfSubdirectories = numberOfSubdirectories;
        this.totalSizeOfFiles = totalSizeOfFiles;
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

    public void addInternalFile(InternalFile internalFile) {
        internalFiles.add(internalFile);
        internalFile.setParentDirectory(this);
    }

    public void removeInternalFile(InternalFile internalFile) {
        internalFiles.remove(internalFile);
        internalFile.setParentDirectory(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directory directory = (Directory) o;
        return id == directory.id &&
                Objects.equals(timeOfAddition, directory.timeOfAddition) &&
                Objects.equals(path, directory.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, timeOfAddition, path);
    }

    @Override
    public String toString() {
        return "Directory{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", timeOfAddition=" + timeOfAddition +
                ", numberOfFiles=" + numberOfFiles +
                ", numberOfSubdirectories=" + numberOfSubdirectories +
                ", totalSizeOfFiles=" + totalSizeOfFiles +
                '}';
    }
}
