package com.cs2340.app_with_realm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
    private Button addBtn;
    private EditText itemEdt;
    private ArrayList<String> lngList;
    private ListView lv;
    private Realm realm;
    private EditText et;
    String tutorials[]
            = { "Algorithms", "Data Structures",
            "Languages", "Interview Corner",
            "GATE", "ISRO CS",
            "UGC NET CS", "CS Subjects",
            "Web Technologies" };

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
                String newCourseName = et.getText().toString();
                Course course = new Course(newCourseName, "", "", "");
                et.setText("");
                realm.executeTransaction (transactionRealm -> {
                    transactionRealm.insert(course);
                });
//                Bundle bundle = new Bundle();
//                bundle.putString("CourseName", newCourseName);
                Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_courseScreen);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}