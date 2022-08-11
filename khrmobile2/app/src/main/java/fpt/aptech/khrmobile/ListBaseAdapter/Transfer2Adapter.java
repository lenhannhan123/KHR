package fpt.aptech.khrmobile.ListBaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.R;
import fpt.aptech.khrmobile.Transferdetail1Activity;
import fpt.aptech.khrmobile.Transferdetail2Activity;

public class Transfer2Adapter extends BaseAdapter {

    Context context ;
    List<ModelString> list;

    public Transfer2Adapter(Context context, List<ModelString> list, Activity activity){
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
//        Mytimeline_Item_Timeline

        if(view ==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.layouttransfer,null);

        }

        ModelString modelString = list.get(i);

        TextView Title = view.findViewById(R.id.Title);
        TextView Status = view.findViewById(R.id.Status);
        LinearLayout buttontrans =  view.findViewById(R.id.buttontrans);

        String status = "";
        int color= Color.parseColor("#a8a8a8");

        switch (modelString.getData3()){

            case "0":
                status="Người đổi đang xác nhận";
                color= Color.parseColor("#e6ed25");
                break;
            case "1":
                status="Đang đợi admin  xác nhận";
                color= Color.parseColor("#e6ed25");
                break;
            case "2":
                status="Người đổi từ chối";
                color=Color.parseColor("#f41e1e");
                break;
            case "3":
                status="Đã đổi thành công";
                color= Color.parseColor("#30c019");
                break;
            default:
                status="Admin từ chối";
                color=Color.parseColor("#f41e1e");
                break;

        }


        Status.setTextColor(color);

        Title.setText(modelString.getData2());
        Status.setText(status);


        buttontrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Transferdetail2Activity.class);

                String idSelect=list.get(i).getData1();

                intent.putExtra("idSelect", idSelect);

                context.startActivity(intent);
            }
        });


        return view;


    }
}
