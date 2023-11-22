package com.example.coursework.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.coursework.Databases.HikeDatabase;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;

import java.time.LocalDate;


public class EditFragment extends Fragment {

    private String name, location, date, parking, length, dif, desc;
    private long id;

    private EditText hName, hLocation, hDate, hLength, hDesc;
    private AutoCompleteTextView hDif;
    private RadioButton yes, no;

    private Button edit;

    private HikeDatabase hikeDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit, container, false);

        hikeDatabase = Room.databaseBuilder(getContext(), HikeDatabase.class, "coursework_db")
                .allowMainThreadQueries()
                .build();

        setDataEdit(v);

        edit = v.findViewById(R.id.btEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean ck;
                getDataEdit();
                if(check(name)||check(location)||check(date)||
                        check(length)||check(dif)){
                    ck = false;
                }else {
                    ck = true;
                }
                if(ck){
                    Hike hike = new Hike(id, name, location, date, parking, length, dif, desc);
                    onUpdateClick(hike);
                }else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Data fill Error")
                            .setMessage("Please fill all text box!")
                            .setNegativeButton("Cancel", null)
                            .show();
                }

            }
        });

        hDate.setOnClickListener(view -> {
            DialogFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getChildFragmentManager(), "getDate");
        });

        return v;
    }
    public void onUpdateClick(Hike hike) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to update this " + hike.getName()+ "trip?")
                .setPositiveButton("Update", (dialog, which) -> {

                    hikeDatabase.hikeDao().updateHike(hike);
                    replaceFragment(new HomeFragment());

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void getDataEdit(){
        name = hName.getText().toString();
        location = hLocation.getText().toString();
        date = hDate.getText().toString();
        if(yes.isChecked() == true){
            parking = "yes";
        }else {
            parking = "no";
        }

        length = hLength.getText().toString();
        dif = hDif.getText().toString();
        desc = hDesc.getText().toString();
    }

    public void setDataEdit(View v){
        hName = v.findViewById(R.id.editName);
        hLocation = v.findViewById(R.id.editHikeLocation);
        hDate = v.findViewById(R.id.editHikeDate);
        hLength = v.findViewById(R.id.editHikeLength);
        hDif = v.findViewById(R.id.editDif);
        hDesc = v.findViewById(R.id.editDesc);


        id = getArguments().getLong("h_id");
        name = getArguments().getString("h_name");
        location = getArguments().getString("h_location");
        date = getArguments().getString("h_date");
        parking = getArguments().getString("h_parking");
        length = getArguments().getString("h_length");
        dif = getArguments().getString("h_dif");
        desc = getArguments().getString("h_desc");

        yes = v.findViewById(R.id.bt_yes_edit);
        no = v.findViewById(R.id.bt_no_edit);

        hName.setText(name);
        hLocation.setText(location);
        hDate.setText(date);
        hLength.setText(length);
        hDif.setText(dif);
        hDesc.setText(desc);

        if(parking == "yes"){
            yes.setChecked(true);
        }else {
            no.setChecked(true);
        }



        String[] itemList = getResources().getStringArray(R.array.dif);
        AutoCompleteTextView autoCompleteTextView = v.findViewById(R.id.editDif);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.autotext_card, itemList);
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
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
            ((EditFragment)getParentFragment()).setDOB(year, ++month, day);
        }

    }
    public void setDOB(int year, int month, int day){

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
        EditText date = getView().findViewById(R.id.editHikeDate);
        date.setText(dayZero+day+"/"+monthZero+month+"/"+year);
    }

    private boolean check(String c){
        return c.matches("");
    }
}

