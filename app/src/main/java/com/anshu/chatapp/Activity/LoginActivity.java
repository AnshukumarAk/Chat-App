package com.anshu.chatapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivityLoginBinding;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Context context=this;
    Dialog update_pic_layout;
    SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AllIniCilizeID();
        String Country_Code= binding.countryCodePicker.getSelectedCountryCode();
        binding.etCountryCode.setText(Country_Code);

        // Set listener for country code changes
        binding.countryCodePicker.setOnCountryChangeListener(() -> {
            String selectedCountryCode =  binding.countryCodePicker.getSelectedCountryCode();
            binding.etCountryCode.setText(selectedCountryCode);

        });

          binding.btnNext.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (CheckValidation()) {
                       String Number=binding.etPhoneNumber.getText().toString().trim();
                       String Code=binding.etCountryCode.getText().toString().trim();
                       String Both=Code+Number;
                      sharedPrefHelper.setString("Number",Number);
                      sharedPrefHelper.setString("Code",Code);
                      sharedPrefHelper.setString("FullNumber",Both);

                      ShowDialog();
                      Handler handler = new Handler(Looper.getMainLooper());
                      handler.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              update_pic_layout.dismiss();
                              ShowPopUp(Number,Code);
                          }
                      }, 3000);
                  }
              }
          });




    }



    private void AllIniCilizeID() {
        update_pic_layout = new Dialog(context);
        sharedPrefHelper=new SharedPrefHelper(this);
    }

    public void ShowDialog() {

        update_pic_layout.setContentView(R.layout.progress_dailoge);
        update_pic_layout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = update_pic_layout.getWindow().getAttributes();
        params.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;

        android.widget.ProgressBar progressBar = (ProgressBar) update_pic_layout.findViewById(R.id.spin_kit);

        update_pic_layout.show();
        update_pic_layout.setCanceledOnTouchOutside(false);
        update_pic_layout.setCancelable(false);
    }

    private void ShowPopUp(String number, String code) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.correct_number_popup, null);
        TextView Yes = (TextView) popupView.findViewById(R.id.yes);
        TextView Edit = (TextView) popupView.findViewById(R.id.edit);
        TextView Mobile_Number = (TextView) popupView.findViewById(R.id.tv_number);



        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//        alertDialog.setTitle("Choose Image");
        alertDialog.setView(popupView);
        final AlertDialog dialog = alertDialog.show();
        alertDialog.setCancelable(true);
        if (!number.equals("")){
            Mobile_Number.setText("+"+code+"  "+number);
        }

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,PhoneNumber_Verifiy.class);
                startActivity(intent);
                finish();
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dialog.dismiss();
            }
        });

    }

    private boolean CheckValidation() {

        if (binding.etPhoneNumber.getText().toString().trim().equals("")){
            Snackbar.make(findViewById(android.R.id.content), "Please Enter Phone Number", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }
}