package fpt.aptech.khrmobile;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MytimedetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MytimedetailFragment extends Fragment {
    View view;
    ArrayList<String> data = new ArrayList<>();
    ViewGroup ctn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MytimedetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MytimedetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MytimedetailFragment newInstance(String param1, String param2) {
        MytimedetailFragment fragment = new MytimedetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mytimedetail, container, false);
        ctn = container;


        Bundle bundle = getArguments();
        if(bundle!=null){
            data.addAll(bundle.getStringArrayList("data"));

            if(data.size()>0){
                setCalender();
            }else {
                Toast.makeText(view.getContext(),"Khong co du lieu",Toast.LENGTH_SHORT).show();
            }



        }

        return view;
    }

    private void  setCalender(){
       ArrayList<String> dataParse = new ArrayList<>();



       if(Integer.parseInt(data.get(0))>=1 && Integer.parseInt(data.get(0))<=5 ){
           dataParse.addAll(data);
       }

        if(Integer.parseInt(data.get(0))>=6 &&Integer.parseInt(data.get(0))<=10 ){
            for (String item: data) {
                dataParse.add(String.valueOf(Integer.parseInt(item)-5));
            }
        }

        if(Integer.parseInt(data.get(0))>=11 &&Integer.parseInt(data.get(0))<=15 ){
            for (String item: data) {
                dataParse.add(String.valueOf(Integer.parseInt(item)-10));
            }
        }

        if(Integer.parseInt(data.get(0))>=16 &&Integer.parseInt(data.get(0))<=20 ){
            for (String item: data) {
                dataParse.add(String.valueOf(Integer.parseInt(item)-15));
            }
        }

        if(Integer.parseInt(data.get(0))>=21 &&Integer.parseInt(data.get(0))<=25 ){
            for (String item: data) {
                dataParse.add(String.valueOf(Integer.parseInt(item)-20));
            }
        }

        if(Integer.parseInt(data.get(0))>=26 &&Integer.parseInt(data.get(0))<=30 ){
            for (String item: data) {
                dataParse.add(String.valueOf(Integer.parseInt(item)-25));
            }
        }

        if(Integer.parseInt(data.get(0))>=31 &&Integer.parseInt(data.get(0))<=35 ){
            for (String item: data) {
                dataParse.add(String.valueOf(Integer.parseInt(item)-30));
            }
        }



        for (String item: dataParse) {

            switch (item){
                case "1":
                    Button button1 =view.findViewById(R.id.Btn_Shift11);
                    ViewCompat.setBackgroundTintList(button1, ContextCompat.getColorStateList(view.getContext(), R.color.red));
//                    button1.setBackgroundColor();
                    break;
                case "2":
                    Button button2 =view.findViewById(R.id.Btn_Shift12);
                    ViewCompat.setBackgroundTintList(button2, ContextCompat.getColorStateList(view.getContext(), R.color.red));
                    break;
                case "3":

                    Button button3 =view.findViewById(R.id.Btn_Shift13);
                    ViewCompat.setBackgroundTintList(button3, ContextCompat.getColorStateList(view.getContext(), R.color.red));

                    break;
                case "4":
                    Button button4 =view.findViewById(R.id.Btn_Shift14);
                    ViewCompat.setBackgroundTintList(button4, ContextCompat.getColorStateList(view.getContext(), R.color.red));
                    break;
                default:
                   Button button5 =view.findViewById(R.id.Btn_Shift15);
                    ViewCompat.setBackgroundTintList(button5, ContextCompat.getColorStateList(view.getContext(), R.color.red));
                    break;

            }


        }

    }
}