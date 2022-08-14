package fpt.aptech.khrmobile.ListBaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.Entities.Timekeeping;
import fpt.aptech.khrmobile.R;
import fpt.aptech.khrmobile.TimekeepingDetail;
import retrofit2.Retrofit;

public class TimekeepingBaseAdapter extends BaseAdapter {
    final List<Timekeeping> timekeepings;
    Context context;
    int count = 1;

    public TimekeepingBaseAdapter(List<Timekeeping> list, Context context) {
        this.timekeepings = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return timekeepings.size();
    }

    @Override
    public Object getItem(int position) {
        return timekeepings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return timekeepings.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewTimekeeping = null;
        if (convertView == null) {
            viewTimekeeping = convertView.inflate(parent.getContext(), R.layout.layouttimekeeping, null);
        } else {
            viewTimekeeping = convertView;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Timekeeping timekeeping = (Timekeeping) getItem(position);

        Button btnViewTimekeeping = ((Button) viewTimekeeping.findViewById(R.id.btnTimekeepingDetail));
        btnViewTimekeeping.setText("Ca " + count++);
        btnViewTimekeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TimekeepingDetail.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data", timekeeping);
                context.startActivity(intent);
            }
        });

        return viewTimekeeping;
    }
}
