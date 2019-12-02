package com.example.busticketingapp.Friend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.busticketingapp.R;

import java.util.ArrayList;

public class FriendList extends AppCompatActivity implements View.OnClickListener{

    private ListView m_oListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        String[] strFriendName = {"권은경", "곽주헌", "권유나", "김수빈"};
        String[] strFriendEmail = {"e@naver.com", "j@naver.com", "y@naver.com", "s@naver.com"};

        int nDatCnt=0;
        ArrayList<FriendData> oData = new ArrayList<>();

        for (int i=0; i<4; ++i)
        {
            FriendData oItem = new FriendData();
            oItem.friendName = strFriendName[nDatCnt];
            oItem.friendEmail = strFriendEmail[nDatCnt++];
            oItem.onClickListener = this;
            oData.add(oItem);

            if (nDatCnt >= strFriendName.length) nDatCnt = 0;
        }

// ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView)findViewById(R.id.friend_listset);
        FriendListAdapter oAdapter = new FriendListAdapter(oData);
        m_oListView.setAdapter(oAdapter);

    }

    @Override
    public void onClick(View v){
        View oParentView = (View)v.getParent(); // parents의 View를 가져온다.
        TextView oTextTitle = (TextView) oParentView.findViewById(R.id.friendName);
        String position = (String) oParentView.getTag();

        AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog);

        String strMsg = oTextTitle.getText()+" 님에게 \n표를 양도하시겠습니까?";
        oDialog.setMessage(strMsg)
                .setPositiveButton("확인", null)
                .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                .show();

    }
}
