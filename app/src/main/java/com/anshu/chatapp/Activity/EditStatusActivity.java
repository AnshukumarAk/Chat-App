package com.anshu.chatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivityEditStatusBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class EditStatusActivity extends AppCompatActivity {


    ActivityEditStatusBinding binding;
    private SharedPrefHelper sharedPrefHelper;
    String imageUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AllIniCilizeID();
         imageUrl = getIntent().getStringExtra("Image");
        SetImage();


    }



    private void AllIniCilizeID() {
         sharedPrefHelper=new SharedPrefHelper(EditStatusActivity.this);

    }
    private void SetImage() {
        if (imageUrl != null && imageUrl.length() > 200) {
            byte[] decodedString = Base64.decode(imageUrl, Base64.NO_WRAP);
            InputStream inputStream = new ByteArrayInputStream(decodedString);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            binding.previewImage.setImageBitmap(bitmap);
            // Get the current layout params
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                    binding.previewImage.getLayoutParams();

            // Set the gravity to center
            layoutParams.gravity = Gravity.CENTER;

            // Set the layout params back to the view
            binding.previewImage.setLayoutParams(layoutParams);
        } else {
            try {
                Glide.with(EditStatusActivity.this)
                        .load(imageUrl)
                        .apply(new RequestOptions().centerCrop())
                        .into(binding.previewImage);
            } catch (Exception e) {
                Log.d("Exception", "" + e);
            }
        }
    }
}