package com.example.busticketingapp.BusList;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.busticketingapp.R;

import java.util.ArrayList;



public class SelectDepartureActivity extends AppCompatActivity {

    private ArrayList<String> mArrayList;
    private CustomAdapter_forTerminal mAdapter;
    private int count = -1;

    Intent gotoTerminal;
    ArrayList<String[]> entireLocation;
    String[] Seoul = {"동서울","서울남부","가락시장","김포공항","상봉","잠실역","잠실역(중앙)","장지역"};
    String[] Incheon ={"인천공항1터미널","인천공항2터미널","인천","성남","계양(계산)","고양(백석)","고양(화정)","광명","광명(철산역)","광주(경기)","구리","금촌",
            "동두천","부천","서수원","세마역(오산)","송도환승센터","송탄","수원터미널","시흥","신갈(용인)","안산터미널","안성","안양","안양역","여주","영통","오산","용인",
            "운정(경기)","의정부","이천","일동","평택","하남BRT","호계동"};
    String[] Gangwon ={"원주","강릉시외터미널","춘천","간성","거진","낙산","동송","동해","물치","삼척","속초","신고한","양구","양양","영월","오색","와수리",
            "원통","장평","장호","정선","주문진","진부","태백","평창","하조대","호산","홍천","횡계","횡성"};
    String[] Daejeon = {"대전복합","대전도룡","대전서남부","대전청사","대전청사(공항선)","북대전IC","세종","세종청사","유성","자운대","조치원"};
    String[] Chungnam = {"천안","계룡금암","고덕","고북","공주","공주(산성)","금산","기지시","남면(태안)","내포시","논산","당진","덕산스파","동학사","만리포",
            "보령(대천)","부여","삽교천","서산","서천","신례원","신평","아산(온양)","안면도","엄사","예산","운산","음암","창기리","천안아산(KTX)역","청양",
            "태안","한서대","합덕","해미","홍성"};
    String[] Chungbuk ={"청주","괴산","남청주","단양","대소","북청주","삼성","수안보","영동","오창산단","음성","제천","진천","청주공항","청주사창","충주"};
    String[] Gwangju = {"광주(유스퀘어)","목포","강진(전남)","고흥","구례","김대중컨벤션센터","나로도","나주","나주혁신도시","남선","남악","남악도청","녹동",
            "담양","당목","땅끝"};
    String[] Jeonbuk ={"전주(리무진터미널)","전주시외터미널","고창","군산","군산고속","김제","남원","대야","무주","부안","순창","완주혁신도시","익산","익산고속",
            "익산왕궁","익산IC","인월","임실","장계","장수","전북(완주)혁신도시","전주대","정읍","진안","호남제일문",};
    String[] Busan = {};
    String[] Daegu ={};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buslist_select_departure);
        Log.v("Subin","Departure select activity");

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_location_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        entireLocation=new ArrayList<>();

        entireLocation.add(Seoul);
        entireLocation.add(Incheon);
        entireLocation.add(Gangwon);
        entireLocation.add(Daejeon);
        entireLocation.add(Chungnam);
        entireLocation.add(Chungbuk);
        entireLocation.add(Gwangju);
        entireLocation.add(Jeonbuk);
        entireLocation.add(Busan);
        entireLocation.add(Daegu);
        mArrayList = new ArrayList<>();

        for(int i=0;i<entireLocation.size();i++){
            for(int j=0;j<entireLocation.get(i).length;j++){
                String[] terminal = (String[])entireLocation.get(i);
                mArrayList.add(terminal[j]);
            }
        }
        mAdapter = new CustomAdapter_forTerminal(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CustomAdapter_forTerminal.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
               String returnString = mArrayList.get(pos);
               Intent backToBus = new Intent(SelectDepartureActivity.this, MainActivity.class);
               backToBus.putExtra("Departure",returnString);
               setResult(RESULT_OK, backToBus);
               finish();

            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        Button entire = (Button)findViewById(R.id.entire_location);
        entire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<entireLocation.size();i++){
                    for(int j=0;j<entireLocation.get(i).length;j++){
                        String[] terminal = (String[])entireLocation.get(i);
                        mArrayList.add(terminal[j]);
                    }
                }

                mAdapter.notifyDataSetChanged();
                Log.v("Subin",mAdapter.getItemCount()+"");
            }
        });
        final Button seoul = (Button)findViewById(R.id.seoul_location);
        seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<Seoul.length;i++){
                    mArrayList.add(Seoul[i]);
                }
                mAdapter.notifyDataSetChanged();
                Log.v("Subin",mAdapter.getItemCount()+"");
            }
        });
        Button incheon = (Button)findViewById(R.id.incheon_location);
        incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<Incheon.length;i++){
                    mArrayList.add(Incheon[i]);
                }
                mAdapter.notifyDataSetChanged();
                Log.v("Subin",mAdapter.getItemCount()+"");
            }
        });
        Button gangwon = (Button)findViewById(R.id.gangwon_location);
        gangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<Gangwon.length;i++){
                    mArrayList.add(Gangwon[i]);
                }
                mAdapter.notifyDataSetChanged();
                Log.v("Subin",mAdapter.getItemCount()+"");
            }
        });
        Button daejeon = (Button)findViewById(R.id.daejeon_location);
        daejeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<Daejeon.length;i++){
                    mArrayList.add(Daejeon[i]);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button chungnam = (Button)findViewById(R.id.chungnam_location);
        chungnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<Chungnam.length;i++){
                    mArrayList.add(Chungnam[i]);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button chungbuk = (Button)findViewById(R.id.chungbuk_location);
        chungbuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<Chungbuk.length;i++){
                    mArrayList.add(Chungbuk[i]);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button gwangju = (Button)findViewById(R.id.gwangju_location);
        gwangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<Gwangju.length;i++){
                    mArrayList.add(Gwangju[i]);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button jeonbuk = (Button)findViewById(R.id.jeonbuk_location);
        jeonbuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<Jeonbuk.length;i++){
                    mArrayList.add(Jeonbuk[i]);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button busan = (Button)findViewById(R.id.busan_location);
        busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<Busan.length;i++){
                    mArrayList.add(Busan[i]);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button daegu = (Button)findViewById(R.id.daegu_location);
        daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for(int i=0;i<Daegu.length;i++){
                    mArrayList.add(Daegu[i]);
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        /*
        데이터베이스에서 장소에 대한 버스 정류장 데이터 가져오기
        버스 정류장 button이 data임

        mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입
        mAdapter.notifyDataSetChanged();
        */

    }

}
