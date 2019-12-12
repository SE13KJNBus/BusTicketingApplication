package com.example.busticketingapp.MailBox;

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

public class MailAdapter extends BaseAdapter{

    LayoutInflater inflater = null;
    private ArrayList<MailData> m_oData = null;
    private int nListCnt = 0;

    public MailAdapter(ArrayList<MailData> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount() { return nListCnt; }

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

        if (m_oData.get(position).message.toString().equals("친구요청")){
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.mailbox_friend, parent, false);

            TextView oTextfName = (TextView) convertView.findViewById(R.id.senderName);
            TextView oTextfEmail = (TextView) convertView.findViewById(R.id.senderEmail);
            TextView oTextfDate = (TextView) convertView.findViewById(R.id.sendDate);
            TextView oTextMessage = (TextView) convertView.findViewById(R.id.message);

            Button oBtnac = (Button)convertView.findViewById(R.id.btn_accept);
            Button oBtnre = (Button)convertView.findViewById(R.id.btn_reject);

            oTextfName.setText(m_oData.get(position).senderName);
            oTextfEmail.setText(m_oData.get(position).senderEmail);
            oTextfDate.setText(m_oData.get(position).senddate);
            oTextMessage.setText(m_oData.get(position).message);
            oBtnac.setOnClickListener(m_oData.get(position).onClickListener);
            oBtnre.setOnClickListener(m_oData.get(position).onClickListener);

//            convertView.setTag(""+position);
//            return convertView;
        }else if(m_oData.get(position).message.toString().equals("표양도거절")){
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.mailbox_ticket_reject, parent, false);

            TextView oTextfName = (TextView) convertView.findViewById(R.id.senderName);
            TextView oTextfEmail = (TextView) convertView.findViewById(R.id.senderEmail);
            TextView oTextfDate = (TextView) convertView.findViewById(R.id.sendDate);
            TextView oTextMessage = (TextView) convertView.findViewById(R.id.message);
            Button oBtnac = (Button)convertView.findViewById(R.id.btn_check);

            oTextfName.setText(m_oData.get(position).senderName);
            oTextfEmail.setText(m_oData.get(position).senderEmail);
            oTextfDate.setText(m_oData.get(position).senddate);
            oTextMessage.setText(m_oData.get(position).message);
            oBtnac.setOnClickListener(m_oData.get(position).onClickListener);

        }else if (m_oData.get(position).message.toString().equals("표양도")){
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.mailbox_ticket_tran, parent, false);

            TextView oTextfName = (TextView) convertView.findViewById(R.id.senderName);
            TextView oTextfEmail = (TextView) convertView.findViewById(R.id.senderEmail);
            TextView oTextfDate = (TextView) convertView.findViewById(R.id.sendDate);
            TextView oTextMessage = (TextView) convertView.findViewById(R.id.message);

            TextView oTextstartPlace = (TextView) convertView.findViewById(R.id.startPlace);
            TextView oTextarrivePlace = (TextView) convertView.findViewById(R.id.arrivePlace);

            TextView oTextfMedate = (TextView) convertView.findViewById(R.id.date);
            TextView oTextstartTime = (TextView) convertView.findViewById(R.id.startTime);
            TextView oTextendTime = (TextView) convertView.findViewById(R.id.endTime);

            TextView oTextcompany = (TextView) convertView.findViewById(R.id.company);
            TextView oTexttime = (TextView) convertView.findViewById(R.id.time);
            TextView oTextseatNum = (TextView) convertView.findViewById(R.id.seatNum);


            Button oBtnac = (Button)convertView.findViewById(R.id.btn_accept);
            Button oBtnre = (Button)convertView.findViewById(R.id.btn_reject);

            oTextfName.setText(m_oData.get(position).senderName);
            oTextfEmail.setText(m_oData.get(position).senderEmail);
            oTextfDate.setText(m_oData.get(position).senddate);
            oTextMessage.setText(m_oData.get(position).message);

            oTextstartPlace.setText(m_oData.get(position).startPlace);
            oTextarrivePlace.setText(m_oData.get(position).arrivePlace);

            oTextfMedate.setText(m_oData.get(position).date);
            oTextstartTime.setText(m_oData.get(position).startTime);
            oTextendTime.setText(m_oData.get(position).endTime);

            oTextcompany.setText(m_oData.get(position).company);
            oTexttime.setText(m_oData.get(position).time);
            oTextseatNum.setText(m_oData.get(position).seatNum);

            oBtnac.setOnClickListener(m_oData.get(position).onClickListener);
            oBtnre.setOnClickListener(m_oData.get(position).onClickListener);

//            convertView.setTag(""+position);
//            return convertView;
        }

        convertView.setTag(position+"");
        Log.i("position",position+"");
        return convertView;
    }
}



