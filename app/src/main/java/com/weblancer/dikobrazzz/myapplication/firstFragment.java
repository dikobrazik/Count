package com.weblancer.dikobrazzz.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class firstFragment extends Fragment {
    private Chronometer chronometer;
    private Chronometer chronometer2;
    private Button startButton;
    private Button stopButton;
    private boolean insertChronometer = false;
    private boolean stop;
    private Button del;
    private Button stopCount;
    private DatabaseAdapter adapter;
    private Calendar calStart;
    private Calendar calStop;
    private SimpleDateFormat sdf;
    private Fragment fr;



    public firstFragment() {

    }

    public static firstFragment newInstance(String param1, String param2) {
        firstFragment fragment = new firstFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        stop = true;
        chronometer = (Chronometer) view.findViewById(R.id.chronometer);
        chronometer2 = (Chronometer) view.findViewById(R.id.chronometer2);
        startButton = (Button) view.findViewById(R.id.btnStart);
        stopButton = (Button) view.findViewById(R.id.btnStop);
        del = (Button) view.findViewById(R.id.del);
        stopCount = (Button) view.findViewById(R.id.stopCount);
        adapter = new DatabaseAdapter(getContext());
        sdf = new SimpleDateFormat("HH:mm:ss");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stop){
                    if(insertChronometer){
                        adapter.insert(new Table(0, sdf.format(calStart.getTime()), sdf.format(calStop.getTime()), chronometer.getText().toString(),
                                chronometer2.getText().toString(), ""));
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {

                    }
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer2.setBase(SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                    chronometer.start();
                    chronometer2.start();
                    calStart = Calendar.getInstance();
                    stop = false;
                }
                insertChronometer = true;
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                calStop = Calendar.getInstance();
                stop = true;
                //insertChronometer = false;
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer2.setBase(SystemClock.elapsedRealtime());
                chronometer.stop();
                chronometer2.stop();
                insertChronometer = false;
                stop = true;

            }
        });
        stopCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                chronometer2.stop();
                calStart = Calendar.getInstance();
                if(calStop == null){
                    calStop = Calendar.getInstance();
                }
                adapter.insert(new Table(0,sdf.format(calStart.getTime()),sdf.format(calStop.getTime()), chronometer.getText().toString(),
                        chronometer2.getText().toString(), ""));
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer2.setBase(SystemClock.elapsedRealtime());
                new MainActivity().clideToSecondFragment();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.open();
        //Toast.makeText(getContext(), "startFirst", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.close();
        //Toast.makeText(getContext(), "stopFirst", Toast.LENGTH_SHORT).show();
    }

}
