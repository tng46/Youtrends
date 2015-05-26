package com.example.santo.youtrends2.app.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.santo.youtrends2.app.R;
import com.example.santo.youtrends2.app.fragments.VideoPlayerFragment;


public class VideoPlayerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("YouTrends");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView dataText = (TextView)findViewById(R.id.youtubeData);
        dataText.setText("Pubblicato il " + getIntent().getStringExtra("youtubeDate"));

        TextView titleText = (TextView)findViewById(R.id.youtubeTitle);
        titleText.setText(getIntent().getStringExtra("youtubeTitle"));






        VideoPlayerFragment f = VideoPlayerFragment.newInstance(getIntent().getStringExtra("youtubeId"));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();

        f.setRetainInstance(true);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
