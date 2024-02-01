package com.cs2340.app_with_realm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.cs2340.app_with_realm.RealmObjects.Course;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class CourseContainer extends BaseAdapter {
    private RealmResults<Course> list;
    private Context context;

    public CourseContainer(RealmResults<Course> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.course_container, null);
        }

        TextView tvContact= view.findViewById(R.id.tvContact);
        tvContact.setText(list.get(position).name);

        Button navigateBtn = view.findViewById(R.id.navigate);
        Button deleteBtn = view.findViewById(R.id.deleteButton);
        Button editBtn = view.findViewById(R.id.editButton);

        navigateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Course courseObjc = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("courseName", courseObjc.name);
                bundle.putString("instructor", courseObjc.instructor);
                bundle.putString("time", courseObjc.time);
                bundle.putString("location", courseObjc.location);
                Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_courseScreen, bundle);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction (transactionRealm -> {
                    list.get(position).deleteFromRealm();
                });
                FirstFragment.getInstance().refreshPage();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                FirstFragment.getInstance().onButtonShowPopupWindowClick(view, true, list.get(position));
            }
        });




        return view;
    }
}