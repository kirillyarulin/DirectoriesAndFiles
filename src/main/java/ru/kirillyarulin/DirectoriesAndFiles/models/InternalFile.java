package ru.kirillyarulin.DirectoriesAndFiles.models;

import javax.persistence.*;

/**
 * Created by Kirill Yarulin on 25.07.18
 */
@Entity
@Table(name = "INTERNAL_FILES")
public class InternalFile {
    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "id_directory")
    private boolean isDirectory;
    @Column(name = "size")
    private long size;
    @ManyToOne
    private Directory parentDirectory;

    public InternalFile() {
    }

    public InternalFile(String name, boolean isDirectory, long size) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }
}
