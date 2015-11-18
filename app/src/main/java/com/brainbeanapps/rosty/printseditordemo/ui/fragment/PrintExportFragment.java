package com.brainbeanapps.rosty.printseditordemo.ui.fragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brainbeanapps.rosty.printseditordemo.R;
import com.brainbeanapps.rosty.printseditordemo.data.PrintsDataSource;
import com.brainbeanapps.rosty.printseditordemo.ui.fragment.base.BaseFragment;
import com.brainbeanapps.rosty.printseditorlib.model.PrintDataSet;
import com.brainbeanapps.rosty.printseditorlib.widget.PrintEditorView;

/**
 * Created by rosty on 7/21/2015.
 */
public class PrintExportFragment extends BaseFragment implements
        View.OnClickListener{

    public static final String TAG = PrintExportFragment.class.getSimpleName();
    public static final String KEY_PRINT_ID = "KEY_PRINT_ID";

    private View containerExportAction;
    private View containerExportProgress;

    private Button exportBtn;

    private PrintsDataSource dataSource;
    private int printId;
    private PrintDataSet printDataSet;
    private PrintEditorView editorView;

    public static PrintExportFragment getInstance(int printId){
        PrintExportFragment fragment = new PrintExportFragment();

        Bundle args = new Bundle();
        args.putSerializable(KEY_PRINT_ID, printId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_export_print, container, false);
    }

    @Override
    public void setupView() {
        containerExportAction = rootView.findViewById(R.id.container_export_action);
        containerExportProgress = rootView.findViewById(R.id.container_export_progress);

        exportBtn = (Button) rootView.findViewById(R.id.btn_export);
        exportBtn.setOnClickListener(this);

        printId = getArguments().getInt(KEY_PRINT_ID);
    }

    @Override
    public void onClick(View v) {
        exportPrint(getPrintDataSet());
    }

    private PrintDataSet getPrintDataSet(){
        if (dataSource == null) {
            dataSource = new PrintsDataSource(getActivity());
            dataSource.open();
        }

        printDataSet = dataSource.getPrintById(printId);
        dataSource.close();

        return printDataSet;
    }

    private void exportPrint(PrintDataSet result){

        new ExportTask().execute();
    }

    private class ExportTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            containerExportAction.setVisibility(View.GONE);
            containerExportProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (isAdded()) {
                getActivity().onBackPressed();
            }
        }
    }
}
