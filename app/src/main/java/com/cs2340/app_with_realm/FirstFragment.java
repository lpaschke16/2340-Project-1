package com.cs2340.app_with_realm;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.cs2340.app_with_realm.RealmObjects.Course;
import com.cs2340.app_with_realm.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.Arrays;
import io.realm.Realm;


import io.realm.RealmResults;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ListView lv;
    private EditText et;
    public Button submitButton;
    private Realm realm;
    private ViewGroup container;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
        this.container = container;
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        realm = Realm.getDefaultInstance();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RealmResults<Course> Courses = realm.where(Course.class).findAll();
        lv = view.findViewById(R.id.list);
        lv.setAdapter(new CourseContainer(Courses, getContext()));

        binding.AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick(view);
            }
        });
    }

    public void navigateCourseScreen(Bundle bundle) {
        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_courseScreen, bundle);
    }


    public void onButtonShowPopupWindowClick(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText nameText = popupView.findViewById(R.id.nameText);
        EditText instructorText = popupView.findViewById(R.id.instructorText);
        EditText timeText = popupView.findViewById(R.id.timeText);
        EditText locationText = popupView.findViewById(R.id.locationText);
        Button submitButton = popupView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View innerView) {
                String newName = nameText.getText().toString();
                String newInstructor = instructorText.getText().toString();
                String newTime = timeText.getText().toString();
                String newLocation = locationText.getText().toString();
                Course course = new Course(newName, newTime, newInstructor, newLocation);

                realm.executeTransaction (transactionRealm -> {
                    transactionRealm.insert(course);
                });
                Bundle bundle = new Bundle();
                bundle.putString("courseName", newName);
                bundle.putString("instructor", newInstructor);
                bundle.putString("time", newTime);
                bundle.putString("location", newLocation);

                navigateCourseScreen(bundle);
                popupWindow.dismiss();
            }
        });

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}