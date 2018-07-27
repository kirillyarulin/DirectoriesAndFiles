package ru.kirillyarulin.DirectoriesAndFiles.utils;

import java.text.DecimalFormat;

/**
 * Created by Kirill Yarulin on 27.07.18
 */
public final class FileSizeConverter {
    private FileSizeConverter() {
    }

    public static String format(long sizeInBytes) {
        DecimalFormat dFormat = new DecimalFormat();
        dFormat.setDecimalSeparatorAlwaysShown(false);

        if (sizeInBytes < 0) {
            throw new IllegalArgumentException("Negative file size");
        }

        if (sizeInBytes < 1024) {
            return sizeInBytes + "bytes";
        } else if (sizeInBytes < Math.pow(1024, 2)) {
            return dFormat.format(sizeInBytes / 1024) + "Kb";
        } else if (sizeInBytes < Math.pow(1024, 3)) {
            return dFormat.format(sizeInBytes / Math.pow(1024, 2)) + "Mb";
        } else if (sizeInBytes < Math.pow(1024, 4)) {
            return dFormat.format(sizeInBytes / Math.pow(1024, 3)) + "Gb";
        } else if (sizeInBytes < Math.pow(1024, 5)) {
            return dFormat.format(sizeInBytes / Math.pow(1024, 4)) + "Tb";
        } else if (sizeInBytes < Math.pow(1024, 6)) {
            return dFormat.format(sizeInBytes / Math.pow(1024, 5)) + "Pb";
        } else {
            return dFormat.format(sizeInBytes / Math.pow(1024, 6)) + "Eb";
        }
    }

}
