package com.vedant.virtualcontrife;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class quiz_create extends Fragment {
    private TextInputLayout QuestionBox , Option1Box ,Option2Box ,Option3Box ,Option4Box,dropdownbox;
    private TextInputEditText Question , Option1,Option2,Option3,Option4;
    private MaterialButton Save;
    private AutoCompleteTextView dropdownitem;
    private int serialnumber =1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz_create, container, false);
        MainActivity activity = (MainActivity) getActivity();
        activity.ShowButton();
        //TODO:issue on back pressed.
        //TODO:onclick method of backbutton popbackstack.
        QuestionBox = v.findViewById(R.id.question_box);
        Option1Box = v.findViewById(R.id.option1_box);
        Option2Box = v.findViewById(R.id.option2_box);
        Option3Box = v.findViewById(R.id.option3_box);
        Option4Box = v.findViewById(R.id.option4_box);
        Question = v.findViewById(R.id.question);
        Option1 = v.findViewById(R.id.option1);
        Option2 = v.findViewById(R.id.option2);
        Option3 = v.findViewById(R.id.option3);
        Option4 = v.findViewById(R.id.option4);
        dropdownbox = v.findViewById(R.id.dropdown_answer);
        dropdownitem = v.findViewById(R.id.dropdown_items);
        Save = v.findViewById(R.id.save_question_button);
        String[] items = new String[] {"1", "2", "3", "4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext() , R.layout.dropdown_menu_popup,items);
        dropdownitem.setAdapter(adapter);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ques = Question.getText().toString();
                String Opt1 = Option1.getText().toString();
                String Opt2 = Option2.getText().toString();
                String Opt3 = Option3.getText().toString();
                String Opt4 = Option4.getText().toString();
                String item = dropdownitem.getText().toString();
                Log.d("TAG", "onCreateView:it "+item);
                int flag=0;
                if(Ques.isEmpty())
                    QuestionBox.setError("Enter Question!!");
                else
                    flag++;
                if(Opt1.isEmpty())
                    Option1Box.setError("Enter Option!!");
                else
                    flag++;
                if(Opt2.isEmpty())
                    Option2Box.setError("Enter Option!!");
                else
                    flag++;
                if(Opt3.isEmpty())
                    Option3Box.setError("Enter Option!!");
                else
                    flag++;
                if(Opt4.isEmpty())
                    Option4Box.setError("Enter Option!!");
                else
                    flag++;
                if(item.isEmpty())
                        dropdownbox.setError("Select the Correct Answer");
                else
                    flag++;
                if(flag == 6)
                {
//                    ArrayList<String> optionlist = new ArrayList<>();
//                    optionlist.add(0,Opt1);
//                    optionlist.add(1,Opt2);
//                    optionlist.add(2,Opt3);
//                    optionlist.add(3,Opt4);
//                    Log.d("TAG", "onClick:optionlist " +optionlist);
                    int ranswer = Integer.parseInt(dropdownitem.getText().toString());
                    JsonArray jsonArray = new JsonArray();
                    jsonArray.add(Opt1);
                    jsonArray.add(Opt2);
                    jsonArray.add(Opt3);
                    jsonArray.add(Opt4);
                    Log.d("TAG", "onClick:jsonarray "+jsonArray.toString());
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("question" , Ques);
                    jsonObject.add("answers" , jsonArray);
                    jsonObject.addProperty("rightAnswer" ,ranswer);
                    jsonObject.addProperty("serial_number" , serialnumber);
                    Call<ResponseBody> call = RetrofitClient.getInstance(getContext()).getApi().Addquestion(SharedPrefManager.getInstance(getContext()).getquizid(),jsonObject);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            String res = null;
                            try {
                                res = response.body().string();
                                JSONObject jsonObject1 = new JSONObject(res);
                                SharedPrefManager.getInstance(getContext()).saveQuestionid(jsonObject1);
                                Toasty.success(getContext() , jsonObject1.getString("message") , Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "onResponse:questionid "+SharedPrefManager.getInstance(getContext()).getQuestionid());
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT).show();
                        }
                    });
                    QuestionBox.setError(null);
                    Option1Box.setError(null);
                    Option2Box.setError(null);
                    Option3Box.setError(null);
                    Option4Box.setError(null);
                    dropdownbox.setError(null);
                   // Toast.makeText(getContext() , "saved" , Toast.LENGTH_SHORT).show();
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                    builder.setTitle("Alert");
                    builder.setMessage("Want to create one more ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Question.getText().clear();
                            Option1.getText().clear();
                            Option2.getText().clear();
                            Option3.getText().clear();
                            Option4.getText().clear();
                            dropdownitem.getText().clear();
                            serialnumber++;
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            assert getFragmentManager() != null;
                            getFragmentManager().popBackStack();
                        }
                    });
                    builder.show();
                }

            }
        });
        return v;
    }
}  