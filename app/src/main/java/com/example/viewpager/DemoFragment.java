package com.example.viewpager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class DemoFragment extends Fragment {
    
    private TextView textView;
    public DemoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        textView = view.findViewById(R.id.txt_display);
        String message = getArguments().getString("message");
        textView.setText(message);

        CheckBox aaaCheckBox = view.findViewById(R.id.aaa);
        CheckBox bbbCheckBox = view.findViewById(R.id.bbb);
        CheckBox cccCheckBox = view.findViewById(R.id.ccc);

        aaaCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DemoFragment", "onClick: " + ((CheckBox)v).isChecked());
            }
        });
        return view;
    }
}