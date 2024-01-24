package com.anshu.chatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivityPhoneNumberVerifiyBinding;

public class PhoneNumber_Verifiy extends AppCompatActivity {

    ActivityPhoneNumberVerifiyBinding binding;
    SharedPrefHelper sharedPrefHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding=ActivityPhoneNumberVerifiyBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());

         AllIniclizeID();
         String Mobile_number= sharedPrefHelper.getString("Number","");
         String Mobile_Code= sharedPrefHelper.getString("Code","");
         if (!Mobile_number.equals("") && !Mobile_Code.equals("")){
             binding.toolbarTitleInputNumbers.setText("Verify"+" "+"+"+Mobile_Code+" "+Mobile_number);
         }

         binding.btnNext.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent intent=new Intent(PhoneNumber_Verifiy.this,CreateProfileActivity.class);
                 startActivity(intent);


             }
         });


    }

    private void AllIniclizeID() {
        sharedPrefHelper=new SharedPrefHelper(this);
    }
}