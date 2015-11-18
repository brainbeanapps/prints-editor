package com.brainbeanapps.rosty.printseditordemo.utile;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Created by rosty on 7/7/2015.
 */
public class FileManager {

    private static final String BASE_FOLDER = "/PrintsEditor";
    private static final String PRINTS_FOLDER = BASE_FOLDER + "/prints";

    private static final String PRINT_PREFIX = "print";
    private static final String PRINT_SUFFIX = ".png";

    public static String saveImage(Bitmap finalBitmap) {

        if (createDirectory(PRINTS_FOLDER)) {
            File dir = new File(Environment.getExternalStorageDirectory() + PRINTS_FOLDER);
            String fileName = PRINT_PREFIX + Calendar.getInstance().getTimeInMillis() + PRINT_SUFFIX;
            File file = new File(dir, fileName);

            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.PNG, 0, out);
                out.flush();
                out.close();

                return file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void createAppDirectory(){
        createDirectory(BASE_FOLDER);
    }

    public static boolean createDirectory(String path){
        File folder = new File(Environment.getExternalStorageDirectory() + path);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        return success;
    }
}
