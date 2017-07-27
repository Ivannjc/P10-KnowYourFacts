package sg.edu.rp.c346.p10_knowyourfacts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

import layout.Frag1;
import layout.Frag2;
import layout.Frag3;

public class MainActivity extends AppCompatActivity {

    ArrayList<Fragment> al;
    MyFragmentPagerAdapter adapter;
    ViewPager vPager;
    Button btnRead;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vPager = (ViewPager) findViewById(R.id.viewpager1);
        btnRead = (Button) findViewById(R.id.btnRead);

        FragmentManager fm = getSupportFragmentManager();


        al = new ArrayList<Fragment>();
        al.add(new Frag1());
        al.add(new Frag2());
        al.add(new Frag3());

        adapter = new MyFragmentPagerAdapter(fm, al);


        vPager.setAdapter(adapter);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
                Intent i = new Intent(MainActivity.this, ScheduleNotificationReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this,12345,i,PendingIntent.FLAG_CANCEL_CURRENT);
                Calendar now = Calendar.getInstance();
                now.add(Calendar.SECOND, 5*60);
                alarm.set(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(),
                        pi);

                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_next) {
            int max = vPager.getChildCount();
            if (vPager.getCurrentItem() < max - 1) {
                int nextPage = vPager.getCurrentItem() + 1;
                vPager.setCurrentItem(nextPage, true);

            }
        }
        else if(item.getItemId() == R.id.action_random){
            Random random = new Random();
            int randomPage = random.nextInt(al.size());
            vPager.setCurrentItem(randomPage, true);

        } else{
            if (vPager.getCurrentItem() > 0) {
                int previousPage = vPager.getCurrentItem() - 1;
                vPager.setCurrentItem(previousPage, true);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("saved", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("content", vPager.getCurrentItem());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("saved", MODE_PRIVATE);
        int content = preferences.getInt("content", 1);
        vPager.setCurrentItem(content, true);
    }
}
