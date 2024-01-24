package com.anshu.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.anshu.chatapp.Adepter.TabAdapter;
import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivityMainBinding;
import com.anshu.chatapp.Fragments.fragment_calls;
import com.anshu.chatapp.Fragments.fragment_chats;
import com.anshu.chatapp.Fragments.fragment_status;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ActivityMainBinding binding;
    Context context = this;
    private TabAdapter mTabAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFabBottom;
    private FloatingActionButton mFabTop;
    private static boolean fabTopVisible = false;
    private static final int RC_PHOTO_PICKER = 2;
    private Toolbar mToolbar;
    private AppBarLayout mAppBarLayout;

    private FloatingActionButton mFab;
    private SharedPrefHelper sharedPrefHelper;
    private MenuItem cameraItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AllIniClizeID();

        String UserName = sharedPrefHelper.getString("UserName", "");
        if (!UserName.equals("")) {
            binding.toolbar.setTitle(UserName);
        }
        setSupportActionBar(binding.toolbar);

        mTabAdapter = new TabAdapter(this, getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setCurrentItem(1);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_camera_alt_white_24dp);

        LinearLayout layout = ((LinearLayout) ((LinearLayout) mTabLayout.getChildAt(0)).getChildAt(0));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.weight = 0.5f;
        layout.setLayoutParams(layoutParams);


        fabSettings();

    }

    private void fabSettings() {
        if (mViewPager.getCurrentItem() == 1) {
            mFabBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(new Intent(MainActivity.this, ContactActivity.class));
                }
            });
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        mFabTop.hide();
                        mFabBottom.hide();
//                        CameraTabFragment cameraTabFragment = new CameraTabFragment();
//                        cameraTabFragment.startCamera();
                        break;
                    case 1:
                        mFabBottom.hide();
                        mFabBottom.setImageResource(R.drawable.ic_comment_white_24dp);
                        mFabTop.hide();
                        mFabBottom.show();

                        mFabBottom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                startActivity(new Intent(MainActivity.this, ContactActivity.class));
                            }
                        });
                        break;
                    case 2:
                        mFabTop.hide();
                        mFabBottom.setImageResource(R.drawable.ic_camera_alt_white_24dp);
                        mFabTop.show();
                        mFabBottom.hide();
                        mFabBottom.show();
                        mFabTop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this, StatusTextActivity.class));
                            }
                        });
                        mFabBottom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/jpeg");
                                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                            }
                        });
                        break;
                    default:
                        mFabTop.hide();
                        mFabBottom.hide();
                        mFabBottom.setImageResource(R.drawable.ic_phone_black_white_24dp);
                        mFabBottom.show();
                        mFabBottom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "view 3", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                }

                if (position == 0) {
                    mToolbar.setVisibility(View.GONE);
                    mAppBarLayout.setExpanded(false, true);
                } else {
                    mToolbar.setVisibility(View.VISIBLE);
                    mAppBarLayout.setExpanded(true, true);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem=menu.findItem(R.id.search);
//        SearchView searchView=(SearchView) menuItem.getActionView();
        cameraItem = menu.findItem(R.id.camera);
//        searchView.setQueryHint("Search file name");
//
//        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String input=newText.toLowerCase();
        if (input.equals("") | (input==null)){

        }else {
            if (cameraItem != null) {
                cameraItem.setVisible(false);
            }
        }
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(context, "Search Click ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.camera:
                Toast.makeText(context, "Camera Click ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void AllIniClizeID() {
        sharedPrefHelper=new SharedPrefHelper(MainActivity.this);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.viewpager);
        mFabBottom = findViewById(R.id.fab_bottom);
        mFabTop = findViewById(R.id.fab_top);
        mToolbar = findViewById(R.id.toolbar);
        mAppBarLayout = findViewById(R.id.appbar_layout);
    }


}