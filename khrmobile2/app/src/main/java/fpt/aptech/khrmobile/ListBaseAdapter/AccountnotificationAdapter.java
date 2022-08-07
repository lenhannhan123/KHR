package fpt.aptech.khrmobile.ListBaseAdapter;

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

import fpt.aptech.khrmobile.Entities.AccountNotification;
import fpt.aptech.khrmobile.R;

public class AccountnotificationAdapter extends RecyclerView.Adapter<AccountnotificationAdapter.NotifacationHolder> {

    List<AccountNotification> notifactionlist;
    Context context;
    public AccountnotificationAdapter(List<AccountNotification> notifactionlist,Context context) {
        this.notifactionlist = notifactionlist;
        this.context = context;
    }
    @NonNull
    @Override
    public NotifacationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.notifiaction_item,parent,false);
        return new NotifacationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifacationHolder holder, int position) {
        AccountNotification notification = notifactionlist.get(position);
        holder.tvtitle.setText(notification.getIdnotification().getTitle());
        holder.tvdatesend.setText(notification.getIdnotification().getDateCreate().toString());
        holder.tvcontent.setText(notification.getIdnotification().getContent());
        boolean isExpandable = notifactionlist.get(position).isStatus();
        holder.relativeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return notifactionlist.size();
    }

    public class NotifacationHolder extends RecyclerView.ViewHolder{
        TextView tvtitle,tvdatesend,tvcontent;
        LinearLayout linearLayout;
        RelativeLayout relativeLayout;
        public NotifacationHolder(@NonNull View itemView) {
            super(itemView);
            tvtitle=itemView.findViewById(R.id.tvTitle);
            tvdatesend=itemView.findViewById(R.id.tvdDateSend);
            tvcontent=itemView.findViewById(R.id.tvContent);
            linearLayout=itemView.findViewById(R.id.linearnotification);
            relativeLayout=itemView.findViewById(R.id.relatenotification);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AccountNotification accountNotification =notifactionlist.get(getAdapterPosition());
                    accountNotification.setStatus(!accountNotification.isStatus());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }

}
