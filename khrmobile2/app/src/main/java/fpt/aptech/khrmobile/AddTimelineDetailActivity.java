package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import fpt.aptech.khrmobile.API.APITimeline;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTimelineDetailActivity extends AppCompatActivity implements AddTimelineFragment.ISendataListener {
    ViewPager2 mViewPage;
    TabLayout mTabLayout;
    String idSelect="";
    List<String> data = new ArrayList<>();
    Boolean[] data12 = new Boolean[40];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timeline_detail);

        Intent intent = getIntent();
        String data_  = intent.getStringExtra("data");
        idSelect  = intent.getStringExtra("idSelect");

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_add_timeline_detail);
        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,AddTimelineDetailActivity.this);


//        Toast.makeText(MyTimelineDetailActivity.this,data_,Toast.LENGTH_SHORT).show();
        androidx.appcompat.widget.AppCompatTextView n = findViewById(R.id.title_Mytimeline_details);
        n.setText(data_);

        ScrollView scrollView = findViewById(R.id.scrollView);
        mTabLayout = findViewById(R.id.myTimeline_tablayout1);
        callNav.setDisplay(scrollView,AddTimelineDetailActivity.this,0.8);



        buttonBack();
        callData();
        checkTablayoutSelected();
        AddTimeline();
    }
    ConfigData configData = new ConfigData();
    private  void callData(){
        String idUser = configData.userId(this);
        APITimeline.api.getMyTimelineDetail(idSelect,idUser).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                data = response.body();



                for (int i = 0; i <data12.length ; i++) {
                    data12[i] = false;

                }



                if(data.size()>0){

                    for (String item: data) {
                        data12[Integer.parseInt(item)]=true;
                    }
                }
                innitFragment();

//                for (int i = 0; i <data1.length ; i++) {
//                    Toast.makeText(AddTimelineDetailActivity.this,"Phan tu :"+i+" la: "+ data1[i],Toast.LENGTH_SHORT).show();
//
//                }




            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(AddTimelineDetailActivity.this,"Lỗi mạng!!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void innitFragment(){

        int position=  mTabLayout.getSelectedTabPosition();

        ArrayList<String> data1 = new ArrayList<>();
        switch (position){
            case 0:


                    for (int i = 1; i < data12.length; i++) {
                        if(data12[i]==true && i>=1 && i<=5){
                            data1.add(String.valueOf(i));
                        }
                    }

                newLayout(new AddTimelineFragment(),data1,"0");
                break;
            case 1:


                    for (int i = 1; i < data12.length; i++) {
                        if (data12[i] == true && i >= 6 && i <= 10) {
                            data1.add(String.valueOf(i));
                        }
                    }






                newLayout(new AddTimelineFragment(),data1,"5");
                break;
            case 2:

                for (int i = 1; i < data12.length; i++) {
                    if(data12[i]==true &&i>=11 && i<=15){
                        data1.add(String.valueOf(i));
                    }
                }



                newLayout(new AddTimelineFragment(),data1,"10");
                break;
            case 3:


                for (int i = 1; i < data12.length; i++) {
                    if(data12[i]==true &&i>=16 && i<=20){
                        data1.add(String.valueOf(i));
                    }
                }

                newLayout(new AddTimelineFragment(),data1,"15");
                break;
            case 4:

                for (int i = 1; i < data12.length; i++) {
                    if(data12[i]==true &&i>=21 && i<=25){
                        data1.add(String.valueOf(i));
                    }
                }

                newLayout(new AddTimelineFragment(),data1,"20");
                break;
            case 5:

                for (int i = 1; i < data12.length; i++) {
                    if(data12[i]==true &&i>=26 && i<=30){
                        data1.add(String.valueOf(i));
                    }
                }


                newLayout(new AddTimelineFragment(),data1,"25");
                break;
            default:

                    for (int i = 1; i < data12.length; i++) {
                        if (data12[i] == true && i >= 31 && i <= 35) {
                            data1.add(String.valueOf(i));
                        }
                    }

                newLayout(new AddTimelineFragment(),data1,"30");
                break;

        }


    }

    private  void  checkTablayoutSelected(){
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                innitFragment();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private  void newLayout(Fragment fragment, ArrayList<String> dataAdd,String stt){

//        Toast.makeText(this,"So luong data: "+dataAdd.size(),Toast.LENGTH_SHORT).show();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data",dataAdd);
        bundle.putString("stt",stt);
        fragment.setArguments(bundle);
        transaction.remove(fragment);
        transaction.replace(R.id.My_timedetail_fragmentlayout1,fragment);
        transaction.commit();
    }

    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_Timeline_detail_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTimelineDetailActivity.this, AddTimeLineActivity.class);
                startActivity(intent);
            }
        });
    }


    public  void AddTimeline(){
        Button buttonAdd = findViewById(R.id.Add_timeline_buttonadd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Boolean check1 = true;

                for (Boolean item: data12) {
                    if(item==true){
                        check1=false;

                    }
                }

                    String mail = configData.userId(AddTimelineDetailActivity.this);;
                    APITimeline.api.CreateTimeline(mail,idSelect,
                            String.valueOf(data12[1]),
                            String.valueOf(data12[2]),
                            String.valueOf(data12[3]),
                            String.valueOf(data12[4]),
                            String.valueOf(data12[5]),
                            String.valueOf(data12[6]),
                            String.valueOf(data12[7]),
                            String.valueOf(data12[8]),
                            String.valueOf(data12[9]),
                            String.valueOf(data12[10]),
                            String.valueOf(data12[11]),
                            String.valueOf(data12[12]),
                            String.valueOf(data12[13]),
                            String.valueOf(data12[14]),
                            String.valueOf(data12[15]),
                            String.valueOf(data12[16]),
                            String.valueOf(data12[17]),
                            String.valueOf(data12[18]),
                            String.valueOf(data12[19]),
                            String.valueOf(data12[20]),
                            String.valueOf(data12[21]),
                            String.valueOf(data12[22]),
                            String.valueOf(data12[23]),
                            String.valueOf(data12[24]),
                            String.valueOf(data12[25]),
                            String.valueOf(data12[26]),
                            String.valueOf(data12[27]),
                            String.valueOf(data12[28]),
                            String.valueOf(data12[29]),
                            String.valueOf(data12[30]),
                            String.valueOf(data12[31]),
                            String.valueOf(data12[32]),
                            String.valueOf(data12[33]),
                            String.valueOf(data12[34]) ,
                            String.valueOf(data12[35])
                    ).enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            List<String> data111 = response.body();
                            Toast.makeText(AddTimelineDetailActivity.this,data111.get(0),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {
                            Toast.makeText(AddTimelineDetailActivity.this,t.toString()+ "Lỗi mạng!!",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent intent = new Intent(AddTimelineDetailActivity.this, AddTimeLineActivity.class);
                    startActivity(intent);
//                    Toast.makeText(AddTimelineDetailActivity.this,"Thêm thành công!!",Toast.LENGTH_SHORT).show();



            }
        });


    }


    @Override
    public void sendData(String position, String stt) {
        int vt = Integer.parseInt(stt)+Integer.parseInt(position);
        if (data12[vt]==true){

            data12[vt]=false;
        }else {
            data12[vt]=true;
        }

        innitFragment();

    }


}