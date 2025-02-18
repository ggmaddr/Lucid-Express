package edu.sjsu.android.luciddreamingapp;

import static edu.sjsu.android.luciddreamingapp.SoundActivity.hasStopped;
import static edu.sjsu.android.luciddreamingapp.SoundActivity.stopMediaPlayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Navigates to the Journal Page
        Button journalButton = view.findViewById(R.id.journal);
        journalButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_journalFragment));

        // Navigates to the Alarm Page
        Button alarmButton = view.findViewById(R.id.alarm);
        alarmButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_alarmFragment));

        // Navigates to the Tutorial Page
        Button tutorialButton = view.findViewById(R.id.tutorial);
        tutorialButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_tutorialFragment));

        // Navigates to the Sound Page
        Button soundButton = view.findViewById(R.id.sound);
        soundButton.setOnClickListener(v -> {
            if (!hasStopped){
                stopMediaPlayer();
            }
            startActivity(new Intent(requireContext(),SoundActivity.class));
        });
        return view;
    }
}