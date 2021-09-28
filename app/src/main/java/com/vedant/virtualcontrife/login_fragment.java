
//TODO:THIS FRAGMENT IS NOT IN USE CURRENTLY...

package com.vedant.virtualcontrife;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
public class login_fragment extends Fragment {
    TextInputEditText Email;
    TextInputLayout Emaillayout;
    MaterialButton Submit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.loginfragment , container,false);
       Email = v.findViewById(R.id.email);
       Submit = v.findViewById(R.id.submit);
       Emaillayout = v.findViewById(R.id.emailbox);
           Submit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String mail = Email.getText().toString();
                   if(mail.isEmpty()) {
                       Emaillayout.setError("Enter Valid Email");
                   }
                   else {
                       Bundle bundle = new Bundle();
                       bundle.putString("Email" , mail);
                       signup_fragment signup = new signup_fragment();
                       signup.setArguments(bundle);
                       getFragmentManager().beginTransaction().replace(R.id.fragment_container, signup).commit();
                   }
               }
           });
       return v;
    }
}
