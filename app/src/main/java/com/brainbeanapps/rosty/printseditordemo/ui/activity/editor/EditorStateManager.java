package com.brainbeanapps.rosty.printseditordemo.ui.activity.editor;

import android.support.v4.app.FragmentManager;

import com.brainbeanapps.rosty.printseditordemo.enums.EditorActivityState;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.PhotoAlbumFragment;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.PrintExportFragment;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.editor.PrintEditorFragment;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.PrintsTemplatesFragment;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.UserPrintsFragment;
import com.brainbeanapps.rosty.printseditordemo.utile.FragmentHelper;
import com.brainbeanapps.rosty.printseditorlib.enums.EditorTemplate;

/**
 * Created by rosty on 7/7/2015.
 */
public class EditorStateManager {

    private FragmentHelper fragmentHelper;
    private EditorActivityState editorActivityState;

    public EditorStateManager(FragmentManager fragmentManager, int fragmentsContainerId) {
        fragmentHelper = new FragmentHelper(fragmentManager, fragmentsContainerId);
    }

    public void showUserPrintsFragment() {
        fragmentHelper.replaceFragment(new UserPrintsFragment(), UserPrintsFragment.TAG);
        editorActivityState = EditorActivityState.USER_PRINTS;
    }

    public void showEditorFragment(EditorTemplate template) {
        fragmentHelper.replaceFragment(
                PrintEditorFragment.getInstance(template),
                PrintEditorFragment.TAG);
        editorActivityState = EditorActivityState.PRINTS_EDITOR;
    }

    public void showTemplatesFragment() {
        fragmentHelper.replaceFragment(new PrintsTemplatesFragment(), PrintsTemplatesFragment.TAG);
        editorActivityState = EditorActivityState.TEMPLATES;
    }

    public void showPhotoAlbumFragment(FragmentManager fm, int containerId) {
        PhotoAlbumFragment.show(fm, containerId);
        editorActivityState = EditorActivityState.PHOTO_ALBUM;
    }

    public void showPrintExportFragment(int printId){
        fragmentHelper.replaceFragment(PrintExportFragment.getInstance(printId), PrintExportFragment.TAG);
        editorActivityState = EditorActivityState.PRINT_EXPORT;
    }

    public void setEditorActivityState(EditorActivityState editorActivityState) {
        this.editorActivityState = editorActivityState;
    }

    public EditorActivityState getState(){
        return editorActivityState;
    }
}