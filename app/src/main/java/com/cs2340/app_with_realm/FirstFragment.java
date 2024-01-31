package com.cs2340.app_with_realm;

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
    private Realm realm;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        realm = Realm.getDefaultInstance();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RealmResults<Course> Courses = realm.where(Course.class).findAll();
        et = view.findViewById(R.id.idEdtItemName);
        lv = view.findViewById(R.id.list);
        lv.setAdapter(new CourseContainer(Courses, getContext()));

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String newCourseName = et.getText().toString();
//                Course course = new Course(newCourseName, "", "", "");
//                et.setText("");
//                realm.executeTransaction (transactionRealm -> {
//                    transactionRealm.insert(course);
//                });
//
//                Bundle bundle = new Bundle();
//                bundle.putString("CourseId", "012");
////
//                Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_courseScreen, bundle);
                onButtonShowPopupWindowClick(view);
            }
        });
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = getLayoutInflater();
//                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

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