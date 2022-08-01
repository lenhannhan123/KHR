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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Timekeeping timekeeping = (Timekeeping) getItem(position);
        String shiftType = "";
        if (timeFormat.format(timekeeping.getTimestart()).compareTo("05:45:00") >= 0 && timeFormat.format(timekeeping.getTimeend()).compareTo("10:15:00") <= 0) {
            shiftType = "Ca sáng";
        } else if (timeFormat.format(timekeeping.getTimestart()).compareTo("09:45:00") >= 0 && timeFormat.format(timekeeping.getTimeend()).compareTo("14:15:00") <= 0) {
            shiftType = "Ca trưa";
        } else if(timeFormat.format(timekeeping.getTimestart()).compareTo("13:45:00") >= 0 && timeFormat.format(timekeeping.getTimeend()).compareTo("18:15:00") <= 0) {
            shiftType = "Ca chiều";
        } else if(timeFormat.format(timekeeping.getTimestart()).compareTo("17:45:00") >= 0 && timeFormat.format(timekeeping.getTimeend()).compareTo("22:15:00") <= 0){
            shiftType = "Ca tối";
        } else if(timeFormat.format(timekeeping.getTimestart()).compareTo("21:45:00") >= 0 && timeFormat.format(timekeeping.getTimeend()).compareTo("06:15:00") <= 0){
            shiftType = "Ca đêm";
        }


        ((Button) viewTimekeeping.findViewById(R.id.btnTimekeepingDetail)).

        setText(shiftType + " Ngày " + dateFormat.format(timekeeping.getTimestart()));

        return viewTimekeeping;
    }
}
