package com.vedant.virtualcontrife;

import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class groupdetail extends Fragment {
    private ImageView group_image;
    private TextView Group_name, Member_count, Members;
    private MaterialButton Schedule, Copy, Share, Startgame;
    private TextView Code;
    private CharSequence[] AlertItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_groupdetail, container, false);
        Group_name = v.findViewById(R.id.newgroupname);
        Member_count = v.findViewById(R.id.member_count);
        Members = v.findViewById(R.id.members_name);
        Schedule = v.findViewById(R.id.schedule_event_button);
        Code = v.findViewById(R.id.code);
        Copy = v.findViewById(R.id.copy_button);
        Share = v.findViewById(R.id.share_button);
        Startgame = v.findViewById(R.id.start_game_button);
        final MainActivity activity = (MainActivity) getActivity();
        activity.ShowButton();
        final int Position = getArguments().getInt("Position");
        final Bundle bundle = new Bundle();
        bundle.putInt("Pos", Position);
        Log.d("TAG", "onCreateView:position " + Position);
        try {
            Group_name.setText(SharedPrefManager.getInstance(getContext()).getres().getData().get(Position).getGroupName());
            Code.setText(SharedPrefManager.getInstance(getContext()).getres().getData().get(Position).getCode());
            int mem_count = SharedPrefManager.getInstance(getContext()).getres().getData().get(Position).getMembers().size();
            if (mem_count == 1)
                Member_count.setText(String.valueOf(mem_count) + " Member");
            else
                Member_count.setText(String.valueOf(mem_count) + " Members");
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < SharedPrefManager.getInstance(getContext()).getres().getData().get(Position).getMembers().size(); i++) {
                arrayList.add(i, SharedPrefManager.getInstance(getContext()).getres().getData().get(Position).getMembers().get(i).getName());
            }
            String memberlist = String.join(",", arrayList);
            Members.setText(memberlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                try {
                    assert clipboardManager != null;
                    clipboardManager.setText(SharedPrefManager.getInstance(getContext()).getres().getData().get(Position).getCode());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toasty.success(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                try {
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey,You Can Join My Group Named " + SharedPrefManager.getInstance(getContext()).getres().getData().get(Position).getGroupName() + " By Using This Code " + SharedPrefManager.getInstance(getContext()).getres().getData().get(Position).getCode());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedule_event sch = new schedule_event();
                sch.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, sch).addToBackStack("sch").commit();
            }
        });
        AlertItems = new CharSequence[]{
                "Create Your Own Quiz",
                "Play From the Existing"
        };
        Startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final ArrayList<String> arrayList = new ArrayList<>();
//                arrayList.add(0,"Create Your Own Quiz");
//                arrayList.add(1,"Play From the Existing");
                final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                //builder.setTitle("Select Your Choice");
                builder.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg, null));
                builder.setItems(AlertItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        if (which == 0) {
                            final MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder(getContext());
                            final AlertDialog alertDialog = builder1.create();
                            builder1.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg, null));
                            LayoutInflater inflater1 = getLayoutInflater();
                            final View dialogview = inflater1.inflate(R.layout.custom_dialog, null);
                            final TextInputLayout quiznambox = dialogview.findViewById(R.id.quiz_name_box);
                            final TextInputEditText quizname = dialogview.findViewById(R.id.quiz_name);
                            MaterialButton ok = dialogview.findViewById(R.id.save_name_quiz);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String name = quizname.getText().toString();
                                    if (name.isEmpty()) {
                                        quiznambox.setError("Name Cannot be Empty");
                                    } else {
                                        JsonObject jsonObject = new JsonObject();
                                        jsonObject.addProperty("quiz", name);
                                        jsonObject.addProperty("autoplay", false);
                                        try {
                                            jsonObject.addProperty("group", SharedPrefManager.getInstance(getContext()).getres().getData().get(Position).getId());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Call<ResponseBody> call = RetrofitClient.getInstance(getContext()).getApi().CreateQuiz(jsonObject);
                                        call.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                try {
                                                    String res = response.body().string();
                                                    JSONObject jsonObject1 = new JSONObject(res);
                                                    SharedPrefManager.getInstance(getContext()).savequizid(jsonObject1);
                                                    Toasty.success(getContext(), jsonObject1.getString("message"), Toast.LENGTH_SHORT).show();
                                                } catch (JSONException | IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        alertDialog.dismiss();
                                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new quiz_create()).addToBackStack("quiz").commit();
                                    }
                                }
                            });
                            alertDialog.setView(dialogview);
                            alertDialog.show();
                            // getFragmentManager().beginTransaction().replace(R.id.fragment_container, new quiz_create()).addToBackStack("quiz").commit();
                        }
                    }
                });
                builder.show();
            }
        });
        //EvenSchedule.setText(getArguments().getString("Date" , "0")+""+getArguments().getString("Time" , "0"));
        return v;
    }
}