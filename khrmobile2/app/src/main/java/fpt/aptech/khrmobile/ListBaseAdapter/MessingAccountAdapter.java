package fpt.aptech.khrmobile.ListBaseAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.R;

public class MessingAccountAdapter extends RecyclerView.Adapter<MessingAccountAdapter.MessingAccountHodlder>{
    List<ModelString> MessingAccountlist;
    Context context;

    public MessingAccountAdapter(List<ModelString> classlist, Context context) {
        this.MessingAccountlist=classlist;
        this.context=context;
    }

    @NonNull
    @Override
    public MessingAccountAdapter.MessingAccountHodlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.message_account_item,parent,false);
        return new MessingAccountAdapter.MessingAccountHodlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessingAccountHodlder holder, int position) {
        ModelString modelStringsa = MessingAccountlist.get(position);
        holder.tvtitle.setText(modelStringsa.getData1());
        holder.tvdate.setText(modelStringsa.getData2());
//        holder.image.setImageResource(Integer.parseInt(modelStringsa.getData4()));


        Glide.with(context)
                .load("http://" + ConfigData.IP + ":7777/"+modelStringsa.getData4())
//                .transform(new RoundedCorners(radius))
//                .transform(new CircleCrop())
                .override(600, 600)
//                .error(R.drawable.icon5)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return MessingAccountlist.size();
    }

    public class MessingAccountHodlder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvtitle,tvdate;
        public MessingAccountHodlder(@NonNull View itemView) {
            super(itemView);
//            imageView=itemView.findViewById(R.id.imageView);
//            tvtitle=itemView.findViewById(R.id.Title);
//            tvdate=itemView.findViewById(R.id.Date);
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
