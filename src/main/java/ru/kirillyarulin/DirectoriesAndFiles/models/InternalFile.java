package ru.kirillyarulin.DirectoriesAndFiles.models;


import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Kirill Yarulin on 25.07.18
 */
@Entity
@Table(name = "INTERNAL_FILES")
public class InternalFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "is_directory")
    private boolean isDirectory;
    @ManyToOne
    @JoinColumn(name = "parent_directory_id")
    private Directory parentDirectory;
    @Column(name = "size")
    private long size;

    public InternalFile() {
    }

    public InternalFile(String name, boolean isDirectory, Directory parentDirectory, long size) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.size = size;
        this.parentDirectory = parentDirectory;
    }

    public InternalFile(String name, boolean isDirectory, Directory parentDirectory) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.size = size;
        this.parentDirectory = parentDirectory;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalFile that = (InternalFile) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "InternalFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isDirectory=" + isDirectory +
                ", parentDirectory=" + parentDirectory +
                ", size=" + size +
                '}';
    }
}
