package edu.umkc.mobile.calanderapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView simpleCalendarView = (CalendarView) findViewById(R.id.calander); // get the reference of CalendarView

        simpleCalendarView.setDate(Calendar.getInstance().getTimeInMillis(),false,true);

        final TextView text = (TextView)findViewById(R.id.text2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        text.setText(simpleDateFormat.format(date));

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub

                text.setText(dayOfMonth +" / " + (month+1) + " / " + year);

            }
        });
    }
    }
