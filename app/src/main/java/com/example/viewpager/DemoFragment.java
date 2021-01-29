package com.example.viewpager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class DemoFragment extends Fragment {
    
    private TextView textView;
    private ArrayList<File> fileList;
    private ArrayList<File> folderList;

    private ListView folderListView;
    private ListView fileListView;
    public DemoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        textView = view.findViewById(R.id.txt_display);
        String message = getArguments().getString("position");
        textView.setText(message);

        folderListView = (ListView) view.findViewById(R.id.folder_listview);
        fileListView = (ListView) view.findViewById(R.id.file_listview);

        folderList = new ArrayList<File>();
        fileList = new ArrayList<File>();

//        fileList.add("hello");
//        fileList.add("not hello");
//        fileList.add("very hello");

        File root = new File(Environment.getRootDirectory().getName());
        Log.d("MainActivity", root.toString());
        Log.d("MainActivity", root.listFiles()[0].toString());

        ListDir(root);
        ArrayAdapter<File> dir = new ArrayAdapter<File>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, folderList); // doesn't work inside ListDir
        folderListView.setAdapter(dir);
        dir = new ArrayAdapter<File>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, fileList);
        fileListView.setAdapter(dir);

        this.folderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File nextLocation = (File) folderListView.getItemAtPosition(position);

                Log.i("DemoFragment", "onFolderClick: " + position);
                Log.i("DemoFragment", "onFolderClick: " + nextLocation);

                ListDir(nextLocation);
                ArrayAdapter<File> dir = new ArrayAdapter<File>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, folderList); // doesn't work inside ListDir
                folderListView.setAdapter(dir);
                dir = new ArrayAdapter<File>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, fileList);
                fileListView.setAdapter(dir);
            }
        });

        this.fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                Log.i("DemoFragment", "onFileClick: " + position);
                Log.i("DemoFragment", "onFileClick: " + currentCheck);
                Log.i("DemoFragment", "onFileClick: " + (File) fileListView.getItemAtPosition(position));
            }
        });

//        aaaCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("DemoFragment", "onClick aaa: " + ((CheckBox)v).isChecked());
//            }
//        });
        return view;
    }

    private void ListDir(File f) {
        File[] files = f.listFiles();
        fileList.clear();
        folderList.clear();
        for(File file:files) {
            if(file.isDirectory())
                folderList.add(file);
            else
                fileList.add(file);
        }
    }
}