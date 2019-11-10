package com.example.busticketingapp.BusList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.busticketingapp.R;

import java.util.ArrayList;


public class CustomAdapter_forBusList extends RecyclerView.Adapter<CustomAdapter_forBusList.CustomViewHolder> {

    private ArrayList<Bus> mList;

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
        protected TextView busGrade;
        protected TextView remainSeat;

        public CustomViewHolder(View view) {
            super(view);
            this.departureTime = view.findViewById(R.id.BusTime);
            this.busCompany = view.findViewById(R.id.BusCompany);
            this.busGrade = view.findViewById(R.id.BusGrade);
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
        viewholder.departureTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.departureTime.setGravity(Gravity.CENTER);
        viewholder.departureTime.setText(mList.get(position).getDepartureTime());

        viewholder.busCompany.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.busCompany.setGravity(Gravity.CENTER);
        viewholder.busCompany.setText(mList.get(position).getBusCompany());

        viewholder.busGrade.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.busGrade.setGravity(Gravity.CENTER);
        viewholder.busGrade.setText(mList.get(position).getBusGrade());

        viewholder.remainSeat.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.remainSeat.setGravity(Gravity.CENTER);
        viewholder.remainSeat.setText(mList.get(position).getRemainSeat()+"");
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}