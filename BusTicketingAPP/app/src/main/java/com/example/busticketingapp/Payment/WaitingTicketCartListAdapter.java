package com.example.busticketingapp.Payment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.busticketingapp.R;

import java.util.ArrayList;

public class WaitingTicketCartListAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<WaitingTicketData> m_oData = null;
    private int nListCnt = 0;

    public WaitingTicketCartListAdapter(ArrayList<WaitingTicketData> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
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
            convertView = inflater.inflate(R.layout.payment_waiting_ticket_cart_list, parent, false);
        }


        TextView oTextArea = (TextView) convertView.findViewById(R.id.area);
        TextView oTextDate = (TextView) convertView.findViewById(R.id.date);
        TextView oTextSeat = (TextView) convertView.findViewById(R.id.seatNum);
        Button oBtn = (Button)convertView.findViewById(R.id.btn_change);
        TextView oTextCompany = (TextView) convertView.findViewById(R.id.company);

        oTextArea.setText(m_oData.get(position).Area);
        oTextDate.setText(m_oData.get(position).Date);
        oTextSeat.setText(m_oData.get(position).SeatNum);
        oTextCompany.setText(m_oData.get(position).Company);
        oBtn.setOnClickListener(m_oData.get(position).onClickListener);


        convertView.setTag(""+position);
        return convertView;
    }
}


