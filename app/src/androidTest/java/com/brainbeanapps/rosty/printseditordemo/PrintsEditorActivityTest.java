package com.brainbeanapps.rosty.printseditordemo;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.brainbeanapps.rosty.printseditordemo.ui.activity.editor.PrintsEditorActivity;

/**
 * Created by rosty on 9/2/2015.
 */
public class PrintsEditorActivityTest extends ActivityInstrumentationTestCase2<PrintsEditorActivity> {

    private PrintsEditorActivity printsEditorActivity;

    private FloatingActionButton floatButton;
    private Toolbar toolbar;
    private MaterialMenuDrawable materialMenuDrawable;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public PrintsEditorActivityTest() {
        super(PrintsEditorActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        printsEditorActivity = getActivity();
        floatButton = (FloatingActionButton) printsEditorActivity.findViewById(R.id.float_button);
        toolbar = (Toolbar) printsEditorActivity.findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) printsEditorActivity.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) printsEditorActivity.findViewById(R.id.drawer);
    }

    public void testPreconditions() {
        assertNotNull("printsEditorActivity is null", printsEditorActivity);
        assertNotNull("toolbar is null", toolbar);
        assertNotNull("floatButton is null", floatButton);
        assertNotNull("drawerLayout is null", drawerLayout);
        assertNotNull("navigationView is null", navigationView);
    }

    @MediumTest
    public void testMyFirstTestFloatButton_visibility() {
        assertEquals(View.VISIBLE, floatButton.getVisibility());
    }

    @MediumTest
    public void testMyFirstTestMaterialMenuDrawable_iconState() {
        materialMenuDrawable = (MaterialMenuDrawable)toolbar.getNavigationIcon();
        assertEquals(MaterialMenuDrawable.IconState.BURGER, materialMenuDrawable.getIconState());
    }

    @MediumTest
    public void testMyFirstTestDrawerLayout_state() {
        TouchUtils.clickView(this, toolbar.getChildAt(0));
        assertTrue(drawerLayout.isDrawerOpen(navigationView));
    }

    @LargeTest
    public void testMyFirstTestFloatButton_clickButtonAndExpectVisibility() {
        TouchUtils.clickView(this, floatButton);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        assertEquals(View.GONE, floatButton.getVisibility());
    }
}
