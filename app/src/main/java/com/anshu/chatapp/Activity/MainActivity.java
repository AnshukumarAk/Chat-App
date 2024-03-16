package com.anshu.chatapp.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.anshu.chatapp.Adepter.TabAdapter;
import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivityMainBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

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
    private static final int CAMERA_REQUEST = 1000;
    String base64 = "";
    private static final int SELECT_PICTURE = 100;
    String data = "";
    String CheckAction = "";
    int position;

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
                                showImagePickerDialog();
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
        getMenuInflater().inflate(R.menu.menu_items, menu);
        cameraItem = menu.findItem(R.id.camera);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_search) {
            Toast.makeText(context, "Search Click ", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.camera) {
            Toast.makeText(context, "Camera Click ", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.action_groupchat) {
            Intent groupChat = new Intent(MainActivity.this, GroupChatActivity.class);
            startActivity(groupChat);
        } else if (itemId == R.id.action_help) {
            // Handle help action
        } else if (itemId == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent logout=new Intent(MainActivity.this,SignInActivity.class);
            startActivity(logout);
            finish();
        } else if (itemId==R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            
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
                        assert photo != null;
                        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        base64 = encodeTobase64(photo);
//                        binding.imgAvatarCreate.setImageBitmap(photo);
//                        binding.fabBottom.setImageBitmap(photo);
                        if (base64.equals("")){

                        }else {
//                            sharedPrefHelper.setString("Image", base64);
                            Intent intent=new Intent(MainActivity.this, EditStatusActivity.class);
                            intent.putExtra("Image",base64);
                            startActivity(intent);
                        }
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
//                            binding.imgAvatarCreate.setImageURI(selectedImageUri);
//                            binding.fabBottom.setImageBitmap(selectedImage);
                            if (base64.equals("")){

                            }else {
//                                sharedPrefHelper.setString("Image",base64);
                                Intent intent=new Intent(MainActivity.this, EditStatusActivity.class);
                                intent.putExtra("Image",base64);
                                startActivity(intent);
                            }
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

//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onBackPressed() {
//        ShowExitPopUp(position);
//    }

    private void ShowExitPopUp(int position) {
        // creating a variable for our bottom sheet dialog.
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);

        // inflating the layout for the bottom sheet dialog.
        View layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.exitpopup, null);

        // initializing our text views and image views.
        Button BtnYes = layout.findViewById(R.id.BtnYes);
        Button BtnCancel = layout.findViewById(R.id.BtnCancel);

        BtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.super.onBackPressed();
            }
        });

        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              bottomSheetDialog.dismiss();
            }
        });

        // setting up our bottom sheet dialog with content view and cancellation properties.
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
            if (position==1){
            // displaying the bottom sheet dialog.
            bottomSheetDialog.show();

        }
    }

}