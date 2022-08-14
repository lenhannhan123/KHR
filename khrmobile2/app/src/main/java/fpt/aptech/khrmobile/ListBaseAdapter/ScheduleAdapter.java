package fpt.aptech.khrmobile.ListBaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import fpt.aptech.khrmobile.AddTimelineDetailActivity;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.R;

public class ScheduleAdapter extends BaseAdapter {
    Context context ;
    List<ModelString> list;

    public ScheduleAdapter(Context context, List<ModelString> list, Activity activity){
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

            view = inflater.inflate(R.layout.layoutscheduledetail,null);

        }

        ModelString modelString = list.get(i);

        TextView text1  =view.findViewById(R.id.name_position);
        text1.setText(modelString.getData2());

        TextView text2  =view.findViewById(R.id.content_position);
        text2.setText(modelString.getData3());





        return view;


    }
}
