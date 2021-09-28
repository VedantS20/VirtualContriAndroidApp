package com.vedant.virtualcontrife;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import java.util.Random;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class groupcreate_fragment extends Fragment {
    TextInputEditText name , description;
    TextInputLayout newgroupbox , descriptionbox;
    MaterialButton Create;
    MaterialButton GetImage;
    ImageView imageView;
    Uri Imageuri;
    public static final int GALLARY_REQUEST_CODE = 123;
    group_fragment gfrag = new group_fragment();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("TAG", "onCreateView GroupCreate: "+SharedPrefManager.getInstance(getContext()).getRefreshToken());
       View v = inflater.inflate(R.layout.groupcreatefragment , container,false);
        MainActivity activity = (MainActivity) getActivity();
        activity.ShowButton();
       name = v.findViewById(R.id.new_group_name);
       Create = v.findViewById(R.id.create);
       GetImage = v.findViewById(R.id.getimage);
       imageView = v.findViewById(R.id.imageview);
       newgroupbox = v.findViewById(R.id.newgroupnamebox);
       description = v.findViewById(R.id.new_group_description);
       descriptionbox = v.findViewById(R.id.new_group_description_box);
       GetImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(intent , "Pick an Image"),GALLARY_REQUEST_CODE);
           }
       });
       Create.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                   String Name = name.getText().toString();
                   String Des = description.getText().toString();
                   if(Name.isEmpty())
                   {
                    newgroupbox.setError("Enter Valid Name");
                   }
                   else if(Des.isEmpty())
                   {
                       descriptionbox.setError("Enter Description");
                   }
                   else {
//                       Bundle bundle = new Bundle();
//                       bundle.putString("namegrp", Name);
//                       bundle.putString("description" , Des);
//                       bundle.putString("Imageurl", String.valueOf(Imageuri));
                       JsonObject jsonObject = new JsonObject();
                       jsonObject.addProperty("group_name" , Name);
                       jsonObject.addProperty("description" , Des);
                      // String Token = SharedPrefManager.getInstance(getContext()).getToken();
                      // String AUTH_TOKEN = "Bearer " + Token;
                       Call<ResponseBody> call = RetrofitClient.getInstance(getContext()).getApi().createGroup(jsonObject);
                       call.enqueue(new Callback<ResponseBody>() {
                           @Override
                           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                               try {
                                   if (response.code() == 200) {
                                       String res = response.body().string();
                                       JSONObject jsonObject1 = new JSONObject(res);
                                       Toasty.success(getContext(), jsonObject1.getString("message"), Toast.LENGTH_SHORT).show();
                                       getFragmentManager().beginTransaction().replace(R.id.fragment_container, gfrag).addToBackStack("create").commit();
                                   } else {
                                       Toasty.warning(getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                   }
                               } catch (IOException | JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                           @Override
                           public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toasty.error(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                           }
                       });
                       //gfrag.setArguments(bundle);

                      // Toasty.success(getContext() , "Group Created Successfully" , Toast.LENGTH_SHORT).show();
                   }
           }
       });
       return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLARY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            Uri imageData = data.getData();
            Imageuri = imageData;
            imageView.setImageURI(Imageuri);
        }
    }
//    public static String getRandomString(int length)
//    {
//        final String charac = "A1B2C3D4E5F6G7H8I9JKLMNOPQRST0UVWXYZ";
//        StringBuilder result = new StringBuilder();
//        while (length>0)
//        {
//            Random ran = new Random();
//            result.append(charac.charAt(ran.nextInt(charac.length())));
//            length--;
//        }
//        return result.toString();
//    }
}
