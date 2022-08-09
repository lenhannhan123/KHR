package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import fpt.aptech.khrmobile.API.APITimeline;
import fpt.aptech.khrmobile.Config.ConfigData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddScheduleTimelineDetailActivity extends AppCompatActivity implements ScheduleFragment.ISendataListener {
    ViewPager2 mViewPage;
    TabLayout mTabLayout;
    String idSelect="";
    List<String> data = new ArrayList<>();
    Boolean[] data12 = new Boolean[40];
    String data_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_timeline_detail);

        Intent intent = getIntent();
         data_  = intent.getStringExtra("data");
        idSelect  = intent.getStringExtra("idSelect");

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_schedule_timeline_detail);
        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,AddScheduleTimelineDetailActivity.this);


//        Toast.makeText(MyTimelineDetailActivity.this,data_,Toast.LENGTH_SHORT).show();
        androidx.appcompat.widget.AppCompatTextView n = findViewById(R.id.title_Mytimeline_details);
        n.setText(data_);

        ScrollView scrollView = findViewById(R.id.scrollView);
        mTabLayout = findViewById(R.id.myTimeline_tablayout1);
        callNav.setDisplay(scrollView,AddScheduleTimelineDetailActivity.this,0.8);


        callData();
        checkTablayoutSelected();
        buttonBack();
    }


    ConfigData configData = new ConfigData();
    private  void callData(){
        String idUser = configData.userId(this);
        APITimeline.api.GetTimeLineSort1(idSelect,idUser).enqueue(new Callback<List<String>>() {
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

//                for (int i = 0; i <data12.length ; i++) {
//                    Toast.makeText(AddScheduleTimelineDetailActivity.this,"Phan tu :"+i+" la: "+ data12[i],Toast.LENGTH_SHORT).show();
//
//                }




            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(AddScheduleTimelineDetailActivity.this,"Lỗi mạng!!",Toast.LENGTH_SHORT).show();
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

                newLayout(new ScheduleFragment(),data1,"0");
                break;
            case 1:


                for (int i = 1; i < data12.length; i++) {
                    if (data12[i] == true && i >= 6 && i <= 10) {
                        data1.add(String.valueOf(i));
                    }
                }






                newLayout(new ScheduleFragment(),data1,"5");
                break;
            case 2:

                for (int i = 1; i < data12.length; i++) {
                    if(data12[i]==true &&i>=11 && i<=15){
                        data1.add(String.valueOf(i));
                    }
                }



                newLayout(new ScheduleFragment(),data1,"10");
                break;
            case 3:


                for (int i = 1; i < data12.length; i++) {
                    if(data12[i]==true &&i>=16 && i<=20){
                        data1.add(String.valueOf(i));
                    }
                }

                newLayout(new ScheduleFragment(),data1,"15");
                break;
            case 4:

                for (int i = 1; i < data12.length; i++) {
                    if(data12[i]==true &&i>=21 && i<=25){
                        data1.add(String.valueOf(i));
                    }
                }

                newLayout(new ScheduleFragment(),data1,"20");
                break;
            case 5:

                for (int i = 1; i < data12.length; i++) {
                    if(data12[i]==true &&i>=26 && i<=30){
                        data1.add(String.valueOf(i));
                    }
                }


                newLayout(new ScheduleFragment(),data1,"25");
                break;
            default:

                for (int i = 1; i < data12.length; i++) {
                    if (data12[i] == true && i >= 31 && i <= 35) {
                        data1.add(String.valueOf(i));
                    }
                }

                newLayout(new ScheduleFragment(),data1,"30");
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

    private  void newLayout(Fragment fragment, ArrayList<String> dataAdd, String stt){

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
        ImageButton button = findViewById(R.id.btn_Work_schedule_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddScheduleTimelineDetailActivity.this, MainWorkScheduleActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void sendData(String position, String stt) {
        int code = Integer.parseInt(stt)+Integer.parseInt(position);

        Intent intent = new Intent(this, WorkScheduleDetailActivity.class);

        intent.putExtra("data", data_);
        intent.putExtra("idSelect", idSelect);
        intent.putExtra("Code", String.valueOf(code));
        this.startActivity(intent);


    }
}