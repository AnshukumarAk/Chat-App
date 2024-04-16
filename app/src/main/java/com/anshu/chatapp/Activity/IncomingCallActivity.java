package com.anshu.chatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityIncomingCallBinding;

public class IncomingCallActivity extends AppCompatActivity {

    ActivityIncomingCallBinding binding;
    int startOff = 0;
    int diff = 100;
    int duration = 600;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIncomingCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Animation right1FadeOut = setAnimFadeOut(startOff,duration);
        final Animation right1FadeIn  = setAnimFadeIn(0,duration);
        final Animation right2FadeOut = setAnimFadeOut(startOff+diff,duration+diff);
        final Animation right2FadeIn  = setAnimFadeIn(0,duration);
        final Animation right3FadeOut = setAnimFadeOut(startOff+diff*2,duration+diff*2);
        final Animation right3FadeIn  = setAnimFadeIn(0,duration);


        binding.imageacceptinvitation.setAnimation(right1FadeIn);


    }

    private Animation setAnimFadeOut(int startOff, int duration){
        Animation animFadeOut;
        animFadeOut = new AlphaAnimation(1, 0);
        animFadeOut.setInterpolator(new AccelerateInterpolator());
        animFadeOut.setStartOffset(startOff);
        animFadeOut.setDuration(duration);
        return  animFadeOut;
    }

    private Animation setAnimFadeIn(int startOff,int duration){
        Animation animFadeIn;
        animFadeIn = new AlphaAnimation(0, 1);
        animFadeIn.setInterpolator(new AccelerateInterpolator());
        animFadeIn.setStartOffset(startOff);
        animFadeIn.setDuration(duration);
        return  animFadeIn;
    }
}