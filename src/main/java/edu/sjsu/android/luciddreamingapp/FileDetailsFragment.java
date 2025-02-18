package edu.sjsu.android.luciddreamingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileDetailsFragment extends Fragment {
    TextView fileTitleTextView;
    TextView fileContentsTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_details, container, false);
        fileTitleTextView = view.findViewById(R.id.text_file_title);
        fileContentsTextView = view.findViewById(R.id.text_file_contents);

        // Get file name from arguments
        assert getArguments() != null;
        String fileName = getArguments().getString("fileName");
        displayFileContents(fileName);

        return view;
    }

    private void displayFileContents(String fileName) {
        // Read file contents from internal storage
        try {
            FileInputStream fis = requireActivity().openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            fileTitleTextView.setText(fileName);
            fileContentsTextView.setText(stringBuilder.toString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
