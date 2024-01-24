package com.anshu.chatapp.Adepter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.anshu.chatapp.Fragments.CameraTabFragment;
import com.anshu.chatapp.Fragments.fragment_calls;
import com.anshu.chatapp.Fragments.fragment_chats;
import com.anshu.chatapp.Fragments.fragment_status;

public class TabAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public TabAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CameraTabFragment();
            case 1:
                return new fragment_chats();
            case 2:
                return new fragment_status();
            default:
                return new fragment_calls();

        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return null;
        } else if (position == 1) {
            return "Chats";
        } else if (position == 2) {
            return "Status";
        } else {
            return "Calls";
        }
    }
}