package com.example.coursework.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.coursework.Databases.HikeDatabase;
import com.example.coursework.HikeAdapter;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HikeAdapter.OnClickListener{

    private List<Hike> list;

    private HikeDatabase hikeDatabase;
    private View v;

    private HikeAdapter hikeAdapter;

    private Button delALL;

    protected FragmentActivity mActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoadAllHike();

        initRecyclerView();

        delALL = v.findViewById(R.id.btDelALL);
        delALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteALLClick();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentActivity){
            mActivity =(FragmentActivity) context;
        }
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = v.findViewById(R.id.Hike_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        hikeAdapter = new HikeAdapter(getContext(),list,this);
        recyclerView.setAdapter(hikeAdapter);
    }


    private void LoadAllHike(){
        hikeDatabase = Room.databaseBuilder(getContext(), HikeDatabase.class, "coursework_db")
                .allowMainThreadQueries()
                .build();
        list = hikeDatabase.hikeDao().getAllHIkes();
        hikeAdapter = new HikeAdapter(getContext(),list, this);
    }

    public void onDeleteALLClick(){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete all hike?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    hikeDatabase.hikeDao().deleteALl();
                    list.removeAll(list);
                    hikeAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
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


    public void onItemClick(Fragment fragment){
        replaceFragment(fragment);
    }

    public void onDeleteClick(Hike hike) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this " + hike.getName()+ "trip?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Remove from the database
                    hikeDatabase.hikeDao().delete(hike);
                    // Update the list
                    list.remove(hike);
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