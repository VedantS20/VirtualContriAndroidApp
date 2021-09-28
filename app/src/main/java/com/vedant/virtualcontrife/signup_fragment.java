
//TODO:THIS FRAGMENT IS NOT IN USE CURRENTLY...

package com.vedant.virtualcontrife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class signup_fragment extends Fragment {
    MaterialButton Login , Resend , Change;
    TextView Mail;
    TextInputLayout OtpBox;
    TextInputEditText Otp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.signupfragment , container , false);
        Login = v.findViewById(R.id.login);
        Resend = v.findViewById(R.id.resendotp);
        Change = v.findViewById(R.id.changeemail);
        OtpBox = v.findViewById(R.id.otpbox);
        Mail = v.findViewById(R.id.emailfromone);
        Otp = v.findViewById(R.id.otp);
        Mail.setText(getArguments().getString("Email"));
        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container , new login_fragment()).commit();
            }
        });
        return v;
    }
}
