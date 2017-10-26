package com.tallygo.tallygoimplementation_navfragment;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.tallygo.tallygoandroid.activities.TGBaseActivity;
import com.tallygo.tallygoandroid.activities.navigation.TGNavigationFragment;
import com.tallygo.tallygoandroid.endpoint.navigation.TGNavigationEndpoint;
import com.tallygo.tallygoandroid.endpoint.navigation.TGRouteRequest;
import com.tallygo.tallygoandroid.endpoint.navigation.TGRouteResponse;
import com.tallygo.tallygoandroid.sdk.TallyGo;
import com.tallygo.tallygoandroid.utils.TGToastHelper;
import com.tallygo.tallygoandroid.utils.TGUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This activity shows how to implement the TGNavigationFragment, which can be used to provide
 * prebuilt turn-by-turn navigation from a TGRoute. In this fragment we enable location services,
 * initialize the TallyGo SDK and request a route that we supply to the fragment. Route requests
 * can be easily modified to fit your needs.
 */

public class MainActivity extends TGBaseActivity implements TGNavigationFragment.Container {

    private static final int PERMISSION_ACCESS_FINE_LOCATION = 101;

    private Button startTallyGoButton;

    /**
     * Here we check for location permissions and create a button to start TallyGo
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check for location
        if (!TGUtils.checkLocationPermission(this)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ACCESS_FINE_LOCATION);
        }

        startTallyGoButton = findViewById(R.id.b_start_tallygo);
        startTallyGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTallyGoNavFragment();
                startTallyGoButton.setVisibility(View.GONE);
            }
        });

    }

    /**
     * This is the starting point for the fragment. We check if TallyGo has been initialized and
     * if it has we proceed, if not we initialize TallyGo with our application and meta-data from
     * our AndroidManifest.xml
     */
    private void startTallyGoNavFragment() {
        if (TallyGo.getInstance() != null && TallyGo.getInstance().isInitialized()) {
            requestRoute();
            return;
        }

        //not initialized so we initialize first
        TallyGo.initializeFromMetaData(getApplication(), new TallyGo.InitializeCallback() {
            @Override
            public void onSuccess() {
                requestRoute();
            }

            @Override
            public void onNoInternetFailure(long retryDelay) {
                //the retry delay is in milliseconds and will increase as we retry the connection,
                //in your own application feel free to retry initialization during this time
                TGToastHelper.showLong(getBaseContext(), "No internet! Retrying in: " + retryDelay / 1000);
            }

            @Override
            public void onFailure(Exception e) {
                //a failure occurred and you may handle it however you like
                TGToastHelper.showLong(getBaseContext(), "Weird failure!");
                startTallyGoButton.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Here we request our route. If location permissions are denied we exit as we need a current
     * location for this method. You may change the coordinates to whatever you desire and see how
     * navigation between points works
     */
    private void requestRoute() {
        if (!TGUtils.checkLocationPermission(this)) {
            TGToastHelper.showLong(getBaseContext(), "Location permissions denied!");
            return;
        }

        //you can create any two points and see different routes
        LatLng currentLocation = TGUtils.getRealCurrentCoordinate(getBaseContext());
        LatLng destinationCoordinate = new LatLng(34.0518764d, -118.2425234d); //central LA

        List<LatLng> waypoints = new ArrayList<>();
        waypoints.add(currentLocation);
        waypoints.add(destinationCoordinate);

        //current date
        Date date = new Date();

        //create the request with the time supplied being departure time
        TGRouteRequest routeRequest = new TGRouteRequest(waypoints, date,
                TGRouteRequest.TGRouteRequestType.DEPARTURE_TIME);

        TallyGo.getTGNavigation().route(routeRequest, new TGNavigationEndpoint.TGRouteCallback() {
            @Override
            public void onSuccess(TGRouteResponse tgRouteResponse) {
                //we received a response so we start the navigation fragment with this route
                onShowNavigationFragment(tgRouteResponse.getStringResponse());
            }

            @Override
            public void onFailure(Exception e) {
                //something failed, this could be a no-network error. handle as you please
                TGToastHelper.showLong(getBaseContext(), "Weird failure!");
                startTallyGoButton.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * These are override methods from TGNavigationFragment.Container. The container interface
     * allows children of this activity to make calls back to this activity to show and hide
     * navigation. If you would like your activity to be aware of these calls then implement
     * this container.
     */

    @Override
    public void onHideNavigationFragment() {
        //the navigation fragment closes itself, so we don't need to handle it here
        startTallyGoButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowNavigationFragment(String routeJsonString) {
        startTallyGoButton.setVisibility(View.GONE);

        //this is where the navigation fragment is started
        TGNavigationFragment fragment =
                TGNavigationFragment.newInstance(routeJsonString);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_main, fragment, TGNavigationFragment.TAG);
        fragmentTransaction.addToBackStack(TGNavigationFragment.TAG);
        fragmentTransaction.commit();
    }
}
