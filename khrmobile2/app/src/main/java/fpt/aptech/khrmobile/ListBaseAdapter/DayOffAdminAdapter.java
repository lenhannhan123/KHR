package fpt.aptech.khrmobile.ListBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

import fpt.aptech.khrmobile.API.DayOffAdmin;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.AccountNotification;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayOffAdminAdapter extends RecyclerView.Adapter<DayOffAdminAdapter.MessingAccountHodlder> {
    List<ModelString> MessingAccountlist;
    Context context;

    public DayOffAdminAdapter(List<ModelString> MessingAccountlist, Context context) {
        this.MessingAccountlist=MessingAccountlist;
        this.context=context;
    }

    @NonNull
    @Override
    public DayOffAdminAdapter.MessingAccountHodlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dayoff_listcheck_item,parent,false);
        return new DayOffAdminAdapter.MessingAccountHodlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayOffAdminAdapter.MessingAccountHodlder holder, int position) {
        ModelString modelStringsa = MessingAccountlist.get(position);
        holder.tv1.setText(modelStringsa.getData2());
        holder.tv2.setText(modelStringsa.getData6());
        holder.tv3.setText(modelStringsa.getData3());
        holder.tv4.setText(modelStringsa.getData4());
        holder.tv5.setText(modelStringsa.getData5());
        boolean isExpandable = Boolean.valueOf(modelStringsa.getData8());
        holder.relativeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        Glide.with(context)
                .load("http://" + ConfigData.IP + ":7777/api/view-profile-image?filename=" +modelStringsa.getData7() )
//                .transform(new RoundedCorners(radius))
                .transform(new CircleCrop())
                .override(200, 200)
//                .error(R.drawable.icon5)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return MessingAccountlist.size();
    }

    public class MessingAccountHodlder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button btn1, btn2;
        TextView tv1, tv2, tv3, tv4, tv5;
        LinearLayout linearLayout;
        RelativeLayout relativeLayout;

        public MessingAccountHodlder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgAd1);
            tv1 = itemView.findViewById(R.id.tvAd1);
            tv2 = itemView.findViewById(R.id.tvAd2);
            tv3 = itemView.findViewById(R.id.tvAd3);
            tv4 = itemView.findViewById(R.id.tvAd4);
            tv5 = itemView.findViewById(R.id.tvAd5);
            btn1 = itemView.findViewById(R.id.btnDadmin1);
            btn2 = itemView.findViewById(R.id.btnDadmin2);
            linearLayout = itemView.findViewById(R.id.linenearaddayoff);
            relativeLayout = itemView.findViewById(R.id.relateaddayoff);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelString accountNotification = MessingAccountlist.get(getAdapterPosition());
                    boolean is = Boolean.valueOf(accountNotification.getData8());
                    accountNotification.setData8(String.valueOf(!is));
                    notifyItemChanged(getAdapterPosition());
                }
            });
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelString accountNotification = MessingAccountlist.get(getAdapterPosition());
                    DayOffAdmin.api.ApprovedDayOff(accountNotification.getData1()).enqueue(new Callback<List<ModelString>>() {
                        @Override
                        public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                            MessingAccountlist.remove(accountNotification);
                            notifyItemChanged(getAdapterPosition());

                        }

                        @Override
                        public void onFailure(Call<List<ModelString>> call, Throwable t) {

                        }
                    });
                }
            });
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelString accountNotification = MessingAccountlist.get(getAdapterPosition());
                    DayOffAdmin.api.DenyinggetListDayOff(accountNotification.getData1()).enqueue(new Callback<List<ModelString>>() {
                        @Override
                        public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                            MessingAccountlist.remove(accountNotification);
                            notifyItemChanged(getAdapterPosition());

                        }

                        @Override
                        public void onFailure(Call<List<ModelString>> call, Throwable t) {

                        }
                    });
                }
            });
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ModelString classed =Testlist.get(getAdapterPosition());
//                    String text = classed.getData1().toString();
//                    String idSelect = classed.getData5();
//                    Intent intent = new Intent(context, TestDetailsActivity.class);
//                    intent.putExtra("idSelect", idSelect);
//                    context.startActivity(intent);
//                }
//            });

        }
    }
}
