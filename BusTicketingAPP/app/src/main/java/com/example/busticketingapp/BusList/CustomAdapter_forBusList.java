package com.example.busticketingapp.BusList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.busticketingapp.R;

import java.util.ArrayList;


public class CustomAdapter_forBusList extends RecyclerView.Adapter<CustomAdapter_forBusList.CustomViewHolder> {

    public ArrayList<Bus> mList;

    private OnItemClickListener mListener = null;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView departureTime;
        protected TextView busCompany;
        protected TextView remainSeat;

        public CustomViewHolder(View view) {
            super(view);
            this.departureTime = view.findViewById(R.id.BusTime);
            this.busCompany = view.findViewById(R.id.BusCompany);
            this.remainSeat = view.findViewById(R.id.remainSeatNum);

            view.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v,pos);
                        }
                    }
                }
            });
        }
    }
    public CustomAdapter_forBusList(ArrayList<Bus> list) {
        this.mList = list;
    }
    public void addItem(Bus individualBus){
        mList.add(individualBus);
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.buslist_select_busitem, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.departureTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        viewholder.departureTime.setGravity(Gravity.CENTER);
        String start = mList.get(position).getDepartureTime().split("-")[0];
        String end = mList.get(position).getDepartureTime().split("-")[1];

        int start2Int = Integer.parseInt(start.split(":")[0])*60+Integer.parseInt(start.split(":")[1]);
        int end2Int = Integer.parseInt(end.split(":")[0])*60+Integer.parseInt(end.split(":")[1]);
        String movingTime = String.format("%02d:%02d",((int)(end2Int-start2Int)/60),(end2Int-start2Int)%60);
        viewholder.departureTime.setText(start+" ~ "+end+" ( "+movingTime+" 소요 )");
        viewholder.busCompany.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        viewholder.busCompany.setGravity(Gravity.CENTER);
        viewholder.busCompany.setText(mList.get(position).getBusCompany());

        viewholder.remainSeat.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        viewholder.remainSeat.setGravity(Gravity.CENTER);
        viewholder.remainSeat.setText(mList.get(position).getRemainSeat()+" 석 ");
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}