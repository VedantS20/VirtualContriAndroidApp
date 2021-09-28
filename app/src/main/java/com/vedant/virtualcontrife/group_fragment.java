package com.vedant.virtualcontrife;

import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class group_fragment extends Fragment implements Backpressed {
    private ViewStub stublist;
    private ViewStub stubgrid;
    private ListView listView;
    private GridView gridView;
    private FloatingActionButton Fab_add;
    private TextView Nogrp;
    //private Button SwitchButton;
    private ImageView SwitchButton , RefreshButton;
    private listViewAdapter ladapter;
    private gridViewAdapter gadapter;
    private ExtendedFloatingActionButton createNewGroup , joingroup;
    private androidx.appcompat.widget.Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private List<Group>Grouplist;
    private int currentViewmode = 0;
    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    //private ProgressBar progressBar;
    private boolean isOpen;
    private ShimmerFrameLayout shimmerFrameLayout;
    private String grp_name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.groupfragment , container,false);
        MainActivity activity = (MainActivity) getActivity();
        activity.HideButton();
       setHasOptionsMenu(true);
        stublist = v.findViewById(R.id.stub_list);
        stubgrid = v.findViewById(R.id.stub_grid);
        SwitchButton = v.findViewById(R.id.switchbutton);
        toolbar = v.findViewById(R.id.app_bar);
        drawerLayout = v.findViewById(R.id.drawer_layout);
        navigationView = v.findViewById(R.id.navigation_view);
        createNewGroup = v.findViewById(R.id.create_new_group_button);
        joingroup = v.findViewById(R.id.join_group);
        RefreshButton = v.findViewById(R.id.reload_button);
        Nogrp = v.findViewById(R.id.nogrptext);
       // progressBar = v.findViewById(R.id.progressBar);
        shimmerFrameLayout = v.findViewById(R.id.shrimmer_layout_frag);
        RefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().detach(group_fragment.this).attach(group_fragment.this).commit();
            }
        });
        final AnimatedVectorDrawable animatedVectorDrawable1 = (AnimatedVectorDrawable) v.getResources().getDrawable(R.drawable.avd_anim1);
        final AnimatedVectorDrawable animatedVectorDrawable2 = (AnimatedVectorDrawable) v.getResources().getDrawable(R.drawable.avd_anim2);
        SwitchButton.setImageDrawable(animatedVectorDrawable1);
        SwitchButton.setSelected(false);
        stublist.inflate();
        stubgrid.inflate();
        Fab_add = v.findViewById(R.id.fab_add);
        listView = v.findViewById(R.id.list_view);
        gridView = v.findViewById(R.id.grid_view);
        //String Token = SharedPrefManager.getInstance(getContext()).getToken();
       // String AUTH_TOKEN = "Bearer "+Token;
        Log.d("TAG", "onCreateView: "+SharedPrefManager.getInstance(getContext()).getRefreshToken());
        Call<ResponseBody> call = RetrofitClient.getInstance(getContext()).getApi().getGroups();
       // Log.d("TAG", "onCreateView: " +AUTH_TOKEN);
        Log.d("TAG", "onCreateView: "+call.request().toString());
       // progressBar.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.code() == 200) {
                        String grp = response.body().string();
                        JSONObject jsonObject = new JSONObject(grp);
                        SharedPrefManager.getInstance(getContext()).saveGroupdetails(jsonObject);
                        Log.d("TAG", "onResponse: " +jsonObject.getJSONArray("data").getJSONObject(0).getString("group_name"));
                        GroupResponse groupResponse = new GroupResponse();
                        groupResponse = SharedPrefManager.getInstance(getContext()).getres();
                        Log.d("TAG", "onResponse: " + groupResponse.getData().get(0).getMembers().get(0).getName());
                        Toasty.success(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onResponse: "+getGrouplist());
                        if(getGrouplist().isEmpty())
                        {
                               Nogrp.setVisibility(View.VISIBLE);
                        }
                        getGrouplist();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        //progressBar.setVisibility(View.INVISIBLE);
                        switchView();
                    }
                    else if(response.code() == 400)
                    {
                        Toasty.warning(getContext() , "Error" , Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.error(getContext() ,t.getMessage() ,Toast.LENGTH_SHORT).show();
            }
        });
//        try {
//                getGrouplist();
//            Log.d("TAG", "onCreateView: "+getGrouplist());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Viewmode" , MODE_PRIVATE);
        currentViewmode  = sharedPreferences.getInt("currentViewmode" , VIEW_MODE_LISTVIEW);
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);
        //switchView();
        createNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new groupcreate_fragment()).addToBackStack("create").commit();
            }
        });
        joingroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container , new joingroup_fragment()).addToBackStack("join").commit();
            }
        });
        SwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonAnimation(animatedVectorDrawable1 , animatedVectorDrawable2);
                if(VIEW_MODE_LISTVIEW == currentViewmode)
                {
                    currentViewmode = VIEW_MODE_GRIDVIEW;
                }
                else {
                    currentViewmode = VIEW_MODE_LISTVIEW;
                }
                switchView();
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("Viewmode" , MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentviewmode" , currentViewmode);
                editor.commit();
            }
        });
        isOpen = false;
        Fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen)
                {
                    createNewGroup.setVisibility(View.INVISIBLE);
                    joingroup.setVisibility(View.INVISIBLE);
                    isOpen = false;
                }
                else
                {
                    createNewGroup.setVisibility(View.VISIBLE);
                    joingroup.setVisibility(View.VISIBLE);
                    isOpen = true;
                }
            }
        });
        logout();
        NevigationDrawerFunction();//Navigation Drawer
        return v;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Group> getGrouplist() throws JSONException {
        Grouplist = new ArrayList<>();
        try {
            GroupResponse groupResponse = SharedPrefManager.getInstance(getContext()).getres();
            User2 user2 = SharedPrefManager.getInstance(getContext()).getloginres();
            for(int j=0 ; j<groupResponse.getData().size();j++)
            {
                for(int i=0 ; i<groupResponse.getData().get(i).getMembers().size();i++)
                {
                    if(groupResponse.getData().get(j).getMembers().get(i).getVerified())
                    {
                        Member2 member2 = groupResponse.getData().get(j).getMembers().get(i);
                        if(member2.getUser().getEmail().equals(user2.getEmail()))
                        {
                            Grouplist.add(new Group(null , groupResponse.getData().get(j).getGroupName() , groupResponse.getData().get(j).getDescription()));
//                            ArrayList<Integer> arrayList = new ArrayList<>();
//                                arrayList.add(j);
//                            SharedPrefManager.getInstance(getContext()).saveindex(arrayList);
                        }
                    }
                }
            }
//            int size = groupResponse.getData().size();
//            for(int k=0 ; k<size;k++)
//            {
//                Grouplist.add(new Group(null , groupResponse.getData().get(k).getGroupName() , groupResponse.getData().get(k).getDescription()));
//            }
        } catch (JSONException | NullPointerException e ) {
            e.printStackTrace();
            Log.d("TAG", "getGrouplist: "+ e.getCause());
        }
        return Grouplist;
    }
    private void switchView() {
        if(VIEW_MODE_LISTVIEW == currentViewmode)
        {
            stublist.setVisibility(View.VISIBLE);
            stubgrid.setVisibility(View.GONE);
        }
        else {
            stublist.setVisibility(View.GONE);
            stubgrid.setVisibility(View.VISIBLE);
        }
            setAdapter();
    }

    private void setAdapter() {
        Log.d("TAG", "setAdapter: "+"here");
        try {
            if(VIEW_MODE_LISTVIEW == currentViewmode)
            {
                ladapter = new listViewAdapter(Objects.requireNonNull(getContext()), R.layout.list_item, Grouplist);
                 listView.setAdapter(ladapter);
            }
            else {
                gadapter = new gridViewAdapter(Objects.requireNonNull(getContext()), R.layout.grid_item , Grouplist);
                gridView.setAdapter(gadapter);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int index=0;
            Log.d("TAG", "onItemClick: "+position);
            Toast.makeText(getContext() , Grouplist.get(position).getGroupName() + " - "+Grouplist.get(position).getDescription() , Toast.LENGTH_SHORT).show();
            try {
                List<Datum> list = SharedPrefManager.getInstance(getContext()).getres().getData();
                for(int i=0;i<list.size();i++)
                {
                    if(list.get(i).getGroupName().equals(Grouplist.get(position).getGroupName()) && list.get(i).getDescription().equals(Grouplist.get(position).getDescription()))
                    {
                        index = i;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Bundle bundle = new Bundle();
                bundle.putInt("Position" ,index);
                groupdetail gd = new groupdetail();
                gd.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container , gd).addToBackStack("detail").commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    };
    public void ButtonAnimation(AnimatedVectorDrawable a1 , AnimatedVectorDrawable a2)
    {
        if(SwitchButton.isSelected())
        {
            SwitchButton.setSelected(false);
            SwitchButton.setImageDrawable(a2);
        }
        else {
            SwitchButton.setSelected(true);
            SwitchButton.setImageDrawable(a1);
        }
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) SwitchButton.getDrawable();
       // SwitchButton.getDrawable();
        Drawable drawable = animatedVectorDrawable.getCurrent();
        if(drawable instanceof Animatable)
        {
            ((Animatable) drawable).start();
        }
    }//buttonAnim

    public void NevigationDrawerFunction()
    {
        View header = navigationView.getHeaderView(0);
        TextView Name = header.findViewById(R.id.profile_name);
        User user = SharedPrefManager.getInstance(getContext()).getUser();
        Name.setText(user.getName());
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.NavdrawerOpen , R.string.NavdrawerClose);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.profile_drawer:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container , new Profile_fragment()).addToBackStack("profile").commit();
                }
                return true;
            }
        });


    }//drawer
    public void logout()
    {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.logout :
                        FragmentManager fragmentManager = getFragmentManager();
                        int backstackcount = fragmentManager.getBackStackEntryCount();
                        for(int i=0 ; i<backstackcount ; i++) //clear all backstacks
                        {
                            fragmentManager.popBackStack();
                        }
                        SharedPrefManager.getInstance(getContext()).Clear();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container , new LoginPage_fragment()).commit();
                        break;
                }
                return true;
            }
        });
    }//logout

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }

    @Override
    public boolean onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
}
