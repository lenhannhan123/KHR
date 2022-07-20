package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fpt.aptech.khrmobile.API.APITimeline;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.ListBaseAdapter.MyTimelineAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTimelineActivity extends AppCompatActivity {

    List<Integer> year= new ArrayList<>();

    ListView listView;
    List<ModelString> list ;
    MyTimelineAdapter adapter;
    String month;
    String Year;
    Spinner spnMonthh;
    Spinner spnYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_timeline);

        spnMonthh= (Spinner) findViewById(R.id.myTimeline_spinner_month);
         spnYear = (Spinner) findViewById(R.id.myTimeline_spinner_year);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_my_timeline);
        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,MyTimelineActivity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,MyTimelineActivity.this,0.8);

        buttonBack();
        callgetYear();
        BtnSearch();

    }


    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_Work_schedule_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyTimelineActivity.this, MainWorkScheduleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void callgetYear(){
        APITimeline.api.getTimelineyear().enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                year.addAll(response.body());
                addmonthhyear();


            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Toast.makeText(MyTimelineActivity.this,"Lỗi mạng!!",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void addmonthhyear(){



        List<String> list = new ArrayList<>();
        list.add("Chọn tháng");
        list.add("Tháng 1");
        list.add("Tháng 2");
        list.add("Tháng 3");
        list.add("Tháng 4");
        list.add("Tháng 5");
        list.add("Tháng 6");
        list.add("Tháng 7");
        list.add("Tháng 8");
        list.add("Tháng 9");
        list.add("Tháng 10");
        list.add("Tháng 11");
        list.add("Tháng 12");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnMonthh.setAdapter(adapter);






        List<String> list1 = new ArrayList<>();
        list1.add("Chọn năm");
        for (Integer item: year) {
            list1.add(item.toString());

        }


        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnYear.setAdapter(adapter1);



        Date currentTime = Calendar.getInstance().getTime();

          month ="Tháng "+ String.valueOf(currentTime.getMonth()+1) ;
         Year =  currentTime.toString().substring(currentTime.toString().length()-4,currentTime.toString().length());

          int  positionMonth=0;
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).equals(month)){
                positionMonth =i;
            }

        }
        spnMonthh.setSelection(positionMonth);

        int  positionYear=0;
        for (int i = 0; i <list1.size() ; i++) {
            if(list1.get(i).equals(Year)){
                positionYear =i;
            }

        }
        spnYear.setSelection(positionYear);

        GetData(String.valueOf(currentTime.getMonth()+1) ,Year );


//        Toast.makeText(MyTimelineActivity.this, String.valueOf(positionMonth), Toast.LENGTH_SHORT).show();




    }

    private String switchmonth(String month){

        switch (month){
            case "Chọn tháng":
                return "00";

            case "Tháng 1":
                return "1";


            case "Tháng 2":
                return "2";

            case "Tháng 3":
                return "3";

            case "Tháng 4":
                return "4";

            case "Tháng 5":
                return "5";

            case "Tháng 6":
                return "6";

            case "Tháng 7":
                return "7";

            case "Tháng 8":
                return "8";

            case "Tháng 9":
                return "9";

            case "Tháng 10":
                return "10";

            case "Tháng 11":
                return "11";

            default:
                return "12";


        }



    }

    private  void GetData(String month, String year){

        String mail = ConfigData.userId;

        listView = findViewById(R.id.My_timeline_list_view);


        APITimeline.api.getMyTimeline(mail,month,year).enqueue(new Callback<List<ModelString>>() {
            @Override
            public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                List<ModelString> data =response.body();
                    MyTimelineAdapter baseAdapter = new MyTimelineAdapter(MyTimelineActivity.this,data,MyTimelineActivity.this);
                    listView.setAdapter(baseAdapter);
                    baseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelString>> call, Throwable t) {
                Toast.makeText(MyTimelineActivity.this,"Lỗi mạng!!",Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void  BtnSearch(){
        Button btnSearch = findViewById(R.id.My_timeline_btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String SlectMonth =switchmonth(spnMonthh.getSelectedItem().toString());
                String SlectYear = spnYear.getSelectedItem().toString();
                if(SlectYear.equals("Chọn năm")){
                    SlectYear="00";
                }
                GetData(SlectMonth,SlectYear);
//                Toast.makeText(MyTimelineActivity.this,SlectMonth,Toast.LENGTH_SHORT).show();
            }
        });
    }
}