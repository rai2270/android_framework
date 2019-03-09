package com.example.tr.myapplication.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tr.myapplication.R;

import androidx.navigation.Navigation;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getView().findViewById(R.id.threadBtn).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_threadFragment));
        getView().findViewById(R.id.restBtn).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_restFragment));
        getView().findViewById(R.id.accountBtn).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_accountFragment));
        getView().findViewById(R.id.settingsBtn).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_settingsFragment));
    }




}
