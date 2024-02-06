package com.cs2340.app_with_realm;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs2340.app_with_realm.RealmObjects.Assignment;
import com.cs2340.app_with_realm.RealmObjects.Task;

import org.w3c.dom.Text;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;


public class TaskContainer extends RecyclerView.Adapter<TaskContainer.TaskViewHolder> {

    private Realm realm;
    private static List<Task> taskList;
    private Context context;

    public TaskContainer(List<Task> tasks) {
        taskList = tasks;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskName.setText(task.getName());
        holder.completeCheckBox.setOnCheckedChangeListener(null);
        holder.completeCheckBox.setChecked(task.isCompleted());
        holder.completeCheckBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                Realm realm = Realm.getDefaultInstance();
                final String taskId = task.getId();

                realm.executeTransactionAsync(r -> {
                    Task taskToDelete = r.where(Task.class).equalTo("id", taskId).findFirst();
                    if (taskToDelete != null) {
                        taskToDelete.deleteFromRealm();
                    }
                }, () -> {
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        taskList.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, taskList.size());
                    }
                    realm.close();
                });
            }

        }));

        holder.editButton.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            showEditDialog(context, task, position);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        Button editButton;
        CheckBox completeCheckBox;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            editButton = itemView.findViewById(R.id.editTaskButton);
            completeCheckBox = itemView.findViewById(R.id.taskCompletedCheckBox);

            completeCheckBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Task task = taskList.get(pos);
                    taskCompleted(task.getId(), isChecked);
                }
            }));

        }
    }

    // Mark a task as completed
    public static void taskCompleted(String taskId, boolean completed) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(realm1 -> {
            Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
            if (task != null) {
                task.setCompleted(completed);
            }
        });
    }

    // Code for editing a task in the to do list
    public void showEditDialog(Context context, Task task, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.edit_task, null);
        builder.setView(view);

        EditText titleInput = view.findViewById(R.id.editTaskTitle);
        titleInput.setText(task.getName());


        // Save changes
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newTitle = titleInput.getText().toString();

            editTask(task.getId(), newTitle);
        });

        // Cancel Changes
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editTask(String taskId, String newTitle) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(r -> {
            Task task = r.where(Task.class).equalTo("id", taskId).findFirst();
            if (task != null) {
                task.setName(newTitle);
            }
        });
    }

    public void updateData(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }


}
