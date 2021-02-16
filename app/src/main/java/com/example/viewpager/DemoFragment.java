package com.example.viewpager;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.FileUtils;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;

import android.widget.ArrayAdapter;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;

public class DemoFragment extends Fragment {
    
    private TextView pathText;
    private TextView titleText;

    private ListView folderListView;
    private ListView fileListView;

    private final String ftpServer = "ftp.innowireless.com";
    private final String ftpUser = "mobileswtest";
    private final String ftpPW = "mobileswtest";
    public DemoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        pathText = view.findViewById(R.id.path_text);
        titleText = view.findViewById(R.id.titleText);
        folderListView = (ListView) view.findViewById(R.id.folder_listview);
        fileListView = (ListView) view.findViewById(R.id.file_listview);

        int position = getArguments().getInt("position");
        if(position == 1) {
            titleText.setText("Phone Storage");

            File root = new File(Environment.getRootDirectory().getName());
            pathText.setText(root.toString());

            ArrayList<File> folderList = new ArrayList<File>();
            ArrayList<File> fileList = new ArrayList<File>();
            File[] files = root.listFiles();
            fileList.clear();
            folderList.clear();
            for(File file:files) {
                if(file.isDirectory())
                    folderList.add(file);
                else
                    fileList.add(file);
            }
            ArrayAdapter<File> dir = new ArrayAdapter<File>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, folderList); // doesn't work inside ListDir
            folderListView.setAdapter(dir);
            dir = new ArrayAdapter<File>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, fileList);
            fileListView.setAdapter(dir);
        } else if(position == 2) {
            titleText.setText("Server");
            pathText.setText(ftpServer);

            ArrayList<FTPFile> folderList = new ArrayList<FTPFile>();
            ArrayList<FTPFile> fileList = new ArrayList<FTPFile>();
            Thread getFTP = new Thread(new Runnable() { // Executor is an alternative, but I chose using thread
                @Override
                public void run() {
                    try  {
                        FTPClient ftpClient = new FTPClient();
//                      ftpClient.connect(InetAddress.getByName(ftpServer));
                        ftpClient.connect(ftpServer);
                        ftpClient.login(ftpUser, ftpPW);
                        Log.d("MainActivity", ftpClient.getReplyString());

                    if(!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                        ftpClient.disconnect();
                        Log.d("MainActivity", "FTP server refused connection.");
                    }
//                    ftpClient.changeWorkingDirectory(serverRoad);
//                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    FTPFile[] ftpFiles = ftpClient.listFiles("/");
                    Log.d("MainActivity", ftpFiles[0].toString());

                    fileList.clear();
                    folderList.clear();
                    for(FTPFile file: ftpFiles) {
                        if (file.isDirectory())
                            folderList.add(file);
                        else
                            fileList.add(file);
                    }
                    ftpClient.logout();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("MainActivity", e.getMessage());
                    }
                }
            });
            getFTP.start(); // cannot change view inside the thread
            try {
                getFTP.join(); // wait until the thread finishes
                ArrayAdapter<FTPFile> dir = new ArrayAdapter<FTPFile>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, folderList); // doesn't work inside ListDir
                folderListView.setAdapter(dir);
                dir = new ArrayAdapter<FTPFile>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, fileList);
                fileListView.setAdapter(dir);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




//        ArrayAdapter<File> adapter = new ArrayAdapter<File>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, folderList)
//        {
//            public View getView(int position, View convertView, ViewGroup parent) {
//                if(convertView == null)
//                {
//                    View v = getLayoutInflater().inflate(android.R.layout.simple_list_item_multiple_choice, null);
//
//                    CheckedTextView ctv = (CheckedTextView)v.findViewById(android.R.id.text1);
//                    Log.i("DemoFragment", ctv.toString());
//                    ctv.setText(folderList.get(position).toString());
//                    Log.i("DemoFragment2", folderList.get(position).toString());
//                    ctv.setVisibility(View.VISIBLE);
//
////                    ctv.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////
////                        }
////
////                    });
//                    return v;
//                }
//                return convertView;
//            };
//        };
//
//        folderListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        folderListView.setAdapter(adapter);

//        this.folderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                File nextLocation = (File) folderListView.getItemAtPosition(position);
//
//                Log.i("DemoFragment", "onFolderClick: " + position);
//                Log.i("DemoFragment", "onFolderClick: " + nextLocation);
//
//                pathText.setText(nextLocation.toString());
//                ListDir(nextLocation);
//                ArrayAdapter<File> dir = new ArrayAdapter<File>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, folderList); // doesn't work inside ListDir
//                folderListView.setAdapter(dir);
//                dir = new ArrayAdapter<File>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, fileList);
//                fileListView.setAdapter(dir);
//            }
//        });

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

//    private void ListDir(File f) {
//        File[] files = f.listFiles();
//        fileList.clear();
//        folderList.clear();
//        for(File file:files) {
//            if(file.isDirectory())
//                folderList.add(file);
//            else
//                fileList.add(file);
//        }
//    }
}