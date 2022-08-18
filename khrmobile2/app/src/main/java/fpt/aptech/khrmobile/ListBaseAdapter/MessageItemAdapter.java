package fpt.aptech.khrmobile.ListBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.R;

public class MessageItemAdapter extends RecyclerView.Adapter<MessageItemAdapter.MessingAccountHodlder> {
    List<ModelString> MessingAccountlist;
    Context context;

    public MessageItemAdapter(List<ModelString> MessingAccountlist, Context context) {
        this.MessingAccountlist=MessingAccountlist;
        this.context=context;
    }

    @NonNull
    @Override
    public MessageItemAdapter.MessingAccountHodlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.message_item,parent,false);
        return new MessageItemAdapter.MessingAccountHodlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageItemAdapter.MessingAccountHodlder holder, int position) {
        ModelString modelStringsa = MessingAccountlist.get(position);
        holder.tvconten.setText(modelStringsa.getData3());
        holder.tvdatesend.setText(modelStringsa.getData4());
        holder.tvname.setText(modelStringsa.getData2());
        //holder.imageView.setImageResource(Integer.parseInt(modelStringsa.getData4()));
        Glide.with(context)
                .load("http://" + ConfigData.IP + ":7777/api/view-profile-image?filename=" +modelStringsa.getData1() )
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
        TextView tvname,tvconten,tvdatesend;
        public MessingAccountHodlder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imgMavatar);
            tvconten=itemView.findViewById(R.id.tvMcontent);
            tvdatesend=itemView.findViewById(R.id.tvMSendTime);
            tvname=itemView.findViewById(R.id.tvMName);
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
