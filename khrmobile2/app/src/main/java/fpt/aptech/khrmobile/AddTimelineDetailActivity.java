package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AddTimelineDetailActivity extends AppCompatActivity {
    ViewPager2 mViewPage;
    TabLayout mTabLayout;
    String idSelect="";
    List<String> data = new ArrayList<>();

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
        mTabLayout = findViewById(R.id.myTimeline_tablayout);
        callNav.setDisplay(scrollView,AddTimelineDetailActivity.this,0.8);

        buttonBack();
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
}