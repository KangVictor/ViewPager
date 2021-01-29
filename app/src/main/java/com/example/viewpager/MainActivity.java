package com.example.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private FragmentCollectionAdaptor adaptor;
    private TextView smallTitleText;
    private TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.pager);
        adaptor = new FragmentCollectionAdaptor(getSupportFragmentManager());
        viewPager.setAdapter(adaptor);

        smallTitleText = findViewById(R.id.txt_display);
        titleText = findViewById(R.id.titleText);
    }

    public void onUploadButtonClicked(View view){

    }
}