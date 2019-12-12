package com.example.busticketingapp.Cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.busticketingapp.R;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    ArrayList<CartData> cart_itemArrayList;

    public void setCart_itemArrayList(ArrayList<CartData> givenCart_itemArrayList){
        this.cart_itemArrayList = givenCart_itemArrayList;
    }
    public ArrayList<CartData> getCart_itemArrayList(){
        return this.cart_itemArrayList;
    }

    CartAdapter(ArrayList<CartData> cart_itemArrayList){
        this.cart_itemArrayList = cart_itemArrayList;
    }
    @Override
    public int getCount() {
        return cart_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.cart_cart_item, parent, false);
        }

        int seatNum;
        TextView startPlaceVal = (TextView) convertView.findViewById(R.id.startPlaceValue);
        TextView arrivePlaceVal = (TextView) convertView.findViewById(R.id.arrivePlaceValue);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView startTimeVal = (TextView) convertView.findViewById(R.id.startTimeValue);
        TextView arriveTimeVal = (TextView) convertView.findViewById(R.id.arriveTimeValue);
        TextView movingTimeVal = (TextView) convertView.findViewById(R.id.movingTimeValue);
        TextView busCompanyVal = (TextView) convertView.findViewById(R.id.busCompanyValue);
        TextView seatNumberVal = (TextView) convertView.findViewById(R.id.seatNumberValue);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);

        startPlaceVal.setText(cart_itemArrayList.get(position).startPlace);
        arrivePlaceVal.setText(cart_itemArrayList.get(position).arrivePlace);
        date.setText(cart_itemArrayList.get(position).date);
        startTimeVal.setText(cart_itemArrayList.get(position).startTime);
        arriveTimeVal.setText(cart_itemArrayList.get(position).arriveTime);
        movingTimeVal.setText(cart_itemArrayList.get(position).movingTime);
        busCompanyVal.setText(cart_itemArrayList.get(position).busCompany);
        seatNumberVal.setText(new Integer((cart_itemArrayList.get(position).seatNum)).toString());
        checkBox.setChecked(cart_itemArrayList.get(position).checkBoxVal);
        checkBox.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    checkBox.setChecked(true);
                    cart_itemArrayList.get(position).checkBoxVal = true;
                }
                else{
                    checkBox.setChecked(false);
                    cart_itemArrayList.get(position).checkBoxVal = false;
                }
            }
        });
        return convertView;
    }
}
