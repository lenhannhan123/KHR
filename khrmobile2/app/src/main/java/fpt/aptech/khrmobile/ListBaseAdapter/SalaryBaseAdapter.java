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

public class SalaryBaseAdapter extends BaseAdapter {
    Context context ;
    List<ModelString> list;

    public SalaryBaseAdapter(Context context, List<ModelString> list, Activity activity){
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

        if(view ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layoutsalary,null);
        }
        ModelString modelString = list.get(i);

        if(!modelString.getData1().equals("numberOfTimekeeping")){
            TextView text1  = view.findViewById(R.id.tvPositionName);
            text1.setText(modelString.getData1());
            TextView text2  = view.findViewById(R.id.tvTime);
            text2.setText(modelString.getData2() + " giờ");
        }


        return view;
    }
}
