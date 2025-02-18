package edu.sjsu.android.luciddreamingapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

import edu.sjsu.android.luciddreamingapp.databinding.FragmentJournalBinding;

public class JournalFragment extends Fragment {

    FragmentJournalBinding binding;
    EditText journalTitleField;
    EditText journalContentsField;
    Button saveButton;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJournalBinding.inflate(inflater, container, false);

        // Goes to the saved entries or history part
        binding.historyButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_journalFragment_to_fileListFragment));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        journalTitleField = binding.journalTitle;
        journalContentsField = binding.journalContents;
        saveButton = binding.saveButton;

        // Set click listener for the save button
        saveButton.setOnClickListener(v -> saveJournalEntry());
    }


    //Writes the journal entry to the app's internal storage
    private void saveJournalEntry() {
        String title = journalTitleField.getText().toString();
        String contents = journalContentsField.getText().toString();

        // Ensure both title and contents are not empty
        if (title.isEmpty() || contents.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in both title and contents", Toast.LENGTH_SHORT).show();
            return;
        }

        // Write the contents to a file in internal storage
        try {
            getActivity();
            FileOutputStream fos = requireActivity().openFileOutput(title + ".txt", Context.MODE_PRIVATE);
            fos.write(contents.getBytes());
            fos.close();
            Toast.makeText(getActivity(), "Journal entry saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace(); //Hopefully this doesn't happen.
            Toast.makeText(getActivity(), "Error saving journal entry", Toast.LENGTH_SHORT).show();
        }
    }

}