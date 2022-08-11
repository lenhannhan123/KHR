package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import fpt.aptech.khrmobile.API.APITimeline;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTransferActivity extends AppCompatActivity {
    Spinner spnTimeline, spnMyDate, spnMyshift, spnYourDate, spnYourshift, spnMyPosition, spnYourUser;
    List<ModelString> Listimeline = new ArrayList<>();
    List<ModelString> MyDate = new ArrayList<>();
    List<ModelString> YourDate = new ArrayList<>();
    List<ModelString> YourShift = new ArrayList<>();
    List<ModelString> MyShift = new ArrayList<>();
    List<ModelString> MyPosition = new ArrayList<>();
    List<ModelString> YourUser = new ArrayList<>();
    int id = 0, number = 0;
    Button buttonSubmit;
    EditText Content;

    ConfigData configData = new ConfigData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transfer);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_add_transfer);

        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation, R.id.page_1, CreateTransferActivity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView, CreateTransferActivity.this, 0.8);


        spnTimeline = (Spinner) findViewById(R.id.Choose_timeline);
        spnMyDate = (Spinner) findViewById(R.id.Choosee_my_date);
        spnMyshift = (Spinner) findViewById(R.id.Choosee_my_shiftcode);

        spnYourDate = (Spinner) findViewById(R.id.Choosee_your_date);
        spnYourshift = (Spinner) findViewById(R.id.Choosee_your_shiftcode);
        spnMyPosition = (Spinner) findViewById(R.id.Choosee_your_position);
        spnYourUser = (Spinner) findViewById(R.id.Choosee_your_user);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        Content = findViewById(R.id.Content);


        CallTimeline();
        CallMyDate();
        CallMyShift();

        CallYourDate();
        CallYourShift();
        CallMyPostion();
        CallYourUser();

        CallCheckPosition();

        CheckValidate();


        buttonBack();
    }

    private void CheckValidate() {


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = true;
                String itemTimeline = spnTimeline.getSelectedItem().toString();
                if (itemTimeline.equals("Chọn timeline")) {
                    check = false;
                    Toast.makeText(CreateTransferActivity.this, "Vui lòng chọn Timeline", Toast.LENGTH_SHORT).show();
                }
                if (check == true) {
                    buttonSubmit.setVisibility(View.VISIBLE);
                } else {
                    return;
                }

//                Chọn timeline


                String itemMyDate = spnMyDate.getSelectedItem().toString();
                if (itemMyDate.equals("Chọn thứ")) {
                    check = false;
                    Toast.makeText(CreateTransferActivity.this, "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
                }
                if (check == true) {
                    buttonSubmit.setVisibility(View.VISIBLE);
                } else {
                    return;
                }
//                Chọn thứ

                String itemMyshift = spnMyshift.getSelectedItem().toString();
                if (itemMyshift.equals("Chọn ca làm")) {
                    check = false;
                    Toast.makeText(CreateTransferActivity.this, "Vui lòng chọn ca  làm", Toast.LENGTH_SHORT).show();
                }
                if (check == true) {
                    buttonSubmit.setVisibility(View.VISIBLE);
                } else {
                    return;
                }
                //   Chọn ca làm

                String itemYourDate = spnYourDate.getSelectedItem().toString();
                if (itemYourDate.equals("Chọn thứ")) {
                    check = false;
                    Toast.makeText(CreateTransferActivity.this, "Vui lòng  chọn ngày", Toast.LENGTH_SHORT).show();
                }
                if (check == true) {
                    buttonSubmit.setVisibility(View.VISIBLE);
                } else {
                    return;
                }
//                Chọn thứ

                String itemYourshift = spnYourshift.getSelectedItem().toString();
                if (itemYourshift.equals("Chọn ca làm")) {
                    check = false;
                    Toast.makeText(CreateTransferActivity.this, "Vui lòng chọn ca làm", Toast.LENGTH_SHORT).show();
                }
                if (check == true) {
                    buttonSubmit.setVisibility(View.VISIBLE);
                } else {
                    return;
                }


//                Chọn ca làm

                String itemMyPosition = spnMyPosition.getSelectedItem().toString();
                if (itemMyPosition.equals("Chọn vị trí")) {
                    check = false;
                    Toast.makeText(CreateTransferActivity.this, "Vui lòng chọn vị trí", Toast.LENGTH_SHORT).show();
                }
                if (check == true) {
                    buttonSubmit.setVisibility(View.VISIBLE);
                } else {

                    return;
                }

                // Chọn vị trí

                String yourmaill = spnYourUser.getSelectedItem().toString();
                if (yourmaill.equals("Chọn nhân viên")) {
                    check = false;
                    Toast.makeText(CreateTransferActivity.this, "Vui lòng chọn nhân viên", Toast.LENGTH_SHORT).show();
                }
                if (check == true) {
                    buttonSubmit.setVisibility(View.VISIBLE);
                } else {
                    return;
                }

                String content = Content.getText().toString();

                if (content.trim().equals("")) {
                    check = false;
                    Toast.makeText(CreateTransferActivity.this, "Vui lòng nhập nội dung yêu cầu", Toast.LENGTH_SHORT).show();
                }
                if (check == true) {
                    buttonSubmit.setVisibility(View.VISIBLE);
                } else {

                    return;
                }

//----------------------------

                String MyShiftitem = spnMyshift.getSelectedItem().toString();
                String MyShifti = "";
                for (ModelString item12 : MyShift) {
                    if (item12.getData2().equals(MyShiftitem)) {
                        MyShifti = item12.getData1();
                    }
                }

                String YourShifttem = spnYourshift.getSelectedItem().toString();
                String YourShifti = "";
                for (ModelString item12 : YourShift) {
                    if (item12.getData2().equals(YourShifttem)) {
                        YourShifti = item12.getData1();
                    }
                }

                String mymaili = configData.userId(CreateTransferActivity.this);


                String item = spnMyPosition.getSelectedItem().toString();
                String Position = "";
                for (ModelString item12 : MyPosition) {
                    if (item12.getData2().equals(item)) {
                        Position = item12.getData1();
                    }
                }


                APITimeline.api.PostReportSendata(MyShifti, YourShifti, String.valueOf(id), mymaili, yourmaill, Position,content).enqueue(new Callback<List<ModelString>>() {
                    @Override
                    public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {



                    }

                    @Override
                    public void onFailure(Call<List<ModelString>> call, Throwable t) {

                    }
                });

                Intent intent = new Intent(CreateTransferActivity.this, MainRequestTransferActivity.class);
                startActivity(intent);
            }
        });

    }

    private void CallYourUser() {
        spnMyPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spnMyPosition.getSelectedItem().toString();


                if (!item.equals("Chọn vị trí")) {


                    String Codeitem = spnYourshift.getSelectedItem().toString();
                    String Code = "";

                    for (ModelString item12 : YourShift) {
                        if (item12.getData2().equals(Codeitem)) {
                            Code = item12.getData1();
                        }
                    }

                    String Position = "";
                    for (ModelString item12 : MyPosition) {
                        if (item12.getData2().equals(item)) {
                            Position = item12.getData1();
                        }
                    }


                    APITimeline.api.GetReportYourUser(Code, String.valueOf(id), Position).enqueue(new Callback<List<ModelString>>() {
                        @Override
                        public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                            YourUser = response.body();
                            FillYourUser();

                        }

                        @Override
                        public void onFailure(Call<List<ModelString>> call, Throwable t) {

                        }
                    });


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void FillYourUser() {
        List<String> list = new ArrayList<>();
        list.add("Chọn nhân viên");
        for (ModelString item : YourUser) {
            list.add(item.getData2());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnYourUser.setAdapter(adapter);
    }

    private void CallCheckPosition() {
        spnYourUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String yourmaill = spnYourUser.getSelectedItem().toString();


                if (!yourmaill.equals("Chọn nhân viên")) {


                    String MyShiftitem = spnMyshift.getSelectedItem().toString();
                    String MyShifti = "";
                    for (ModelString item12 : MyShift) {
                        if (item12.getData2().equals(MyShiftitem)) {
                            MyShifti = item12.getData1();
                        }
                    }

                    String YourShifttem = spnYourshift.getSelectedItem().toString();
                    String YourShifti = "";
                    for (ModelString item12 : YourShift) {
                        if (item12.getData2().equals(YourShifttem)) {
                            YourShifti = item12.getData1();
                        }
                    }

                    String mymaili = configData.userId(CreateTransferActivity.this);


                    String item = spnMyPosition.getSelectedItem().toString();
                    String Position = "";
                    for (ModelString item12 : MyPosition) {
                        if (item12.getData2().equals(item)) {
                            Position = item12.getData1();
                        }
                    }


                    APITimeline.api.GetReportCheckPosition(MyShifti, YourShifti, String.valueOf(id), mymaili, yourmaill, Position).enqueue(new Callback<List<ModelString>>() {
                        @Override
                        public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {


                            if (response.body().get(0).getData1().equals("done")) {

                                buttonSubmit.setVisibility(View.VISIBLE);
                            } else {

                                Toast.makeText(CreateTransferActivity.this, response.body().get(0).getData1().toString(), Toast.LENGTH_SHORT).show();
                                buttonSubmit.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ModelString>> call, Throwable t) {

                        }
                    });


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void CallMyPostion() {
        spnYourshift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spnYourshift.getSelectedItem().toString();


                if (!item.equals("Chọn ca làm")) {


                    String mail = configData.userId(CreateTransferActivity.this);
                    APITimeline.api.GetReportMyPosition(mail).enqueue(new Callback<List<ModelString>>() {
                        @Override
                        public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                            MyPosition = response.body();


                            FillMyPosition();

                        }

                        @Override
                        public void onFailure(Call<List<ModelString>> call, Throwable t) {

                        }
                    });


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void FillMyPosition() {
        List<String> list = new ArrayList<>();
        list.add("Chọn vị trí");
        for (ModelString item : MyPosition) {
            list.add(item.getData2());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnMyPosition.setAdapter(adapter);
    }

    private void CallYourShift() {
        spnYourDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spnYourDate.getSelectedItem().toString();


                if (!item.equals("Chọn thứ")) {
                    int thu = 0;
                    YourShift = new ArrayList<>();

                    for (ModelString item1 : YourDate) {

                        if (item1.getData2().equals(item)) {
                            thu = Integer.parseInt(item1.getData1());
                        }

                    }


                    int shiftcodemin = (thu - 2) * 5;

                    for (int j = shiftcodemin; j < shiftcodemin + 5; j++) {
                        ModelString modelString = new ModelString();

                        switch (j - shiftcodemin) {

                            case 0:
                                modelString.setData2("Ca sáng");
                                break;
                            case 1:
                                modelString.setData2("Ca trưa");
                                break;
                            case 2:
                                modelString.setData2("Ca chiều");
                                break;
                            case 3:
                                modelString.setData2("Ca tối");
                                break;
                            default:
                                modelString.setData2("Ca đêm");
                                break;

                        }
                        modelString.setData1(String.valueOf(j));
                        YourShift.add(modelString);
                    }

                    FillYourShift();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void FillYourShift() {

        List<String> list = new ArrayList<>();
        list.add("Chọn ca làm");
        for (ModelString item : YourShift) {
            list.add(item.getData2());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnYourshift.setAdapter(adapter);
    }

    private void CallYourDate() {
        spnMyshift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spnMyshift.getSelectedItem().toString();


                if (!item.equals("Chọn ca làm")) {


                    String mail = configData.userId(CreateTransferActivity.this);
                    APITimeline.api.GetReportMyDate(mail, String.valueOf(id)).enqueue(new Callback<List<ModelString>>() {
                        @Override
                        public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                            YourDate = response.body();

                            FillYourDate();

                        }

                        @Override
                        public void onFailure(Call<List<ModelString>> call, Throwable t) {

                        }
                    });


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void FillYourDate() {
        List<String> list = new ArrayList<>();
        list.add("Chọn thứ");
        for (ModelString item : YourDate) {
            list.add(item.getData2());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnYourDate.setAdapter(adapter);
    }

    private void CallMyShift() {
        spnMyDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spnMyDate.getSelectedItem().toString();


                if (!item.equals("Chọn thứ")) {
                    for (ModelString item1 : MyDate) {
                        if (item1.getData2().equals(item)) {
                            number = Integer.parseInt(item1.getData1());
                        }
                    }


                    String mail = configData.userId(CreateTransferActivity.this);
                    APITimeline.api.GetReportMyShift(mail, String.valueOf(id), String.valueOf(number)).enqueue(new Callback<List<ModelString>>() {
                        @Override
                        public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                            MyShift = response.body();

                            FillMyShhiff();

                        }

                        @Override
                        public void onFailure(Call<List<ModelString>> call, Throwable t) {

                        }
                    });


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void FillMyShhiff() {
        List<String> list = new ArrayList<>();
        list.add("Chọn ca làm");
        for (ModelString item : MyShift) {
            list.add(item.getData2());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnMyshift.setAdapter(adapter);
    }

    private void CallMyDate() {
        spnTimeline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spnTimeline.getSelectedItem().toString();


                if (!item.equals("Chọn timeline")) {
                    for (ModelString item1 : Listimeline) {
                        if (item1.getData2().equals(item)) {
                            id = Integer.parseInt(item1.getData1());
                        }
                    }

                    String mail = configData.userId(CreateTransferActivity.this);
                    APITimeline.api.GetReportMyDate(mail, String.valueOf(id)).enqueue(new Callback<List<ModelString>>() {
                        @Override
                        public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                            MyDate = response.body();

                            FillMyDate();

                        }

                        @Override
                        public void onFailure(Call<List<ModelString>> call, Throwable t) {

                        }
                    });


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void FillMyDate() {

        List<String> list = new ArrayList<>();
        list.add("Chọn thứ");
        for (ModelString item : MyDate) {
            list.add(item.getData2());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnMyDate.setAdapter(adapter);
    }


    private void CallTimeline() {
        List<String> list = new ArrayList<>();

        String mail = configData.userId(this);
        APITimeline.api.GetReportChooseTimeline(mail).enqueue(new Callback<List<ModelString>>() {
            @Override
            public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                Listimeline = response.body();
                FillTimeline();

            }

            @Override
            public void onFailure(Call<List<ModelString>> call, Throwable t) {

            }
        });


    }

    private void FillTimeline() {
        List<String> list = new ArrayList<>();
        list.add("Chọn timeline");
        for (ModelString item : Listimeline) {
            list.add(item.getData2());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnTimeline.setAdapter(adapter);

    }

    private void buttonBack() {
        ImageButton button = findViewById(R.id.btn_Work_schedule_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTransferActivity.this, MainTransferActivity.class);
                startActivity(intent);
            }
        });
    }
}