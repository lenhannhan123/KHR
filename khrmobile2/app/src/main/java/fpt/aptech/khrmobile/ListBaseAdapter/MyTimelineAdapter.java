package fpt.aptech.khrmobile.ListBaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.MainWorkScheduleActivity;
import fpt.aptech.khrmobile.MyTimelineActivity;
import fpt.aptech.khrmobile.MyTimelineDetailActivity;
import fpt.aptech.khrmobile.R;

public class MyTimelineAdapter extends BaseAdapter {

    Context context ;
    List<ModelString> list;

    public MyTimelineAdapter(Context context, List<ModelString> list, Activity activity){
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        Mytimeline_Item_Timeline

        if(view ==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.layoutmytimeline,null);

        }

        ModelString modelString = list.get(i);

        Button Mytimeline_Item_Timeline =view.findViewById(R.id.Mytimeline_Item_Timeline);

        Mytimeline_Item_Timeline.setText(modelString.getData2());



        Mytimeline_Item_Timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyTimelineDetailActivity.class);
                intent.putExtra("data", Mytimeline_Item_Timeline.getText());
                context.startActivity(intent);
            }
        });


        return view;


    }
}
