package com.brainbeanapps.rosty.printseditordemo.ui.decorator;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

/**
 * Created by rosty on 7/9/2015.
 */
public abstract class MultipleStateButton<T> implements View.OnClickListener{

    public interface OnStateChangeListener{

        void onStateChanged(int state);
    }

    protected ImageButton imageBtn;
    protected List<T> states;

    private int currentState;

    protected Context context;
    protected OnStateChangeListener listener;

    public MultipleStateButton(ImageButton imageBtn, List<T> states, Context context) {
        this.imageBtn = imageBtn;
        this.states = states;
        this.context = context;

        imageBtn.setOnClickListener(this);
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;

        updateState();
    }

    public void setListener(OnStateChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        currentState++;

        if (currentState == states.size()){
            currentState = 0;
        }

        updateState();
    }

    private void updateState(){
        onStateUpdate(states.get(currentState));
    }

    public abstract void onStateUpdate(T state);
}
