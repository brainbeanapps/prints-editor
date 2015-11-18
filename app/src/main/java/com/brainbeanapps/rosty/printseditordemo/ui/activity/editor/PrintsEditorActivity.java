package com.brainbeanapps.rosty.printseditordemo.ui.activity.editor;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.PhotoAdapter;
import com.brainbeanapps.rosty.printseditordemo.enums.EditorAction;
import com.brainbeanapps.rosty.printseditordemo.enums.EditorActivityState;
import com.brainbeanapps.rosty.printseditordemo.ui.activity.base.BaseActivity;
import com.brainbeanapps.rosty.printseditordemo.ui.decorator.AnimatedFloatButton;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.PhotoAlbumFragment;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.base.BaseDialogFragment;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.dialog.SaveDialogFragment;
import com.brainbeanapps.rosty.printseditorlib.enums.EditorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PrintsEditorActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        BaseDialogFragment.OnDialogResultListener {

    public interface OnEditorActionListener{

        void onAction(EditorAction action);
    }

    public static final String TAG = PrintsEditorActivity.class.getSimpleName();

    public static final int MAIN_FRAGMENTS_CONTAINER = R.id.main_fragments_container;
    public static final int SECONDARY_FRAGMENTS_CONTAINER = R.id.secondary_fragments_container;

    private AnimatedFloatButton floatButton;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private EditorStateManager stateManager;
    private ToolbarManager toolbarManager;

    private List<OnEditorActionListener> actionListeners;
    private List<PhotoAdapter.OnPhotoSelectedListener> photoSelectedListeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.drawer);

        floatButton = new AnimatedFloatButton ((FloatingActionButton) findViewById(R.id.float_button));
        floatButton.getFloatingActionButton().setOnClickListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();

        stateManager = new EditorStateManager(fragmentManager, MAIN_FRAGMENTS_CONTAINER);
        toolbarManager = new ToolbarManager(toolbar, materialMenuDrawable, this);

        toolbarManager.setUserPrintsState();
        stateManager.showUserPrintsFragment();

        toolbar.setNavigationOnClickListener(onNavigationClickListener);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_prints_aditor);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.float_button:
                setPrintsEditorState(EditorTemplate.SIMPLE_TEMPLATE);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();

        switch (menuItem.getItemId()){

            case R.id.nav_create_new:
                setPrintsEditorState(EditorTemplate.SIMPLE_TEMPLATE);
                break;

            case R.id.nav_user_prints:
                setUserPrintsState();
                break;

            case R.id.nav_prints_templates:
                setTemplatesState();
                break;

            case R.id.nav_about:
                break;

            case R.id.nav_support:
                break;
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_create:
                notifyActionListeners(EditorAction.SAVE_RESULT);
                setUserPrintsState();
                break;

            case R.id.action_open_album:
                notifyActionListeners(EditorAction.ADD_IMAGE);
                setPhotoAlbumState();
                break;

            case R.id.action_reset:
                notifyActionListeners(EditorAction.RESET_IMAGE);
                break;

        }
        return false;
    }

    public void onPhotoSelected(Bitmap image){
        notifyPhotoSelectedListener(image);
        setPrintsEditorState();
        setPrintsEditorImageState();
    }

    public void addPhotoSelectedListener(PhotoAdapter.OnPhotoSelectedListener photoChosenListener) {
        if (photoSelectedListeners == null)
            photoSelectedListeners = new ArrayList<>();

        if (!photoSelectedListeners.contains(photoChosenListener))
            photoSelectedListeners.add(photoChosenListener);
    }

    public void removePhotoSelectedListener(PhotoAdapter.OnPhotoSelectedListener photoChosenListener){
        if (photoSelectedListeners != null){
            if (photoSelectedListeners.contains(photoChosenListener)){
                photoSelectedListeners.remove(photoChosenListener);
            }
        }
    }

    private void notifyPhotoSelectedListener(Bitmap image){
        if (photoSelectedListeners != null){
            for (PhotoAdapter.OnPhotoSelectedListener l: photoSelectedListeners){
                l.onPhotoSelected(image);
            }
        }
    }

    public void onTemplateSelected(EditorTemplate template){
        setPrintsEditorState(template);
    }


    public void addActionListener(OnEditorActionListener actionListener) {
        if (actionListeners == null)
            actionListeners = new ArrayList<>();

        if (!actionListeners.contains(actionListener))
            actionListeners.add(actionListener);
    }

    public void removeActionListener(OnEditorActionListener actionListener){
        if (actionListeners != null){
            if (actionListeners.contains(actionListener)){
                actionListeners.remove(actionListener);
            }
        }
    }

    private void notifyActionListeners(EditorAction action){
        if (actionListeners != null){
            for (OnEditorActionListener l: actionListeners){
                l.onAction(action);
            }
        }
    }

    public void setUserPrintsState(){
        stateManager.showUserPrintsFragment();
        toolbarManager.setUserPrintsState();
        floatButton.show();

        navigationView.getMenu().findItem(R.id.nav_user_prints).setChecked(true);
    }

    private void setTemplatesState(){
        stateManager.showTemplatesFragment();
        toolbarManager.setTemplatesState();
        floatButton.show();

        navigationView.getMenu().findItem(R.id.nav_prints_templates).setChecked(true);
    }

    private void setPrintsEditorState(EditorTemplate template){
        stateManager.showEditorFragment(template);
        setPrintsEditorState();
    }

    private void setPrintsEditorState(){
        floatButton.hide();
        toolbarManager.setEditorState();

        navigationView.getMenu().findItem(R.id.nav_create_new).setChecked(true);
    }

    public void setPrintsEditorTextState(){
        stateManager.setEditorActivityState(EditorActivityState.PRINTS_EDITOR);
        toolbarManager.setEditorTextState();
    }

    public void setPrintsEditorSVGState(){
        stateManager.setEditorActivityState(EditorActivityState.PRINTS_EDITOR);
        toolbarManager.setEditorSVGState();
    }

    public void setPrintsEditorImageState(){
        stateManager.setEditorActivityState(EditorActivityState.PRINTS_EDITOR);
        toolbarManager.setEditorImageState();
    }

    public void setPhotoAlbumState(){
        stateManager.showPhotoAlbumFragment(getSupportFragmentManager(), SECONDARY_FRAGMENTS_CONTAINER);
        toolbarManager.setPhotoAlbumState();
    }

    public void setPrintExportState(int printId){
        floatButton.hide();

        stateManager.showPrintExportFragment(printId);
        toolbarManager.setPrintExportState();
    }

    private View.OnClickListener onNavigationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (stateManager.getState()){

                case USER_PRINTS:
                case TEMPLATES:
                    drawerLayout.openDrawer(Gravity.LEFT);
                    break;

                case PRINTS_EDITOR:
                    SaveDialogFragment.newInstance(
                            getApplicationContext())
                            .show(getSupportFragmentManager());
                    break;

                case PRINT_EXPORT:
                    setUserPrintsState();
                    break;

                case PHOTO_ALBUM:
                    setPrintsEditorImageState();
                    setPrintsEditorState();
                    PhotoAlbumFragment.removeFragment(getSupportFragmentManager());
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        switch (stateManager.getState()){

            case USER_PRINTS:
            case TEMPLATES:
                super.onBackPressed();
                break;

            case PRINT_EXPORT:
                setUserPrintsState();
                break;

            case PRINTS_EDITOR:
                SaveDialogFragment.newInstance(this).show(getSupportFragmentManager());
                break;

            case PHOTO_ALBUM:
                setPrintsEditorImageState();
                setPrintsEditorState();
                PhotoAlbumFragment.removeFragment(getSupportFragmentManager());
                break;
        }
    }

    @Override
    public void onDialogResult(int resultCode) {

        switch (resultCode) {

            case BaseDialogFragment.DIALOG_POSITIVE:
                notifyActionListeners(EditorAction.SAVE_RESULT);
                break;

            case BaseDialogFragment.DIALOG_NEGATIVE:
                break;
        }

        setUserPrintsState();
    }
}
