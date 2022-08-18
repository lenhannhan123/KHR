package fpt.aptech.khrmobile.ListBaseAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.MessageDetailsActivity;
import fpt.aptech.khrmobile.R;

public class MessingAccountAdapter extends RecyclerView.Adapter<MessingAccountAdapter.MessingAccountHodlder>{
    List<ModelString> MessingAccountlist;
    Context context;

    public MessingAccountAdapter(List<ModelString> MessingAccountlist, Context context) {
        this.MessingAccountlist=MessingAccountlist;
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
        holder.tvtitle.setText(modelStringsa.getData2());
        //holder.tvdate.setText(modelStringsa.getData2());
        //holder.imageView.setImageResource(Integer.parseInt(modelStringsa.getData4()));


        Glide.with(context)
                .load("http://" + ConfigData.IP + ":7777/api/view-profile-image?filename=" +modelStringsa.getData1() )
//                .transform(new RoundedCorners(radius))
                .transform(new CircleCrop())
                .override(500, 500)
//                .error(R.drawable.icon5)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return MessingAccountlist.size();
    }

    public class MessingAccountHodlder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvtitle;
        LinearLayout linearLayout;
        public MessingAccountHodlder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.message_image_item);
            tvtitle=itemView.findViewById(R.id.message_text_item);

            linearLayout=itemView.findViewById(R.id.linenearavarta);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelString classed =MessingAccountlist.get(getAdapterPosition());
                    Intent intent = new Intent(context, MessageDetailsActivity.class);
                    String idSelect = classed.getData3();
                    String name = classed.getData2();
                    String avatar = classed.getData1();
                    intent.putExtra("idSelect", idSelect);
                    intent.putExtra("name", name);
                    intent.putExtra("avatar", avatar);
                    //System.out.println(classed.getData5());
                    context.startActivity(intent);
                }
            });

        }
    }
}
