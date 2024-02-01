package com.cs2340.app_with_realm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CourseScreen extends Fragment {
    TextView instructorText;
    TextView locationText;
    TextView timeText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.course_screen, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        instructorText = view.findViewById(R.id.instructorText);
        locationText = view.findViewById(R.id.locationText);
        timeText = view.findViewById(R.id.timeText);

        instructorText.setText(getValue("instructor"));
        locationText.setText(getValue("location"));
        timeText.setText(getValue("time"));

    }
    private String getValue(String type) {
        return type + ": " + getArguments().getString(type);
    }
}