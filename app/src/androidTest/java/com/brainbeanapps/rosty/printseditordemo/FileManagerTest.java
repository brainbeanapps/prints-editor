package com.brainbeanapps.rosty.printseditordemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;

import com.brainbeanapps.rosty.printseditordemo.ui.activity.editor.PrintsEditorActivity;
import com.brainbeanapps.rosty.printseditordemo.utile.FileManager;

import java.io.File;

/**
 * Created by rosty on 9/2/2015.
 */
public class FileManagerTest extends ActivityInstrumentationTestCase2<PrintsEditorActivity> {

    private static final String NEW_FOLDER = "/NewFolder";

    private PrintsEditorActivity printsEditorActivity;

    public FileManagerTest() {
        super(PrintsEditorActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        printsEditorActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("printsEditorActivity is null", printsEditorActivity);
    }

    @LargeTest
    public void testFileManagerSaveImage(){
        Bitmap imageToSave = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_empty_emoticon_sad);
        String expectedPath = FileManager.saveImage(imageToSave);

        File imageFile = new File(expectedPath);

        assertTrue(imageFile.exists());
    }

    @MediumTest
    public void testFileManagerCreateDirectory(){
        FileManager.createDirectory(NEW_FOLDER);
        File expectedDir = new File(Environment.getExternalStorageDirectory() + NEW_FOLDER);

        assertTrue(expectedDir.exists());
        assertTrue(expectedDir.isDirectory());
    }
}
