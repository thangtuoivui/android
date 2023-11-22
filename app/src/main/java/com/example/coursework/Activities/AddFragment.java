package com.example.coursework.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


import com.example.coursework.Databases.HikeDatabase;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;

import java.time.LocalDate;

public class AddFragment extends Fragment {

    private HikeDatabase hikeDatabase;
    private EditText editHikename, editLocation, editDate, editLength, editDesc;
    private RadioButton yes, no;

    AutoCompleteTextView editDif;

    private Button add;
    private String check = "";
    private boolean ck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);

        hikeDatabase = Room.databaseBuilder(getContext(), HikeDatabase.class, "coursework_db")
                .allowMainThreadQueries()
                .build();


        editHikename = v.findViewById(R.id.hikeName_ip);
        editLocation = v.findViewById(R.id.hikeLocation_ip);
        editDate = v.findViewById(R.id.hikeDate_ip);
        editLength = v.findViewById(R.id.hikeLength_ip);
        editDif = v.findViewById(R.id.Dif_ip);
        String arrayDiff [] = getResources().getStringArray(R.array.dif);
        ArrayAdapter<String> adapterItem = new ArrayAdapter<String>(getContext(),R.layout.autotext_card, arrayDiff);
        editDif.setAdapter(adapterItem);
        editDesc = v.findViewById(R.id.Desc_ip);

        yes = v.findViewById(R.id.bt_yes);
        no = v.findViewById(R.id.bt_no);

        add = v.findViewById(R.id.btAdd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDetails(v);
            }
        });

        editDate.setOnClickListener(view -> {
            DialogFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getChildFragmentManager(), "getDate");
        });
        return v;
    }

    private void saveDetails(View v) {

        String name = editHikename.getText().toString();
        String location = editLocation.getText().toString();
        String date = editDate.getText().toString();
        String length = editLength.getText().toString();
        String dif = editDif.getText().toString();
        String desc = editDesc.getText().toString();

        if(yes.isChecked() == true){
            check = "yes";
        }else if(no.isChecked() == true){
            check = "no";
        }

        if(check(name)||check(location)||check(date)||
                check(length)||check(dif)||check(desc)||
                check(check)){
            ck = false;
        }else {
            ck = true;
        }

        if(ck){
            Hike hike = new Hike();
            hike.name = name;
            hike.location = location;
            hike.date = date;
            hike.parking = check;
            hike.length = length;
            hike.difficulty_level = dif;
            hike.description = desc;

            hikeDatabase.hikeDao().insertHike(hike);

            new AlertDialog.Builder(getContext())
                    .setTitle("Hike detail")
                    .setMessage("Hike name: " + name + "\n" +
                            "Location: " + location + "\n" +
                            "Date: " + date + "\n" +
                            "Parking check: " + check + "\n" +
                            "Length: " + length + "\n" +
                            "Difficult level: " + dif + "\n" +
                            "Description: " + desc)
                    .setNegativeButton("OK", null)
                    .show();
        }else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Delete Contact")
                    .setMessage("Please fill all the data!")
                    .setNegativeButton("Oke", null)
                    .show();
        }
    }

    private boolean check(String c){
        return c.matches("");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
        {
            LocalDate d = LocalDate.now();
            int year = d.getYear();
            int month = d.getMonthValue();
            int day = d.getDayOfMonth();
            return new DatePickerDialog(getActivity(), this, year, --month, day);}
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day){
            ((AddFragment)getParentFragment()).updateDOB(year, ++month, day);
        }

    }
    public void updateDOB(int year, int month, int day){

        String dayZero , monthZero;
        if (day<10){
            dayZero="0";
        } else {
            dayZero="";
        }
        if (month<10){
            monthZero="0";
        } else {
            monthZero="";
        }
        TextView date = getView().findViewById(R.id.hikeDate_ip);
        date.setText(dayZero+day+"/"+monthZero+month+"/"+year);
    }
}