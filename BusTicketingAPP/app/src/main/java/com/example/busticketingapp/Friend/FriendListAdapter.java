package com.example.busticketingapp.Friend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.busticketingapp.Payment.WaitingTicketData;
import com.example.busticketingapp.R;

import java.util.ArrayList;

public class FriendListAdapter  extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<FriendData> m_oData = null;
    private int nListCnt = 0;

    public FriendListAdapter(ArrayList<FriendData> _oData)
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
            convertView = inflater.inflate(R.layout.fiend_member, parent, false);
        }


        TextView oTextfName = (TextView) convertView.findViewById(R.id.friendName);
        TextView oTextfEmail = (TextView) convertView.findViewById(R.id.friendEmail);
        Button oBtn = (Button)convertView.findViewById(R.id.btn_transfer);

        oTextfName.setText(m_oData.get(position).friendName);
        oTextfEmail.setText(m_oData.get(position).friendEmail);
        oBtn.setOnClickListener(m_oData.get(position).onClickListener);

        convertView.setTag(""+position);
        return convertView;
    }
}



