package fpt.aptech.khrmobile.ListBaseAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpt.aptech.khrmobile.Entities.DayOff;
import fpt.aptech.khrmobile.R;

public class DayoffAdapter extends RecyclerView.Adapter<DayoffAdapter.DayOffVH>{

    List<DayOff> dayOffList;
    Context context;

    public DayoffAdapter(List<DayOff> dayOffList, Context context) {
        this.dayOffList = dayOffList;
        this.context = context;
    }

    @NonNull
    @Override
    public DayOffVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.from(parent.getContext()).inflate(R.layout.dayoff_item,parent,false);
        return new DayOffVH(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DayOffVH holder, int position) {
        DayOff dayoff = dayOffList.get(position);
        try {
            holder.tvtitle.setText("Xin phép nghĩ ngày:"+ dayoff.getStartdate().getDay()+" - "+dayoff.getStartdate().getMonth()+" - "+(dayoff.getStartdate().getYear()+1900));
            switch (dayoff.getStatus()){
                case(0):holder.tvstatus.setText("Chưa được duyệt");
                    holder.tvstatus.setTextColor(R.color.approved_check);
                    break;
                case(1):holder.tvstatus.setText("Đã duyệt");
                    holder.tvstatus.setTextColor(R.color.approved_satus);
                    break;
                case(2):holder.tvstatus.setText("Từ chối");
                    holder.tvstatus.setTextColor(R.color.approved_deny);
            }
            holder.tvdatestare.setText("Từ :"+dayoff.getStartdate().getDay()+"-"+dayoff.getStartdate().getMonth());
            holder.tvdateend.setText("Đến :"+dayoff.getEnddate().getDay()+"-"+dayoff.getEnddate().getMonth());
            holder.tvnumdate.setText("Tổng(ngày) :"+dayoff.getDaynumber());
            holder.tvContent.setText("Lý do :"+dayoff.getContent());
            holder.relativeLayout.setVisibility(true ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return dayOffList.size();
    }

    public class DayOffVH extends RecyclerView.ViewHolder {
        TextView tvtitle,tvstatus,tvdatestare,tvdateend,tvnumdate,tvContent;
        LinearLayout linearLayout;
        RelativeLayout relativeLayout;
        public DayOffVH(@NonNull View itemView) {
            super(itemView);
            tvtitle=itemView.findViewById(R.id.tvdDTitle);
            tvstatus=itemView.findViewById(R.id.dStatus);
            tvdatestare=itemView.findViewById(R.id.tvdstart);
            tvdateend=itemView.findViewById(R.id.tvdend);
            tvnumdate=itemView.findViewById(R.id.tvdnum);
            tvContent=itemView.findViewById(R.id.tvdcontent);
            linearLayout=itemView.findViewById(R.id.lineardayoff);
            relativeLayout=itemView.findViewById(R.id.relatedayoff);
        }
    }
}
