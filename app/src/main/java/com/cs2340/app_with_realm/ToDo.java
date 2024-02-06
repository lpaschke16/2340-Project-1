package com.cs2340.app_with_realm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs2340.app_with_realm.RealmObjects.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ToDo extends Fragment {
    private Realm realm;
    private RecyclerView tasksRecyclerView;
    private Button addTaskButton;
    private TaskContainer adapter;
    EditText taskInput;
    EditText taskEditText;

    private RealmResults<Task> taskList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_do, container, false);
        taskInput = view.findViewById(R.id.taskEditText);
        realm = Realm.getDefaultInstance();
        addTaskButton = view.findViewById(R.id.addTaskButton);
        tasksRecyclerView = view.findViewById(R.id.tasksRecyclerView);

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addTaskButton.setOnClickListener(v -> {
            String taskName = taskInput.getText().toString().trim();
            if (!taskName.isEmpty()) {
                addTask(taskName);
            }
        });

        taskList = realm.where(Task.class).findAllAsync();
        taskList.addChangeListener(new RealmChangeListener<RealmResults<Task>>() {
            @Override
            public void onChange(RealmResults<Task> tasks) {
                adapter.updateData(tasks);
            }
        });

        adapter = new TaskContainer(taskList);
        tasksRecyclerView.setAdapter(adapter);


        return view;
    }


    private void addTask(String taskName) {
        realm.executeTransactionAsync(realm -> {
            Task task = realm.createObject(Task.class, UUID.randomUUID().toString());
            task.setName(taskName);
            task.setCompleted(false);
        }, () -> {
            loadTasks();
        });
    }

    private void loadTasks() {
        List<Task> tasks = realm.copyFromRealm(realm.where(Task.class).findAll());
        adapter.updateData(tasks);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskEditText = view.findViewById(R.id.taskEditText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }


}
