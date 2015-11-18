package com.brainbeanapps.rosty.printseditordemo.ui.fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.adapter.BaseListAdapter;
import com.brainbeanapps.rosty.printseditordemo.adapter.PhotoAdapter;
import com.brainbeanapps.rosty.printseditordemo.ui.GridSpacesItemDecoration;
import com.brainbeanapps.rosty.printseditordemo.ui.activity.editor.PrintsEditorActivity;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.base.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rosty on 7/15/2015.
 */
public class PhotoAlbumFragment extends BaseListFragment<String> {

    public static final String TAG = PhotoAlbumFragment.class.getSimpleName();
    public static final int COLUMN_NUMBER = 3;

    public static void show(FragmentManager fm, int containerId){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, new PhotoAlbumFragment(), TAG);
        ft.commit();
    }

    public static void removeFragment(FragmentManager fm){
        Fragment fragment = fm.findFragmentByTag(TAG);

        if (fragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int space = (int)getResources().getDimension(R.dimen.content_space)/2;
        recyclerView.addItemDecoration(new GridSpacesItemDecoration(space, COLUMN_NUMBER));
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLUMN_NUMBER);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    public BaseListAdapter getAdapter() {
        if (adapter == null){
            adapter = new PhotoAdapter(getActivity(), dataList, photoChosenListener);
        }
        return adapter;
    }

    @Override
    public List<String> getData() {
        return getPhotosDevice();
    }

    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_photo_album, container, false);
    }

    public List<String>  getPhotosDevice() {
        final String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

        final String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

        Uri uri = MediaStore.Files.getContentUri("external");
        CursorLoader cursorLoader = new CursorLoader(
                getActivity(),
                uri,
                projection,
                selection,
                null,
                MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC"
        );

        final Cursor cursor = cursorLoader.loadInBackground();

        ArrayList<String> result = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);

            do {
                final String data = cursor.getString(dataColumn);

                result.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return result;
    }

    private PhotoAdapter.OnPhotoSelectedListener photoChosenListener = new PhotoAdapter.OnPhotoSelectedListener() {
        @Override
        public void onPhotoSelected(Bitmap image) {
            ((PrintsEditorActivity) getActivity()).onPhotoSelected(image);
            removeFragment(getActivity().getSupportFragmentManager());
        }
    };
}
