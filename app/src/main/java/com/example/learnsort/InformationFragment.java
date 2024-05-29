package com.example.learnsort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InformationFragment extends Fragment {

    private String your_text;
    private TextView informatii;

    public InformationFragment(String your_text) {
        this.your_text = your_text;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout first
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        // Find the TextView in the inflated layout
        informatii = view.findViewById(R.id.textViewInfo);

        // Set the text for the TextView
        informatii.setText(your_text);

        return view;
    }
}