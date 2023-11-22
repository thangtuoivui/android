package com.example.coursework.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.coursework.Databases.HikeDatabase;
import com.example.coursework.HikeAdapter;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;


public class SearchFragment extends Fragment implements HikeAdapter.OnClickListener {
    private HikeDatabase hikeDatabase;
    private HikeAdapter hikeAdapter;
    private List<Hike> hikeList;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search, container, false);

        hikeDatabase = Room.databaseBuilder(getActivity(), HikeDatabase.class, "coursework_db")
                .allowMainThreadQueries().build();

        RecyclerView recyclerView = v.findViewById(R.id.Hike_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        MaterialButton searchBtn = v.findViewById(R.id.btSearch);
        EditText searchText = v.findViewById(R.id.search_txt);
        searchBtn.setOnClickListener(view -> {
            String name = "%" + searchText.getText().toString() + "%";
            hikeList = hikeDatabase.hikeDao().searchHikeName(name);
            hikeAdapter = new HikeAdapter(getContext(),hikeList, this);
            recyclerView.setAdapter(hikeAdapter);
        });
        return v;
    }

    public void onItemClick(Fragment fragment){
        replaceFragment(fragment);
    }

    public void sendHikeData(Hike hike, Fragment fragment){
        Bundle result = new Bundle();
        result.putLong("h_id", hike.hike_id);
        result.putString("h_name", hike.name);
        result.putString("h_location", hike.location);
        result.putString("h_date", hike.date);
        result.putString("h_parking", hike.parking);
        result.putString("h_length", hike.length);
        result.putString("h_dif", hike.difficulty_level);
        result.putString("h_desc", hike.description);
        fragment.setArguments(result);
    }
    public void onDeleteClick(Hike hike) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this " + hike.getName()+ "trip?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Remove from the database
                    hikeDatabase.hikeDao().delete(hike);
                    // Update the list
                    hikeList.remove(hike);
                    hikeAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}