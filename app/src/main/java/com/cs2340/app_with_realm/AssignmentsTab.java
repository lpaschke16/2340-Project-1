package com.cs2340.app_with_realm;

import android.app.Application;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cs2340.app_with_realm.R;
import com.cs2340.app_with_realm.RealmObjects.Assignment;
import com.cs2340.app_with_realm.databinding.AssignmentsTabBinding;

import java.util.Calendar;
import java.util.UUID;

import androidx.fragment.app.Fragment;

import io.realm.Realm;

public class AssignmentsTab extends Fragment {
    private AssignmentsTabBinding binding;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextDueDate;
    private EditText editTextClass;
    private Button saveButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.assignments_tab, container, false);
        editTextTitle = view.findViewById(R.id.editTextAssignmentTitle);
        editTextDescription = view.findViewById(R.id.editTextAssignmentDescription);
        editTextDueDate = view.findViewById(R.id.editTextDueDate);
        editTextClass = view.findViewById(R.id.editTextClass);
        saveButton = view.findViewById(R.id.buttonSaveAssignment);

        // Come back to - don't use lambda expressions
        editTextDueDate.setOnClickListener(v -> showDate());
        saveButton.setOnClickListener(v -> saveAssignment());

        return view;
    }

    public void showDate() {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = year + "/" + (month + 1) + "/" + dayOfMonth;
                    editTextDueDate.setText(selectedDate);
                },
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void saveAssignment() {
        String assignmentTitle = editTextTitle.getText().toString().trim();
            String assignmentDescription = editTextDescription.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();
        String className = editTextClass.getText().toString().trim();

        // error handling for when a field is not inputted
        if (assignmentTitle.isEmpty() || dueDate.isEmpty() || className.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all required fields!", Toast.LENGTH_SHORT).show();
        }

        // Saving the assignment to the Realm
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Assignment assignment = realm1.createObject(Assignment.class, UUID.randomUUID().toString());
            assignment.setTitle(assignmentTitle);
            assignment.setClassName(className);
            assignment.setDescription(assignmentDescription);
            assignment.setDueDate(dueDate);
        });

        // A message indicating that the assignment has been added
        Toast.makeText(getContext(), "Assignment successfully added!", Toast.LENGTH_SHORT).show();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initializing the Realm for data storage

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
