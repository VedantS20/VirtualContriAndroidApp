package com.vedant.virtualcontrife;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration_fragment extends Fragment {
    TextInputLayout FirstNameBox, LastNameBox, PhoneBox, EmailBox, UsernameBox, PasswordBox, ConfirmPasswordBox;
    TextInputEditText FirstName, LastName, Phone, Email, Username, Password, ConfirmPassword;
    MaterialButton SignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registrationfragment, container, false);
        MainActivity activity = (MainActivity) getActivity();
        activity.ShowButton();
        FirstNameBox = v.findViewById(R.id.first_name_box);
        LastNameBox = v.findViewById(R.id.last_name_box);
        PhoneBox = v.findViewById(R.id.phone_box);
        EmailBox = v.findViewById(R.id.email_box);
        UsernameBox = v.findViewById(R.id.username_box);
        PasswordBox = v.findViewById(R.id.password_box);
        ConfirmPasswordBox = v.findViewById(R.id.cof_password_box);
        FirstName = v.findViewById(R.id.first_name);
        LastName = v.findViewById(R.id.last_name);
        Phone = v.findViewById(R.id.phone);
        Email = v.findViewById(R.id.email);
        Username = v.findViewById(R.id.username);
        Password = v.findViewById(R.id.password);
        ConfirmPassword = v.findViewById(R.id.conf_password);
        SignUp = v.findViewById(R.id.signup_button);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = FirstName.getText().toString();
                String lName = LastName.getText().toString();
                String Ph = Phone.getText().toString();
                String Mail = Email.getText().toString();
                String User = Username.getText().toString();
                String Pass = Password.getText().toString();
                String CPass = ConfirmPassword.getText().toString();
                int flag = 0;
                if (fName.isEmpty())
                    FirstNameBox.setError("Field must not be empty");
                else flag++;
                if (lName.isEmpty())
                    LastNameBox.setError("Field must not be empty");
                else flag++;
                if (Ph.isEmpty())
                    PhoneBox.setError("Field must not be empty");
                else flag++;
                if (Mail.isEmpty())
                    EmailBox.setError("Field must not be empty");
                else flag++;
                if (User.isEmpty())
                    UsernameBox.setError("Field must not be empty");
                else flag++;
                if (Pass.isEmpty())
                    PasswordBox.setError("Field must not be empty");
                else flag++;
                if (CPass.isEmpty())
                    ConfirmPasswordBox.setError("Field must not be empty");
                else flag++;
                if (flag == 7) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name" , fName +" "+lName);
                    jsonObject.addProperty("password" , Pass);
                    jsonObject.addProperty("email" , Mail);
                    jsonObject.addProperty("phone" , Ph);
                    Call<ResponseBody> call = RetrofitClient.getInstance(getContext())
                            .getApi()
                            .RegisterUser(jsonObject);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String s = response.body().string();
                                JSONObject jsonObject = new JSONObject(s);
                                if(response.code() == 200) {
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginPage_fragment()).commit();
                                    Toasty.success(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toasty.warning(getContext() , jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toasty.warning(getContext(), "Enter Valid Info", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
}
