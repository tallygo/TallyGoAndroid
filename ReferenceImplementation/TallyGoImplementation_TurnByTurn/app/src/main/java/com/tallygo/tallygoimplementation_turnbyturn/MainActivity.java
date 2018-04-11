package com.tallygo.tallygoimplementation_turnbyturn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.tallygo.tallygoandroid.activities.TGTurnByTurnActivity;
import com.tallygo.tallygoandroid.sdk.TGMutableConfiguration;
import com.tallygo.tallygoandroid.sdk.TallyGo;
import com.tallygo.tallygoandroid.sdk.navigation.TGRouteRequest;
import com.tallygo.tallygoandroid.utils.TGBundleKeys;

import java.util.ArrayList;
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
        enableSimulatedDrive();

        LatLng currentLocation = new LatLng(32.6809d, -117.1784d); //Hotel Del Coronado
        LatLng destinationCoordinate = new LatLng(32.7306181d,-117.1462286d); //SD Zoo
        List<LatLng> waypoints = new ArrayList<>();
        waypoints.add(currentLocation);
        waypoints.add(destinationCoordinate);

        //create the request with the date/time supplied as the departure time
        TGRouteRequest routeRequest = new TGRouteRequest.Builder(waypoints).build();

        Intent intent = new Intent(this, TGTurnByTurnActivity.class);
        intent.putExtra(TGBundleKeys.ROUTE_REQUEST, routeRequest);
        startActivity(intent);
        finish();
    }

    private void enableSimulatedDrive() {
        TGMutableConfiguration configuration = TallyGo.getInstance(this).getMutableConfiguration();

        //simulate location
        LatLng simulatedLocation = new LatLng(32.6809d, -117.1784d); //Hotel Del Coronado
        configuration.setSimulatedLocation(simulatedLocation);

        //simulate the drive
        configuration.getSimulateNavigationSettings().setSimulated(true);

        TallyGo.getInstance(this).updateConfiguration(configuration);
    }
}
