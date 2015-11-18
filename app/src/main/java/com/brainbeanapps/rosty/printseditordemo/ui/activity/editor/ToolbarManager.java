package com.brainbeanapps.rosty.printseditordemo.ui.activity.editor;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.Toolbar;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.brainbeanapps.rosty.printseditordemo.R;

/**
 * Created by rosty on 7/7/2015.
 */
public class ToolbarManager {

    private Toolbar toolbar;
    private PrintsEditorActivity activity;
    private MaterialMenuDrawable materialMenuDrawable;

    public ToolbarManager(Toolbar toolbar,MaterialMenuDrawable materialMenuDrawable, PrintsEditorActivity activity) {
        this.toolbar = toolbar;
        this.activity = activity;
        this.materialMenuDrawable = materialMenuDrawable;
    }

    public void setUserPrintsState(){
        setupMenu(R.string.title_user_prints, R.menu.menu_user_prints);
        materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.BURGER, true);
        setToolbarElevation(activity.getResources().getDimension(R.dimen.shadow_top));
    }

    public void setEditorTextState(){
        setupMenu(R.string.title_print_editor, R.menu.menu_prints_editor_text);
    }

    public void setEditorSVGState(){
        setupMenu(R.string.title_print_editor, R.menu.menu_prints_editor_svg);
    }

    public void setEditorState() {
        materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW, true);
        float elevation = activity.getResources().getDimension(R.dimen.shadow_mid);
        setToolbarElevation(elevation);
    }

    public void setEditorImageState() {
        setupMenu(R.string.title_print_editor, R.menu.menu_prints_editor_image);
    }

    public void setTemplatesState() {
        materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.BURGER, true);
        setupMenu(R.string.title_prints_templates, R.menu.menu_prints_templates);
        setToolbarElevation(activity.getResources().getDimension(R.dimen.shadow_top));
    }

    public void setPhotoAlbumState(){
        materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.X, true);
        setupMenu(R.string.title_photo_album, R.menu.menu_photo_album);
        setToolbarElevation(activity.getResources().getDimension(R.dimen.shadow_top));
    }

    public void setPrintExportState(){
        materialMenuDrawable.animateIconState(MaterialMenuDrawable.IconState.ARROW, true);
        setupMenu(R.string.title_print_export, R.menu.menu_print_export);
        setToolbarElevation(activity.getResources().getDimension(R.dimen.shadow_top));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setToolbarElevation(float elevation){
        if (Build.VERSION.SDK_INT >= 21) {
            toolbar.setElevation(elevation);
        }
    }

    private void setupMenu(int title, int menu){
        toolbar.getMenu().clear();
        toolbar.inflateMenu(menu);
        toolbar.setTitle(activity.getResources().getString(title));
    }
}
