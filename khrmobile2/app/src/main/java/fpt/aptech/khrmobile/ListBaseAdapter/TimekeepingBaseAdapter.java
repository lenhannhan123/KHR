package fpt.aptech.khrmobile.ListBaseAdapter;

import android.app.Activity;
import android.content.Context;
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

public class TimekeepingBaseAdapter extends BaseAdapter {
    final List<Timekeeping> timekeepings;

    public TimekeepingBaseAdapter(List<Timekeeping> list) {
        this.timekeepings = list;
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
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Timekeeping timekeeping = (Timekeeping) getItem(position);

        ((Button) viewTimekeeping.findViewById(R.id.btnTimekeepingDetail)).

        setText("Ca " + simpleDateFormat.format(timekeeping.getTimestart()));

        return viewTimekeeping;
    }
}
