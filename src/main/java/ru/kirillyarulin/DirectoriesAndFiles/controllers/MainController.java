package ru.kirillyarulin.DirectoriesAndFiles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kirillyarulin.DirectoriesAndFiles.models.Directory;
import ru.kirillyarulin.DirectoriesAndFiles.models.InternalFile;
import ru.kirillyarulin.DirectoriesAndFiles.services.DirectoryService;
import ru.kirillyarulin.DirectoriesAndFiles.services.InternalFileService;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Kirill Yarulin on 27.07.18
 */
@Controller
public class MainController {
    @Autowired
    private DirectoryService directoryService;

    private boolean isError = false;
    private boolean tableSortingOrder = false;

    @ModelAttribute("isError")
    public boolean isError() {
        return isError;
    }

    @ModelAttribute("tableSortingOrder")
    public boolean tableSortingOrder() {
        return tableSortingOrder;
    }

    @ModelAttribute("directories")
    public List<Directory> allDirectories() {
        List<Directory> directories = directoryService.getAllDirectories();
        if (tableSortingOrder) {
            directories.sort(Comparator.comparing(Directory::getTimeOfAddition));
        } else {
            directories.sort(Comparator.comparing(Directory::getTimeOfAddition).reversed());
        }

        return directories;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        return "index";
    }

    @PostMapping("/addDirectory")
    public String addDirectory(@RequestParam(name = "directoryPath") String directoryPath, Model model) {
        try {
            if (directoryPath.length() < 1) {
                throw new IllegalArgumentException("Empty directory path");
            }
            Directory newDirectory = directoryService.addDirectory(directoryPath);
            model.addAttribute("directory", newDirectory);
            isError = false;
        } catch (IllegalArgumentException e) {
            isError = true;
        }

        return "redirect:/";
    }


    @GetMapping("deleteDirectory")
    public String deleteDirectory(@RequestParam(name = "directoryId") long directoryId) {
        directoryService.deleteDirectoryById(directoryId);
        return "redirect:/";
    }

    @GetMapping("changeTableSortingOrder")
    public String changeTableSortingOrder() {
        tableSortingOrder = !tableSortingOrder;
        return "redirect:/";
    }
}
