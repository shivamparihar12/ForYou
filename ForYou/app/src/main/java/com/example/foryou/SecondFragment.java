package com.example.foryou;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foryou.databinding.FragmentSecondBinding;
import com.example.foryou.retrofit.retrofitmodel.UserMeetData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private static final String TAG = "SecondFragment";
    private Map<String, Integer> map;
    private ArrayList<String> expressionAttribute;
    UserMeetData userMeetData;
    private String label[] = new String[]{"Surprise", "Fear", "Angry", "Neutral", "Sad", "Disgust", "Happy"};

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userMeetData = SecondFragmentArgs.fromBundle(getArguments()).getUserMeetData();
        map = new HashMap<>();

        //Data visualization
        createPieChart();
        createLineChart();

    }

    public void createLineChart() {
        ArrayList<Entry> arrayList = new ArrayList<>();
        for (int i = 0; i < userMeetData.getEmoList().size(); i++) {
            arrayList.add(new Entry(i, (float) approxEmoVal(userMeetData.getEmoList().get(i))));
        }
        LineDataSet lineDataSet = new LineDataSet(arrayList, "emotions over time");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add((ILineDataSet) lineDataSet);
        LineData data = new LineData(dataSets);
        binding.lineChart.setData(data);
        binding.lineChart.invalidate();

    }

    public float approxEmoVal(String attribute) {
        float val;
        switch (attribute) {
            case "Surprise":
                val = 0.25F;
                break;
            case "Fear":
                val = 1;
                break;
            case "Angry":
                val = 2;
                break;
            case "Neutral":
                val = 3;
                break;
            case "Sad":
                val = 4;
                break;
            case "Disgust":
                val = 5;
                break;
            default:
                val = 6;
        }
        return val;
    }

    public void createPieChart() {
        for (int i = 0; i < label.length; i++) {
            map.put(label[i], 1);
        }

        Log.d(TAG, userMeetData.getDate());
        Log.d(TAG, userMeetData.getEmoList().get(0));
        for (int i = 0; i < userMeetData.getEmoList().size(); i++) {
            map.put(userMeetData.getEmoList().get(i), (map.get(userMeetData.getEmoList().get(i)) + 1));
        }

        ArrayList<PieEntry> pieEntryArrayList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            pieEntryArrayList.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntryArrayList, "Emotion Attributes");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(13f);
        PieData pieData = new PieData(pieDataSet);

        binding.pieChart.setData(pieData);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.setCenterText("Emotions");
        binding.pieChart.animate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}