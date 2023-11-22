package com.example.coursework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;


import com.example.coursework.Activities.EditFragment;
import com.example.coursework.Activities.ObservationFragment;
import com.example.coursework.Databases.HikeDatabase;
import com.example.coursework.Models.Hike;


import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.UserViewHolder> {

    private OnClickListener onClickListener;
    Context context;
    private List<Hike> mListHike;
    public interface OnClickListener {
        void onDeleteClick(Hike hike);
        void onItemClick(Fragment fragment);
        void sendHikeData(Hike hike, Fragment fragment);
    }

    public HikeAdapter(Context context,List<Hike> mListHike, OnClickListener onClickListener) {
        this.mListHike = mListHike;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Hike hike = mListHike.get(position);
        if(hike == null){
            return;
        }
        holder.tvHikename.setText(hike.getName());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onDeleteClick(hike);
                }
            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onItemClick(new ObservationFragment());
                }
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    Fragment fragment = new EditFragment();
                    onClickListener.sendHikeData(hike, fragment);
                    onClickListener.onItemClick(fragment);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListHike != null){
            return mListHike.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout linearLayout;
        private TextView tvHikename;
        private Button more, del;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvHikename = itemView.findViewById(R.id.hike_name);
            more = itemView.findViewById(R.id.btMore);
            del = itemView.findViewById(R.id.btDel);
            linearLayout = itemView.findViewById(R.id.item_card);
        }
    }
}
