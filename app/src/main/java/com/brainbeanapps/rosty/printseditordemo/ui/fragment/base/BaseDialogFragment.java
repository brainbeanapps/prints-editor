package com.brainbeanapps.rosty.printseditordemo.ui.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.brainbeanapps.rosty.printseditordemo.R;

/**
 * Created by rosty on 7/24/2015.
 */
public abstract class BaseDialogFragment extends DialogFragment implements View.OnClickListener{

    public interface OnDialogResultListener {

        void onDialogResult(int resultCode);
    }

    public static final String DIALOG_TITLE = "dialog_title";
    public static final String DIALOG_POSITIVE_TEXT = "dialog_positive";
    public static final String DIALOG_NEGATIVE_TEXT = "dialog_negative";

    public static final int DIALOG_POSITIVE = 0x411;
    public static final int DIALOG_NEGATIVE= 0x422;
    public static final int DIALOG_REQUEST= 0x433;

    protected Button positiveBtn;
    protected Button negativeBtn;

    private OnDialogResultListener onDialogResultListener;

    public void show(FragmentManager fragmentManager){
        this.setCancelable(false);
        this.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(this, getDialogTag()).commit();
    }

    public void show(Fragment fragment ,FragmentManager fragmentManager){
        this.setTargetFragment(fragment, 0);
        show(fragmentManager);
    }

    protected void build(String title, String negativeVtnText, String positiveBtnText) {

        Bundle bundle = new Bundle();
        bundle.putString(DIALOG_TITLE, title);
        bundle.putString(DIALOG_POSITIVE_TEXT, positiveBtnText);
        bundle.putString(DIALOG_NEGATIVE_TEXT, negativeVtnText);

        setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_fragment_base, container);
        getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white_round_corners));

        String text = getArguments().getString(DIALOG_TITLE);
        String negative = getArguments().getString(DIALOG_NEGATIVE_TEXT);
        String positive = getArguments().getString(DIALOG_POSITIVE_TEXT);

        TextView title = (TextView) v.findViewById(R.id.dialog_title);
        title.setText(text);

        positiveBtn = (Button) v.findViewById(R.id.dialog_positive_btn);
        negativeBtn = (Button) v.findViewById(R.id.dialog_negative_btn);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        positiveBtn.setText(positive);
        negativeBtn.setText(negative);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            this.onDialogResultListener = (OnDialogResultListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        onDialogResultListener = null;
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.dialog_positive_btn:
                sendResultAndDismiss(DIALOG_POSITIVE);
                break;

            case R.id.dialog_negative_btn:
                sendResultAndDismiss(DIALOG_NEGATIVE);
                break;
        }
    }

    private void sendResultAndDismiss(int resultCode){
        if (getTargetFragment() != null){
            getTargetFragment().onActivityResult(DIALOG_REQUEST, resultCode, null);
        }

        if (onDialogResultListener != null){
            onDialogResultListener.onDialogResult(resultCode);
        }

        dismiss();
    }

    public abstract String getDialogTag();
}
