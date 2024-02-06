package com.cs2340.app_with_realm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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

import com.cs2340.app_with_realm.RealmObjects.Course;
import com.cs2340.app_with_realm.RealmObjects.Exam;

import io.realm.Realm;
import io.realm.RealmResults;


public class ExamsTab extends Fragment {
    private static ExamsTab instance;
    private Realm realm;
    private Button addExamBtn;
    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.exams_tab, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        MainActivity.setCurFragment("ExamsTab");
        RealmResults<Exam> Exams = realm.where(Exam.class).findAll();
        lv = view.findViewById(R.id.examList);
        lv.setAdapter(new ExamContainer(Exams, getContext()));

        addExamBtn = view.findViewById(R.id.addExam);
        addExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick(view, false, null);
            }
        });
    }
    public static ExamsTab getInstace() {
        return instance;
    }
    public void refreshPage() {
        FragmentActivity activity = getActivity();
        activity.recreate();
    }
    public void onButtonShowPopupWindowClick(View view, boolean isEdit, Exam exam) {
        // inflate the layout of the popup window
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.exam_popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText nameText = popupView.findViewById(R.id.examNameInput);
        EditText dateText = popupView.findViewById(R.id.examDateInput);
        EditText timeText = popupView.findViewById(R.id.examTimeInput);
        EditText locationText = popupView.findViewById(R.id.examLocationInput);
        Button submitButton = popupView.findViewById(R.id.examSubmitButton);

        if (isEdit) {
            nameText.setText(exam.name);
            dateText.setText(exam.date);
            timeText.setText(exam.time);
            locationText.setText(exam.location);
        }
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View innerView) {
                String newName = nameText.getText().toString();
                String newDate = dateText.getText().toString();
                String newTime = timeText.getText().toString();
                String newLocation = locationText.getText().toString();

                if (isEdit) {
                    realm.executeTransaction (transactionRealm -> {
                        exam.setName(newName);
                        exam.setTime(newTime);
                        exam.setDate(newDate);
                        exam.setLocation(newLocation);
                    });
                    refreshPage();
                } else {
                    Exam newExam = new Exam(newName, newDate, newTime, newLocation);
                    realm.executeTransaction(transactionRealm -> {
                        transactionRealm.insert(newExam);
                    });
                    refreshPage();
                }
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
    }


}