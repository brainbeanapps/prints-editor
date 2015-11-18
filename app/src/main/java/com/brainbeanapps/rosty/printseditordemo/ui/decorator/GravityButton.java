package com.brainbeanapps.rosty.printseditordemo.ui.decorator;

import android.content.Context;
import android.widget.ImageButton;

import java.util.List;

/**
 * Created by rosty on 7/9/2015.
 */
public class GravityButton extends MultipleStateButton<GravityButton.GravityState>{

    public GravityButton(ImageButton gravityBtn, List<GravityState> gravityStates, Context context) {
        super(gravityBtn, gravityStates, context);
    }

    public void onStateUpdate(GravityState state) {
        imageBtn.setImageDrawable(context.getResources().getDrawable(state.getImage()));
        listener.onStateChanged(state.getGravity());
    }

    public static class GravityState{

        private int gravity;
        private int image;

        public GravityState(int gravity, int image) {
            this.gravity = gravity;
            this.image = image;
        }

        public int getGravity() {
            return gravity;
        }

        public int getImage() {
            return image;
        }
    }
}
