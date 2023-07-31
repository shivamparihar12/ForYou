package com.example.foryou.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foryou.FirstFragmentDirections;
import com.example.foryou.R;
import com.example.foryou.retrofit.retrofitmodel.UserMeetData;

import java.util.ArrayList;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<UserMeetData> userMeetDataArrayList;
    private Fragment fragment;

    public UserDataAdapter(Context mContext, Fragment fragment, ArrayList<UserMeetData> userMeetDataArrayList) {
        this.mContext = mContext;
        this.userMeetDataArrayList = userMeetDataArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user_meet_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleName.setText(userMeetDataArrayList.get(position).getTitle());
        holder.date.setText((userMeetDataArrayList.get(position).getDate()));
        holder.titleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavHostFragment.findNavController(fragment).navigate(R.id.action_FirstFragment_to_SecondFragment);
                NavDirections action= FirstFragmentDirections.actionFirstFragmentToSecondFragment(userMeetDataArrayList.get(position));
                NavHostFragment.findNavController(fragment).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userMeetDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleName, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleName = itemView.findViewById(R.id.file_title);
            date = itemView.findViewById(R.id.file_date);
        }
    }
}


