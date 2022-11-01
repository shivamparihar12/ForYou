package com.example.foryou;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foryou.databinding.FragmentFirstBinding;
import com.example.foryou.utility.ConvertImageToBitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.IOException;
import java.util.ArrayList;

public class FirstFragment extends Fragment {


    private FragmentFirstBinding binding;
    private static final String TAG = "MainActivity";
    private int mScreenDensity;
    private static final int REQUEST_CODE = 1010;
//    private static final int REQUEST_PERMISSION = 10001;
    private static final int displayHeight = 1280, displayWidth = 720;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private FaceEmotionAttributesRecognition faceEmotionAttributesRecognition;
    private MediaProjection.Callback mMediaProjectionCallback;
    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;
    private MediaRecorder mediaRecorder;
    private ImageReader imageReader;
    private VirtualDisplay virtualDisplay;
    private final Object mStateLock = new Object();
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private String videoUri = "";
    private ArrayList<Float> arrayOfEmotionAttribute;

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        imageReader = ImageReader.newInstance(displayWidth, displayHeight, PixelFormat.RGBA_8888, 1);
        //to  load cascade classifier and model that will be called one time
        try {
            int inputSize = 48;
            faceEmotionAttributesRecognition = new FaceEmotionAttributesRecognition(getActivity().getAssets(), getActivity(), "model.tflite", inputSize);
        } catch (IOException e) {
            Log.d(TAG, "face nono" + e.getStackTrace() + " \n" + e.getMessage());
            e.printStackTrace();
        }
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;
        mediaRecorder = new MediaRecorder();
        arrayOfEmotionAttribute=new ArrayList<>();

        mediaProjectionManager = (MediaProjectionManager) getActivity().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        binding.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onToggleScreenShare(view);
            }
        });

    }


    private void onToggleScreenShare(View view) {
        if (((ToggleButton) view).isChecked()) {
//            imageReader=ImageReader.newInstance(displayWidth,displayHeight, ImageFormat.FLEX_RGBA_8888,5);
//            initRecorder();
            shareScreen();
        } else {
            if (mediaRecorder != null)
//                mediaRecorder.stop();
//            mediaRecorder.reset();
                Log.v(TAG, "Stopiing Recording");
            stopScreenSharing();
        }
    }

    private void shareScreen() {
        if (mediaProjection == null) {
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);
            return;
        }
        virtualDisplay = createVirtualDisplay();
//        mediaRecorder.start();
    }


    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != REQUEST_CODE) {
            Log.e(TAG, "Unknown request code: " + requestCode);
            return;
        }
        if (resultCode != RESULT_OK) {
            Toast.makeText(getActivity(), "Screen casting permission denied", Toast.LENGTH_SHORT).show();
            binding.toggleButton.setChecked(false);
            return;
        }


//        imageReader= ImageReader.newInstance(displayWidth,displayHeight, PixelFormat.RGBA_8888,10);
        mMediaProjectionCallback = new MediaProjection.Callback() {
            @Override
            public void onStop() {
                if (binding.toggleButton.isChecked()) {
                    binding.toggleButton.setChecked(false);
//                    mediaRecorder.stop();
//                    mediaRecorder.reset();
                    Log.v(TAG, "Recording Stopped");
                }
                mediaProjection = null;
                stopScreenSharing();
            }
        };
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
        mediaProjection.registerCallback(mMediaProjectionCallback, null);
        virtualDisplay = createVirtualDisplay();

        imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader imageReader) {
                Image image = null;
                image = imageReader.acquireNextImage();
                if (image != null) {
                    Mat mRgba;
                    mRgba = new Mat();
                    ConvertImageToBitmap convertImageToBitmap = new ConvertImageToBitmap();
                    Bitmap bitmap = convertImageToBitmap.toBitmap(image);
                    Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Utils.bitmapToMat(bmp32, mRgba);
                    Log.d(TAG + " mat mRgba", String.valueOf(bmp32.getWidth()));
                    arrayOfEmotionAttribute.addAll(faceEmotionAttributesRecognition.recognizeImage(mRgba));
                    //todo
                }
                image.close();
                Log.i(TAG, "in OnImageAvailable");
                Log.d(TAG, imageReader.getImageFormat() + " " + imageReader.getMaxImages() + " " + imageReader.getImageFormat());
            }
        }, mBackgroundHandler);
    }

    private void stopScreenSharing() {
        if (virtualDisplay == null)
            return;
        virtualDisplay.release();
        destroyMediaProjection();
    }

    private void destroyMediaProjection() {
        if (mediaProjection != null) {
            mediaProjection.unregisterCallback(mMediaProjectionCallback);
            mediaProjection.stop();
            mediaProjection = null;
        }
        Log.i(TAG, "mediaProjection Stopped");
    }

    private VirtualDisplay createVirtualDisplay() {
        return mediaProjection.createVirtualDisplay(TAG,
                displayWidth, displayHeight, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.getSurface(),
                new VirtualDisplay.Callback() {
                    @Override
                    public void onPaused() {
                        Log.i(TAG, "onPaused");
                        super.onPaused();
                    }

                    @Override
                    public void onResumed() {
                        Log.i(TAG, "onResumed");
                        super.onResumed();
                    }
                }, null/*handler*/);
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("ImageReaderBackground");
        mBackgroundThread.start();
        synchronized (mStateLock) {
            mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
        }
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            synchronized (mStateLock) {
                mBackgroundHandler = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();
    }

    @Override
    public void onPause() {
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}