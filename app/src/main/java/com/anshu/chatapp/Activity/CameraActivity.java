package com.anshu.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraX;
import androidx.camera.core.FlashMode;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.anshu.chatapp.Adepter.GalleryAdapter;
import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityCameraBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {

    ActivityCameraBinding binding;

    private final String[] REQUIRED_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_PERMISSIONS = 10;

    private int flashType = 1;

    private RecyclerView mRecyclerView;
    private TextureView mTextureView;
    private ImageView mFlash;
    private ImageView mCapture;
    private ImageView mSwitch;

    private CameraX.LensFacing mLensFacing = CameraX.LensFacing.BACK;
    private ImageCapture mImageCapture = null;
    private FlashMode flashMode = FlashMode.AUTO;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        AllIniClizeID();

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(CameraActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        } else {
            startCamera();
        }

        mRecyclerView.hasFixedSize();
        mRecyclerView.setLayoutManager(new GridLayoutManager(CameraActivity.this, 1, RecyclerView.HORIZONTAL, false));

        ArrayList<String> images = new ArrayList<>();

        if (images.isEmpty()) {
            images = getAllImages();
            GalleryAdapter adapter = new GalleryAdapter(CameraActivity.this, images);
            mRecyclerView.setAdapter(adapter);
        }

        mFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashToggle();
                Log.i("MYTAG", "flash no : " + flashType);
                setFlashIcon();
            }
        });

        
    }

    private void AllIniClizeID() {
        mRecyclerView =findViewById(R.id.rv_image_galery);
        mTextureView = findViewById(R.id.textureview);
        mFlash = findViewById(R.id.img_flash);
        mCapture = findViewById(R.id.img_capture);
        mSwitch = findViewById(R.id.img_switch);
        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                Toast.makeText(CameraActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CameraActivity.this, "Permissions not granted by the user. " + allPermissionsGranted(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private boolean allPermissionsGranted() {
        if (Build.VERSION.SDK_INT <= 32) {
            for (String permissions : REQUIRED_PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(CameraActivity.this, permissions) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void startCamera() {
        bindCamera();

        mSwitch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                if (mLensFacing == CameraX.LensFacing.FRONT) {
                    mLensFacing = CameraX.LensFacing.BACK;
                } else {
                    mLensFacing = CameraX.LensFacing.FRONT;
                }
                try {
                    CameraX.getCameraWithLensFacing(mLensFacing);
                    CameraX.unbindAll();
                    bindCamera();
                } catch (CameraInfoUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });

        mCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
                @SuppressLint("RestrictedApi") File file = new File(CameraActivity.this.getExternalCacheDir() + File.separator + System.currentTimeMillis() + ".png");
                mImageCapture.setFlashMode(flashMode);
                mImageCapture.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        Intent intent = new Intent(CameraActivity.this, EditStatusActivity.class);
                        intent.putExtra("from_activity", CameraActivity.this.getClass().getSimpleName());
                        intent.putExtra("Image", file.getAbsolutePath());
                        startActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        String msg = "Pic captured failed : " + message;
                        Toast.makeText(CameraActivity.this, msg, Toast.LENGTH_SHORT).show();
                        if (cause != null) {
                            cause.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void bindCamera() {
        CameraX.unbindAll();

        Rational aspectRatio = new Rational(mTextureView.getWidth(), mTextureView.getHeight());
        Size screenSize = new Size(mTextureView.getWidth(), mTextureView.getHeight());

        PreviewConfig previewConfig = new PreviewConfig.Builder()
                .setTargetAspectRatio(aspectRatio)
                .setTargetResolution(screenSize)
                .setLensFacing(mLensFacing)
                .build();

        Preview preview = new Preview(previewConfig);
        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) mTextureView.getParent();
                parent.removeView(mTextureView);
                parent.addView(mTextureView, 0);
                mTextureView.setSurfaceTexture(output.getSurfaceTexture());
            }
        });

        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder()
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(CameraActivity.this.getWindowManager().getDefaultDisplay().getRotation())
                .setLensFacing(mLensFacing)
                .build();

        mImageCapture = new ImageCapture(imageCaptureConfig);

        CameraX.bindToLifecycle(this, preview, mImageCapture);
    }

    private void setFlashIcon() {
        if (flashType == 1) {
            mFlash.setImageResource(R.drawable.ic_flash_auto_white_24dp);
            flashMode = FlashMode.AUTO;

        } else if (flashType == 2) {
            mFlash.setImageResource(R.drawable.ic_flash_on_white_24dp);
            flashMode = FlashMode.ON;

        } else if (flashType == 3) {
            mFlash.setImageResource(R.drawable.ic_flash_off_white_24dp);
            flashMode = FlashMode.OFF;

        }
    }

    private void flashToggle() {
        if (flashType == 1) {
            flashType = 2;
        } else if (flashType == 2) {
            flashType = 3;
        } else if (flashType == 3) {
            flashType = 1;
        }
    }

    private ArrayList<String> getAllImages() {
        ArrayList<String> images = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE
        };

        Cursor cursor = CameraActivity.this.getContentResolver().query(uri, projection, null, null, null);

        try {
            cursor.moveToFirst();
            do {
                images.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
            } while (cursor.moveToNext());
            cursor.close();
            ArrayList<String> reSelection = new ArrayList<>();
            for (int i = images.size() - 1; i > 0; i--) {
                reSelection.add(images.get(i));
            }
            images = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

}