package com.cs2340.app_with_realm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs2340.app_with_realm.RealmObjects.Assignment;

import java.util.List;
import android.app.AlertDialog;
import android.content.Context;

import io.realm.Realm;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<Assignment> assignments;

    public AssignmentAdapter(List<Assignment> assignments) {
        this.assignments = assignments;
    }
    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignment assignment = assignments.get(position);
        holder.title.setText(assignment.getTitle());
        holder.description.setText(assignment.getDescription());
        holder.className.setText(assignment.getClassName());
        holder.dueDate.setText(assignment.getDueDate());
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Confirm Delete")
                    .setMessage("Do you really want to delete this assignment?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        int pos = holder.getAdapterPosition();
                        String assignmentId = assignments.get(pos).getId();
                        deleteAssignment(assignmentId, pos);
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });

        holder.editButton.setOnClickListener(v -> {
            // Context is required for creating dialogs, use the one from the itemView
            Context context = holder.itemView.getContext();

            // Show the edit dialog, passing in the context, the current assignment, and its position
            showEditDialog(context, assignment, position);
        });


    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, className, dueDate;
        Button editButton, deleteButton;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.assignmentTitle);
            description = itemView.findViewById(R.id.assignmentDescription);
            className = itemView.findViewById(R.id.assignmentClass);
            dueDate = itemView.findViewById(R.id.assignmentDueDate);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
    public void deleteAssignment(String assignmentId, int position) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(r -> {
            Assignment assignment = r.where(Assignment.class).equalTo("id", assignmentId).findFirst();
            if (assignment != null) {
                assignment.deleteFromRealm();
            }
        });
        assignments.remove(position);
        notifyItemRemoved(position);
    }

    // Code for editing the fields of data for an assignment
    private void showEditDialog(Context context, Assignment assignment, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.edit_assignment, null); // Ensure this layout exists in your res/layout directory
        builder.setView(view);

        // Initialize your EditText fields from the dialog layout
        EditText titleInput = view.findViewById(R.id.editTitle);
        EditText descriptionInput = view.findViewById(R.id.editDescription);
        EditText dueDateInput = view.findViewById(R.id.editDueDate);
        EditText classInput = view.findViewById(R.id.editClass);

        // Pre-populate EditText fields with current assignment details
        titleInput.setText(assignment.getTitle());
        descriptionInput.setText(assignment.getDescription());
        dueDateInput.setText(assignment.getDueDate());
        classInput.setText(assignment.getClassName());

        // Set up the "Save" button
        builder.setPositiveButton("Save", (dialog, which) -> {
            // Extract text from EditText fields
            String newTitle = titleInput.getText().toString();
            String newDescription = descriptionInput.getText().toString();
            String newDueDate = dueDateInput.getText().toString();
            String newClass = classInput.getText().toString();
            updateAssignment(assignment.getId(), newTitle, newDescription, newClass, newDueDate, position);
        });

        // Set up the "Cancel" button
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void updateAssignment(String assignmentId, String newTitle, String newDescription, String newClass, String newDueDate, int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(r -> {
            Assignment assignment = r.where(Assignment.class).equalTo("id", assignmentId).findFirst();
            if (assignment != null) {
                // Modifications are now inside a write transaction
                assignment.setTitle(newTitle);
                assignment.setDescription(newDescription);
                assignment.setDueDate(newDueDate);
                assignment.setClassName(newClass);
            }
        }, () -> {
            // Success callback: Notify adapter and update UI here, if necessary
            // This code runs on the UI thread
            notifyItemChanged(position);
        }, error -> {
            // Error callback: Log or handle the error
            Log.e("RealmError", "Error updating assignment", error);
        });

        // It's important to close the Realm instance when done to avoid memory leaks
        realm.close();
    }



}
