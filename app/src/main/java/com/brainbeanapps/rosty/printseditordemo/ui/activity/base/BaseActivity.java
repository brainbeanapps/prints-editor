package com.brainbeanapps.rosty.printseditordemo.ui.activity.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.brainbeanapps.rosty.printseditordemo.R;

/**
 * Created by rosty on 7/7/2015.
 */
public abstract class BaseActivity extends AppCompatActivity implements
        Toolbar.OnMenuItemClickListener,
        View.OnClickListener{

    public static final String TAG = BaseActivity.class.getSimpleName();

    private static final int ID_TOOLBAR = R.id.toolbar;
    private static final int ID_STATUSBAR = R.id.statusbar;

    protected Toolbar toolbar;
    protected View statusbar;
    protected MaterialMenuDrawable materialMenuDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRootView();

        materialMenuDrawable = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);

        toolbar = (Toolbar) findViewById(ID_TOOLBAR);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationIcon(materialMenuDrawable);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white_tr));

        statusbar = findViewById(ID_STATUSBAR);
        setStatusBarColor(getResources().getColor(R.color.primary_dark));
    }

    public void setStatusBarColor(int color){

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(color);
            getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            statusbar.getLayoutParams().height = getStatusBarHeight();
            statusbar.setBackgroundColor(color);
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected abstract void setRootView();

    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }


}
