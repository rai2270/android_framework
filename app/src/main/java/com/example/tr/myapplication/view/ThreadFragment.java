package com.example.tr.myapplication.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tr.myapplication.MyApplication;
import com.example.tr.myapplication.R;
import com.example.tr.myapplication.domain.work.MyWorker;
import com.example.tr.myapplication.utility.LumberJack;
import com.example.tr.myapplication.view.mvp.MainFragmentPresenter;
import com.example.tr.myapplication.view.mvp.view.IMainFragmentView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThreadFragment extends Fragment implements IMainFragmentView {

    @Inject
    MainFragmentPresenter presenter;

    @BindView(R.id.textView2)
    TextView textViewResults;

    public ThreadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_thread, container, false);
        ButterKnife.bind(this, v);
        MyApplication.getInstance().getAppComponent().inject(ThreadFragment.this);
        presenter.setView(this);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onPause() {
        presenter.stop();
        super.onPause();
    }

    @OnClick(R.id.button)
    void submit() {
        Toast.makeText(getActivity(), "move to background - check logcat", Toast.LENGTH_SHORT).show();
        presenter.doPriQ();
    }

    @OnClick(R.id.button2)
    void submit2() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            long time = System.currentTimeMillis();
            getActivity().runOnUiThread(() -> textViewResults.setText("" + time));
        });
    }

    @OnClick(R.id.button3)
    void submit3() {
        Data data = new Data.Builder()
                .putString(MyWorker.EXTRA_TITLE, "Message from Activity!")
                .putString(MyWorker.EXTRA_TEXT, "Hi! I have come from activity.")
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        final OneTimeWorkRequest simpleRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInputData(data)
                .setConstraints(constraints)
                .addTag("simple_work")
                .build();

        WorkManager.getInstance().enqueue(simpleRequest);

        WorkManager.getInstance().getWorkInfoByIdLiveData(simpleRequest.getId())
                .observe(this, workInfo -> {
                    if (workInfo != null) {
                        textViewResults.setText("SimpleWorkRequest: " + workInfo.getState().name() + "\n");
                    }

                    if (workInfo != null && workInfo.getState().isFinished()) {
                        String message = workInfo.getOutputData().getString(MyWorker.EXTRA_OUTPUT_MESSAGE);
                        textViewResults.setText("" + message);
                    }
                });
    }

    @Override
    public void showResultsFromJobSticky(long time) {
        LumberJack.logGeneric("ThreadFragment: showResultsFromJobSticky");
        textViewResults.setText("" + time);
    }

    @Override
    public void showResultsFromJob(String time) {
        LumberJack.logGeneric("ThreadFragment: showResultsFromJob");
        textViewResults.setText(time);
    }


}
