package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
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
import fpt.aptech.khrmobile.Entities.ModelString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTimelineDetailActivity extends AppCompatActivity {

    ViewPager2 mViewPage;
    TabLayout mTabLayout;
    String idSelect="";
    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_timeline_detail);

        Intent intent = getIntent();
        String data_  = intent.getStringExtra("data");
         idSelect  = intent.getStringExtra("idSelect");


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_my_timeline_detail);
        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,MyTimelineDetailActivity.this);


//        Toast.makeText(MyTimelineDetailActivity.this,data_,Toast.LENGTH_SHORT).show();
        androidx.appcompat.widget.AppCompatTextView n = findViewById(R.id.title_Mytimeline_details);
        n.setText(data_);

        ScrollView scrollView = findViewById(R.id.scrollView);
        mTabLayout = findViewById(R.id.myTimeline_tablayout);
        callNav.setDisplay(scrollView,MyTimelineDetailActivity.this,0.8);
        CallDataMytimeline();
        buttonBack();
        checkTablayoutSelected();

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

    private void innitFragment(){

      int position=  mTabLayout.getSelectedTabPosition();
        ArrayList<String> data1 = new ArrayList<>();
      switch (position){
          case 0:

              if(data.size() >0){

                  for (String item: data) {
                      if(Integer.parseInt(item)>=1 && Integer.parseInt(item)<=5){
                          data1.add(item);
                      }
                  }
                  
              }
              newLayout(new MytimedetailFragment(),data1);
              break;
          case 1:

              if(data.size() >0){
                  for (String item: data) {
                      if(Integer.parseInt(item)>=6 && Integer.parseInt(item)<=10){
                          data1.add(item);
                      }
                  }

              }

              newLayout(new MytimedetailFragment(),data1);
              break;
          case 2:
              if(data.size() >0){
                  for (String item: data) {
                      if(Integer.parseInt(item)>=11 && Integer.parseInt(item)<=15){
                          data1.add(item);
                      }
                  }

              }
              newLayout(new MytimedetailFragment(),data1);
              break;
          case 3:
              if(data.size() >0){
                  for (String item: data) {
                      if(Integer.parseInt(item)>=16 && Integer.parseInt(item)<=20){
                          data1.add(item);
                      }
                  }

              }
              newLayout(new MytimedetailFragment(),data1);
              break;
          case 4:
              if(data.size() >0){
                  for (String item: data) {
                      if(Integer.parseInt(item)>=21 && Integer.parseInt(item)<=25){
                          data1.add(item);
                      }
                  }

              }
              newLayout(new MytimedetailFragment(),data1);
              break;
          case 5:
              if(data.size() >0){
                  for (String item: data) {
                      if(Integer.parseInt(item)>=26 && Integer.parseInt(item)<=30){
                          data1.add(item);
                      }
                  }

              }
              newLayout(new MytimedetailFragment(),data1);
              break;
          default:
              if(data.size() >0){
                  for (String item: data) {
                      if(Integer.parseInt(item)>=31 && Integer.parseInt(item)<=35){
                          data1.add(item);
                      }
                  }

              }
              newLayout(new MytimedetailFragment(),data1);
              break;

      }


    }

    private  void newLayout(Fragment fragment, ArrayList<String> dataAdd){

//        Toast.makeText(this,"So luong data: "+dataAdd.size(),Toast.LENGTH_SHORT).show();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data",dataAdd);
        fragment.setArguments(bundle);
        transaction.remove(fragment);
        transaction.replace(R.id.My_timedetail_fragmentlayout,fragment);
        transaction.commit();
    }

    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_Timeline_detail_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyTimelineDetailActivity.this, MyTimelineActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CallDataMytimeline(){
        String idUser = ConfigData.userId;
        APITimeline.api.getMyTimelineDetail(idSelect,idUser).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    data = response.body();
                     innitFragment();
//                    Toast.makeText(MyTimelineDetailActivity.this,"Number "+data.size() ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(MyTimelineDetailActivity.this,"Lỗi mạng!!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}