package com.tallygo.tallygoimplementation_turnbyturn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.tallygo.tallygoandroid.activities.turnbyturn.TGTurnByTurnActivity;
import com.tallygo.tallygoandroid.fragments.navigation.base.TGBaseTurnByTurnFragment;
import com.tallygo.tallygoandroid.sdk.navigation.TGRouteRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startTallyGoButton = findViewById(R.id.b_start_tallygo);
        startTallyGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTurnByTurn();
            }
        });
    }

    private void startTurnByTurn() {
        //you can use this line to get your current location if you would like
        //LatLng currentLocation = TGUtils.getRealCurrentCoordinate(getBaseContext());

        LatLng currentLocation = new LatLng(32.6809d, -117.1784d); //Hotel Del Coronado
        LatLng destinationCoordinate = new LatLng(32.7306181d,-117.1462286d); //SD Zoo

        List<LatLng> waypoints = new ArrayList<>();
        waypoints.add(currentLocation);
        waypoints.add(destinationCoordinate);

        //current date
        Date date = new Date();

        //create the request with the date/time supplied as the departure time
        TGRouteRequest routeRequest = new TGRouteRequest(waypoints, date,
                TGRouteRequest.TGRouteRequestType.DEPARTURE_TIME);

        //create options to pass to the turn-by-turn activity
        TGBaseTurnByTurnFragment.Options options = new TGBaseTurnByTurnFragment.Options();
        options.putRouteRequest(routeRequest);

        //we simulate the route, so you don't have to drive to test it
        options.putSimulated(true);

        //also try this if you would like to control how the simulation moves
        //options.putClickToUpdate(true);

        Intent intent = new Intent(this, TGTurnByTurnActivity.class);
        intent.putExtra(TGBaseTurnByTurnFragment.Options.TG_BASE_TURN_BY_TURN_OPTIONS_KEY, options);
        startActivity(intent);
    }
}
