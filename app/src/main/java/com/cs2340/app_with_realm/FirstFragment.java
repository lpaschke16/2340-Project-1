package com.cs2340.app_with_realm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cs2340.app_with_realm.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ListView languageLV;
    private Button addBtn;
    private EditText itemEdt;
    private ArrayList<String> lngList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);
//        // on below line we are initializing our variables.
//        languageLV = container.findViewById(R.id.idLVLanguages);
//        addBtn = container.findViewById(R.id.idBtnAdd);
//        itemEdt = container.findViewById(R.id.idEdtItemName);
//        lngList = new ArrayList<>();
//
//        // on below line we are adding items to our list
//        lngList.add("C++");
//        lngList.add("Python");
//
//        // on the below line we are initializing the adapter for our list view.
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, lngList);
//
//        // on below line we are setting adapter for our list view.
//        languageLV.setAdapter(adapter);
//
//        // on below line we are adding click listener for our button.
//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // on below line we are getting text from edit text
//                String item = itemEdt.getText().toString();
//
//                // on below line we are checking if item is not empty
//                if (!item.isEmpty()) {
//
//                    // on below line we are adding item to our list.
//                    lngList.add(item);
//
//                    // on below line we are notifying adapter
//                    // that data in list is updated to
//                    // update our list view.
//                    adapter.notifyDataSetChanged();
//                }
//
//            }
//        });



        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}