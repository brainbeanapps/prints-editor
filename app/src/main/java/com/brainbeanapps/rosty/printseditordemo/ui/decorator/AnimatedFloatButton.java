package com.brainbeanapps.rosty.printseditordemo.ui.decorator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * Created by rosty on 7/7/2015.
 */
public class AnimatedFloatButton {

    private FloatingActionButton floatingActionButton;
    private boolean fabIsShown;

    public AnimatedFloatButton(FloatingActionButton floatingActionButton) {
        this.floatingActionButton = floatingActionButton;
        fabIsShown = true;
    }

    public FloatingActionButton getFloatingActionButton() {
        return floatingActionButton;
    }

    public void show() {
        if (!fabIsShown) {
            floatingActionButton.setClickable(true);
            floatingActionButton.setVisibility(View.VISIBLE);

            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                    floatingActionButton,
                    PropertyValuesHolder.ofFloat("scaleX", 1f),
                    PropertyValuesHolder.ofFloat("scaleY", 1f));
            scaleDown.setDuration(300);
            scaleDown.start();
            fabIsShown = true;
        }
    }

    public void hide() {
        if (fabIsShown) {
            floatingActionButton.setClickable(false);

            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                    floatingActionButton,
                    PropertyValuesHolder.ofFloat("scaleX", 0f),
                    PropertyValuesHolder.ofFloat("scaleY", 0f));

            scaleDown.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    floatingActionButton.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            scaleDown.setDuration(300);
            scaleDown.start();
            fabIsShown = false;
        }
    }
}
