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

public class CustomAdapter_forTerminal extends RecyclerView.Adapter<CustomAdapter_forTerminal.CustomViewHolder> {

    private ArrayList<String> mList;

    private OnItemClickListener mListener = null;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;

        public CustomViewHolder(View view) {
            super(view);
            this.id = view.findViewById(R.id.id_listitem);
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
    public CustomAdapter_forTerminal(ArrayList<String> list) {
        this.mList = list;
    }

    public void addItem(String locationTerminal){
        mList.add(locationTerminal);
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.buslist_select_location_item, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.id.setGravity(Gravity.CENTER);
        String[] splitString = mList.get(position).split(":");
        viewholder.id.setText(splitString[1]);
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}