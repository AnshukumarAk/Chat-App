package com.anshu.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.anshu.chatapp.Adepter.UserAdepter;
import com.anshu.chatapp.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Context context = this;
    // Declare variables
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    ArrayList<String> list;
    private String[] labels = new String[]{"CHATS", "STATUS", "CALLS"};
    ViewPagerFragmentAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list=new ArrayList<>();
        adapter = new ViewPagerFragmentAdapter(this);
        binding.viewPager2.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, (tab, position) -> {
            tab.setText(labels[position]);
        }).attach();
        binding.viewPager2.setCurrentItem(0, false);
    }
    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        // return fragments at every position
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new fragment_chats(); // calls fragment
                case 1:
                    return new fragment_status(); // chats fragment
                case 2:
                    return new fragment_calls(); // status fragment
            }
            return new fragment_chats(); //chats fragment
        }

        @Override
        public int getItemCount() {
            return labels.length;
        }
    }
}
