package com.anshu.chatapp.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivitySplashBinding;
import com.anshu.chatapp.services.Config;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    private static final int REQUEST = 112;
    private SharedPrefHelper sharedPrefHelper;
    private BroadcastReceiver mRegistrationBroadcastReceiver;



    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());


        sharedPrefHelper=new SharedPrefHelper(this);
        SetValues();
        SetSplashAnimetion();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        @SuppressLint({"StringFormatInvalid", "LocalSuppress"})
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
//                        Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);


                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //  txtMessage.setText(message);
                }
            }
        };

        if (Build.VERSION.SDK_INT <= 32) {
            Log.d("TAG","@@@ IN IF Build.VERSION.SDK_INT >= 23");
            String[] PERMISSIONS = {
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.POST_NOTIFICATIONS,

             };

            if (!hasPermissions(this, PERMISSIONS)) {
                Log.d("TAG","@@@ IN IF hasPermissions");
                ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, REQUEST);
            } else {
                Log.d("TAG","@@@ IN ELSE hasPermissions");
                CallNextActivity();

            }
        }
        else {
            String[] PERMISSIONS = {
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.POST_NOTIFICATIONS,
            };

            if (!hasPermissions(this, PERMISSIONS)) {
                Log.d("TAG","@@@ IN IF hasPermissions");
                ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, REQUEST);
            } else {
                Log.d("TAG","@@@ IN ELSE hasPermissions");
                CallNextActivity();

            }
            Log.d("TAG","@@@ IN ELSE  Build.VERSION.SDK_INT >= 23");
//            callNextActivity();
        }




    }

    private void SetValues() {
        PackageInfo pInfo = null;
        try {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version1 = pInfo.versionName;
            binding.llVersion.tvVersion.setText("App Version:"+" "+version1);
            sharedPrefHelper.setString("AppVersion",version1);

        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void SetSplashAnimetion() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.side_slide);
        binding.image.startAnimation(animation);

    }


    private void CallNextActivity() {
        int SPLASH_DISPLAY_LENGHT = 5000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPrefHelper.getString("login","").equals("yes")){
                        Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                         Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                }
            }
        }, SPLASH_DISPLAY_LENGHT);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CallNextActivity();
                }else {
                    Toast.makeText(this, "PERMISSIONS Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}