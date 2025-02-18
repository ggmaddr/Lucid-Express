package edu.sjsu.android.luciddreamingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListFragment extends Fragment {

    List<String> fileList;
    FileListAdapter fileListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // While I prefer to use binding, some guides say to use this instead... and well, it works just fine.
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_files);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fileList = getSavedFilesFromStorage(); // Get list of saved files
        fileListAdapter = new FileListAdapter(fileList);
        recyclerView.setAdapter(fileListAdapter);

        return view;
    }

    // Method to retrieve saved file names from internal storage
    private List<String> getSavedFilesFromStorage() {
        List<String> savedFiles = new ArrayList<>();
        File directory = requireActivity().getFilesDir();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) { // Get only .txt files
                    savedFiles.add(file.getName()); // Add file name to the list
                }
            }
        }
        return savedFiles;
    }
}