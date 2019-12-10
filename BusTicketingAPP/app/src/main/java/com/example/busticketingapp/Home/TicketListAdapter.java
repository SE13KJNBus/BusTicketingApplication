package com.example.busticketingapp.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.busticketingapp.R;

import java.util.ArrayList;

public class TicketListAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<TicketData> m_oData = null;
    private int nListCnt = 0;

    public TicketListAdapter(ArrayList<TicketData> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        return nListCnt;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.home_ticket_list, parent, false);
        }


        TextView oTextArea = (TextView) convertView.findViewById(R.id.area);
        TextView oTextDate = (TextView) convertView.findViewById(R.id.date);
        TextView oTextSeat = (TextView) convertView.findViewById(R.id.seatNum);
        Button oBtnChange = (Button)convertView.findViewById(R.id.btn_change);
        Button oBtnCancel = (Button)convertView.findViewById(R.id.btn_cancel);

        oTextArea.setText(m_oData.get(position).Area);
        oTextDate.setText(m_oData.get(position).Date);
        oTextSeat.setText(m_oData.get(position).SeatNum);
        oBtnChange.setOnClickListener(m_oData.get(position).onClickListener);
        oBtnCancel.setOnClickListener(m_oData.get(position).onClickListener);

        convertView.setTag(""+position);
        return convertView;
    }
}
