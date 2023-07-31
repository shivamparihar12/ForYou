package com.example.foryou;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class FaceEmotionAttributesRecognition {
    private Interpreter interpreter;
    private int inputSize;
    private int height = 0, width = 0;
    private GpuDelegate gpuDelegate = null;
    private String TAG = "FaceEmotionAttributesRecognition";

    //cascadeClassifier
    private CascadeClassifier cascadeClassifier;

    public FaceEmotionAttributesRecognition(AssetManager assetManager, Context context, String modelPath, int inputSize) throws IOException {
        this.inputSize = inputSize;
        Interpreter.Options options = new Interpreter.Options();
        gpuDelegate = new GpuDelegate();
        options.addDelegate(gpuDelegate);
        options.setNumThreads(4);//set this according to your phone
        interpreter = new Interpreter(loadModelFile(assetManager, modelPath), options);
        Log.d(TAG, "tensorflow model is loaded");

        //loading haarcascade classifier
        try {
            InputStream is = context.getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
            //create a folder
            File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);
            //create a new file in the above declared folder
            File cascadeFile = new File(cascadeDir, "haarcascade_frontalface_alt");
            // define o/p stream to transfer data to file we created
            FileOutputStream os = new FileOutputStream(cascadeFile);
            // buffer to store byte
            byte[] buffer = new byte[4096];
            int byteRead;
            // read byte in while loop
            while ((byteRead = is.read(buffer)) != -1) {
                //writing on cascade file
                os.write(buffer, 0, byteRead);
            }
            is.close();
            os.close();
            cascadeClassifier = new CascadeClassifier(cascadeFile.getAbsolutePath());
            Log.d(TAG, "Classifier is loaded");

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList recognizeImage(Mat matImage) {
        ArrayList<String> array=new ArrayList<>();
        Core.flip(matImage.t(), matImage, 1);
        Mat grayScaleImage = new Mat();
        Imgproc.cvtColor(matImage, grayScaleImage, Imgproc.COLOR_RGBA2GRAY);
//        Imgproc.
        height = grayScaleImage.height();
        width = grayScaleImage.width();

        //define min height of face in orginal image
        //below this size no face in original image will show
        int absolutFaceSize = (int) (height * 0.1);
        //now creating matOfRect to store face
        MatOfRect faces = new MatOfRect();
        //check if cascadeClassifier is loaded or not
        if (cascadeClassifier != null) {
            //detect face in frame
            cascadeClassifier.detectMultiScale(grayScaleImage, faces, 1.1, 2, 2,
                    new Size(absolutFaceSize, absolutFaceSize), new Size());

        }
        Rect[] faceArray = faces.toArray();
        for (int i = 0; i < faceArray.length; i++) {
            Rect roi = new Rect((int) faceArray[i].tl().x, (int) faceArray[i].tl().y,
                    ((int) faceArray[i].br().x) - (int) (faceArray[i].tl().x),
                    ((int) faceArray[i].br().y) - (int) (faceArray[i].tl().y));
            Mat cropped_rgba = new Mat(matImage, roi);
            Bitmap bitmap = null;
            bitmap = Bitmap.createBitmap(cropped_rgba.cols(), cropped_rgba.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(cropped_rgba, bitmap);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 48, 48, false);
            ByteBuffer byteBuffer = convertBitmapToByteBuffer(scaledBitmap);

            float[][] emotion = new float[1][1];
            interpreter.run(byteBuffer, emotion);
            float emotionFloatVal=(float)Array.get(Array.get(emotion, 0), 0);
            String emoLabel=getEmotionLabel(emotionFloatVal);
            array.add(emoLabel);
            Log.d(TAG, "output: " + emotionFloatVal+" "+emoLabel);
        }

        Core.flip(matImage.t(), matImage, 0);
        return array;
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap scaledBitmap) {
        ByteBuffer byteBuffer;
        int size = inputSize;
        byteBuffer = ByteBuffer.allocateDirect(4 * 1 * size * size * 3);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[size * size];
        scaledBitmap.getPixels(intValues, 0, scaledBitmap.getWidth(), 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int val = intValues[pixel++];
                byteBuffer.putFloat((((val >> 16) & 0xFF)) / 255.0f);
                byteBuffer.putFloat((((val >> 8) & 0xFF)) / 255.0f);
                byteBuffer.putFloat(((val & 0xFF)) / 255.0f);
            }
        }
        return byteBuffer;

    }

    //wwe will use this to load model
    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(modelPath);
        //creating i/o stream to read file
        FileInputStream inputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();

        long startOffset = assetFileDescriptor.getStartOffset();
        long declaredLength = assetFileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private String getEmotionLabel(float emotion_v) {
        String val = "";
        if (emotion_v >= 0 & emotion_v < 0.5) {
            val = "Surprise";
        } else if (emotion_v >= 0.5 & emotion_v < 1.5) {
            val = "Fear";
        } else if (emotion_v >= 1.5 & emotion_v < 2.5) {
            val = "Angry";
        } else if (emotion_v >= 2.5 & emotion_v < 3.5) {
            val = "Neutral";
        } else if (emotion_v >= 3.5 & emotion_v < 4.5) {
            val = "Sad";
        } else if (emotion_v >= 4.5 & emotion_v < 5.5) {
            val = "Disgust";
        } else {
            val = "Happy";
        }
        return val;
    }
}
