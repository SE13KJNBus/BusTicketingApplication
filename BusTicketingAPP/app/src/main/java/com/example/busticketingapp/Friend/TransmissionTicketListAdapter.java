package com.example.busticketingapp.Friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.busticketingapp.Home.TicketData;
import com.example.busticketingapp.R;

import java.util.ArrayList;

public class TransmissionTicketListAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<TicketData> m_oData = null;
    private int nListCnt = 0;

    public TransmissionTicketListAdapter(ArrayList<TicketData> _oData) {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount() {
        return nListCnt;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final Context context = parent.getContext();
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.friend_transmission_ticket_data, parent, false);
        }


        TextView oTextSplace = (TextView) convertView.findViewById(R.id.startPlace);
        TextView oTextEplace = (TextView) convertView.findViewById(R.id.arrivePlace);
        TextView oTextDate = (TextView) convertView.findViewById(R.id.date);
        TextView oTextStime = (TextView) convertView.findViewById(R.id.startTime);
        TextView oTextEtime = (TextView) convertView.findViewById(R.id.endTime);
        TextView oTextCompany = (TextView) convertView.findViewById(R.id.company);
        TextView oTextTime = (TextView) convertView.findViewById(R.id.time);
        TextView oTextSeat = (TextView) convertView.findViewById(R.id.seatNum);

        Button oBtnTransmission = (Button) convertView.findViewById(R.id.btn_transmission);

        oTextSplace.setText(m_oData.get(position).startPlace);
        oTextEplace.setText(m_oData.get(position).arrivePlace);
        oTextStime.setText(m_oData.get(position).startTime);
        oTextEtime.setText(m_oData.get(position).endTime);
        oTextCompany.setText(m_oData.get(position).company);
        oTextTime.setText(m_oData.get(position).time);
        oTextDate.setText(m_oData.get(position).date);
        oTextSeat.setText(m_oData.get(position).seatNum);

        oBtnTransmission.setOnClickListener(m_oData.get(position).onClickListener);

        convertView.setTag("" + position);
        return convertView;
    }
}