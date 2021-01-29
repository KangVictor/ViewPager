package com.example.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.File.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private FragmentCollectionAdaptor adaptor;
    private TextView subTitleText;
    private TextView titleText;
    private ArrayList<String> fileList = new ArrayList<String>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.pager);
        adaptor = new FragmentCollectionAdaptor(getSupportFragmentManager());
        viewPager.setAdapter(adaptor);

        titleText = findViewById(R.id.titleText);
    }

//    private void ListDir(File f) {
//        File[] files = f.listFiles();
//        filesList.clear();
//        for(File file:files) {
//            filesList.add(file.getName());
//        }
//    }

    public void onUploadButtonClicked(View view){

    }
}