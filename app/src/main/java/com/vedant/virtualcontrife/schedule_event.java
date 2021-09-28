package com.vedant.virtualcontrife;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class schedule_event extends Fragment {
    TextInputEditText Date, Time , Winamount;
    TextInputLayout Date_box, Time_box , Winamount_box;
    MaterialButton Schedule;
    private String time1;
    private String Date1;
    private String date1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule_event, container, false);
        Date = v.findViewById(R.id.date);
        Date_box = v.findViewById(R.id.date_box);
        Schedule = v.findViewById(R.id.schedule);
        Time = v.findViewById(R.id.time);
        Time_box = v.findViewById(R.id.time_box);
        Winamount = v.findViewById(R.id.winamount);
        Winamount_box = v.findViewById(R.id.winamount_box);
        assert getArguments() != null;
        final int pos = getArguments().getInt("Pos");
        //TODO:add the constraints to calender (should not select the old dates)
        final MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = builder.build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                String datemilisec = selection.toString();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                long miliseconds = Long.parseLong(datemilisec);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(miliseconds);
                date1 = formatter.format(calendar.getTime());
                Date.setText(formatter.format(calendar.getTime()));
                //Date.setText(materialDatePicker.getHeaderText());
            }
        });
        Date_box.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getFragmentManager(), "DATE_PICKER");
            }
        });
        Time_box.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        SimpleDateFormat f24hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date date = f24hours.parse(time);
                            SimpleDateFormat f12hours = new SimpleDateFormat(
                                    "hh:mm aa"
                            );
                            time1 = time;
                            Time.setText(time);
                            //settext
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        Schedule.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String amount = Winamount.getText().toString();
                int flag = 0;
                if (date1.isEmpty())
                    Date_box.setError("Enter Date!");
                else
                    flag++;
                if (time1.isEmpty())
                    Time_box.setError("Enter Time!");
                else
                    flag++;
                if (amount.isEmpty())
                    Winamount_box.setError("Enter Amount!");
                else
                    flag++;
                if (flag == 3) {
                    DateTimeFormatter inputformat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
                    DateTimeFormatter outputformat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                    LocalDateTime datetime = LocalDateTime.parse(date1 + " " + time1, inputformat);
                    String formatdate = outputformat.format(datetime);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("start_date", formatdate);
                    jsonObject.addProperty("winning_amount" , amount);
                    try {
                        jsonObject.addProperty("group" , SharedPrefManager.getInstance(getContext()).getres().getData().get(pos).getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Call<ResponseBody> call = RetrofitClient.getInstance(getContext()).getApi().Createschedule(jsonObject);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String res = response.body().string();
                                JSONObject jsonObject1 = new JSONObject(res);
                                SharedPrefManager.getInstance(getContext()).saveSchid(jsonObject1);
                                Log.d("TAG", "onResponse: "+SharedPrefManager.getInstance(getContext()).getSchid());
                                Toasty.success(getContext() , jsonObject1.getString("message") , Toast.LENGTH_SHORT).show();
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    Log.d("TAG", "onClick: " + formatdate);
                     getFragmentManager().popBackStack();
                }
            }
        });
        return v;
    }
}