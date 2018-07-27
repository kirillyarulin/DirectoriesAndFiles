package ru.kirillyarulin.DirectoriesAndFiles.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kirill Yarulin on 27.07.18
 */
public class FileSizeConverterTest {

    @Test
    public void format() {
        assertEquals("1.5Gb", FileSizeConverter.format(1610612736));
        assertEquals("70Mb", FileSizeConverter.format(73400320));
        assertEquals("3Kb", FileSizeConverter.format(3072));
        assertEquals("8Eb", FileSizeConverter.format(Long.MAX_VALUE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatWithWrongSize() {
        FileSizeConverter.format(-1);
    }
}