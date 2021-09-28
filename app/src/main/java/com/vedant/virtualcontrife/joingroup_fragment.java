package com.vedant.virtualcontrife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class joingroup_fragment extends Fragment implements Backpressed{
    TextInputLayout Privateidbox, NameBox;
    TextInputEditText Privateid, Name;
    MaterialButton Join;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.joimgroupfragment, container, false);
        MainActivity activity = (MainActivity) getActivity();
        activity.ShowButton();
        Privateidbox = v.findViewById(R.id.id_box_join);
        Privateid = v.findViewById(R.id.id_join);
        NameBox = v.findViewById(R.id.name_box_join);
        Name = v.findViewById(R.id.name_join);
        Join = v.findViewById(R.id.join_button);
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Privateid.getText().toString();
                String name = Name.getText().toString();
                if (id.isEmpty())
                    Privateidbox.setError("Enter Private Code");
                if (name.isEmpty())
                    NameBox.setError("Enter Name");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", name);
                jsonObject.addProperty("code", id);
               // String Token = SharedPrefManager.getInstance(getContext()).getToken();
               // String AUTH_TOKEN = "Bearer " + Token;
                Call<ResponseBody> call = RetrofitClient.getInstance(getContext()).getApi().JoinGroup(jsonObject);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.code() == 200) {
                                String res = response.body().string();
                                JSONObject jsonObject1 = new JSONObject(res);
                                Toasty.success(getContext(), jsonObject1.getString("message"), Toast.LENGTH_SHORT).show();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container , new group_fragment()).commit();
                            } else {
                                Toasty.warning(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
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
            }
        });
        return v;
    }

    @Override
    public boolean onBackPressed() {
        getFragmentManager().popBackStack();
        return true;
    }
}
