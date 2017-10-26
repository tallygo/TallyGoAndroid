package com.tallygo.tallygoimplementation_basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tallygo.tallygoandroid.activities.TallyGoActivity;

/**
 * This activity shows the most basic way to implement TallyGo's SDK. All we need to do is launch
 * the TallyGoActivity and boom! Your user has a fully functioning Map, Search and Turn-by-Turn
 * navigation application. Of course, this will not be useful if you would like to specify
 * map functionality or provide a route of your own... We have other reference implementations for
 * that!
 */

public class MainActivity extends AppCompatActivity {

    /**
     * Here we create a view and a button to start TallyGo
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startTallyGoButton = findViewById(R.id.b_start_tallygo);
        startTallyGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTallyGo();
            }
        });

    }

    /**
     * And presto! You have navigation!
     *
     * Note that this kills the current activity, and brings the user into the fully pre-built
     * TallyGo search and navigation system. While you could build some logic like this, it is
     * advised that you check out our other implementations for further customization.
     */
    private void startTallyGo() {
        Intent intent = new Intent(this, TallyGoActivity.class);
        startActivity(intent);
        finish();
    }
}
