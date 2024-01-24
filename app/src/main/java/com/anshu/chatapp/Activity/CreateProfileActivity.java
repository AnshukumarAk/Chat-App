package com.anshu.chatapp.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivityCreateProfileBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class CreateProfileActivity extends AppCompatActivity {

   ActivityCreateProfileBinding binding;
   Context context=this;
   String CheckAction="";
    private static final int CAMERA_REQUEST = 1000;
    String data = "";
    private static final int SELECT_PICTURE = 100;
    String base64="";
    SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefHelper=new SharedPrefHelper(this);
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckCondition()){
                    String UserName=binding.etCreateProfileUsername.getText().toString().trim();
                    sharedPrefHelper.setString("UserName",UserName);
                    sharedPrefHelper.setString("UserPhoto",base64);
                    Intent intent=new Intent(CreateProfileActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        binding.imgAvatarCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImagePickerDialog();
            }
        });

    }

    private boolean CheckCondition() {
       if (binding.etCreateProfileUsername.getText().toString().trim().equals("")){
           binding.etCreateProfileUsername.setError("Field Required");
           binding.etCreateProfileUsername.requestFocus();
           return false;
       }


        return true;
    }

    public void showImagePickerDialog() {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.choose_option, null);
        ImageView camera = (ImageView) popupView.findViewById(R.id.camera);
        ImageView gallery = (ImageView) popupView.findViewById(R.id.gallery);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Choose Image");
        alertDialog.setView(popupView);
        final AlertDialog dialog = alertDialog.show();
        alertDialog.setCancelable(true);


        //Camera Click
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAction="PhotoShoot";
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt, CAMERA_REQUEST);
                if(data!=null){
                    dialog.dismiss();
                }
            }
        });

        //Gallery Click
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAction="ChooseGallery";
                try{
                    openImagesDocument();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG,"ChooseGallery"+e);
                }
                if(data!=null){
                    dialog.dismiss();
                }
            }
        });

    }
    private void openImagesDocument() {
        Intent pictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pictureIntent.setType("image/*");
        pictureIntent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String[] mimeTypes = new String[]{"image/jpeg", "image/png"};
            pictureIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }
        startActivityForResult(Intent.createChooser(pictureIntent, "Select Picture"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            if (CheckAction.equals("PhotoShoot")) {
                if (requestCode == CAMERA_REQUEST) {
                    if (resultCode != RESULT_CANCELED) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        base64 = encodeTobase64(photo);
                        binding.imgAvatarCreate.setImageBitmap(photo);
                    }
                }
            }
            if (CheckAction.equals("ChooseGallery")) {
                if (resultCode == RESULT_OK) {
                    if (requestCode == SELECT_PICTURE) {
                        // Get the url of the image from data
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(selectedImageUri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            base64 = encodeTobase64(selectedImage);
                            // update the preview image in the layout
                            binding.imgAvatarCreate.setImageURI(selectedImageUri);
                        }
                    }
                }
            }

        }
    }
    private String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream byteArrayOS = null;
        try {
            System.gc();
            byteArrayOS = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOS);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOS);
        }
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
    }


}