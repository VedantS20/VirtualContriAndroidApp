package com.vedant.virtualcontrife;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.jar.Attributes;

public class Profile_fragment extends Fragment {
    private TextInputEditText Name , Phone , Email;
    private TextInputLayout Fnamelay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.profile_fragment , container,false);
        MainActivity activity = (MainActivity) getActivity();
        activity.ShowButton();
        Name = v.findViewById(R.id.name_profile_fragment);
        Phone = v.findViewById(R.id.phone_profile_fragment);
        Email = v.findViewById(R.id.email_profile_fragment);
        User user = SharedPrefManager.getInstance(getContext()).getUser();
        Name.setText(user.getName());
        Phone.setText(user.getPhone());
        Email.setText(user.getEmail());
        return v;
    }
}
