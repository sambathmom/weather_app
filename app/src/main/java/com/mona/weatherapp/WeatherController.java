package com.mona.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;


public class WeatherController extends AppCompatActivity {

    // Constants:
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // App ID to use OpenWeather data
    final String APP_ID = "27d815b33bdd6df971cc91596b23e4f6";
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 5000;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;

    // TODO: Set LOCATION_PROVIDER here:
    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;


    // Member Variables:
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;

    // TODO: Declare a LocationManager and a LocationListener here:
    LocationManager locationManager;
    LocationListener locationListener; //Check location in change


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_controller_layout);

        // Linking the elements in the layout to Java code
        mCityLabel =  findViewById(R.id.locationTV);
        mWeatherImage =  findViewById(R.id.weatherSymbolIV);
        mTemperatureLabel =  findViewById(R.id.tempTV);
        ImageButton changeCityButton =  findViewById(R.id.changeCityButton);


        // TODO: Add an OnClickListener to the changeCityButton here:

    }


    // TODO: Add onResume() here:

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Weather ", "onResume() call back");

        getWeatherForCurrentLocation();
    }


    // TODO: Add getWeatherForNewCity(String city) here:


    // TODO: Add getWeatherForCurrentLocation() here:
    private void getWeatherForCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Weather ", "onLocationChanged()  call back received.");

                String lon = String.valueOf(location.getLongitude());
                String lat = String.valueOf(location.getLatitude());

                Log.d("Weather ", "Lon " + lon);
                Log.d("Weather ", "Lat " + lat);


                RequestParams requestParams = new RequestParams();
                requestParams.put("lon", lon);
                requestParams.put("lat",  lat);
                requestParams.put("appId", APP_ID);

                letsDoSomeNetworking(requestParams);



            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("Weather ", "onStatusChanged()  call back received.");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("Weather ", "onProviderEnabled()  call back received.");
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("Weather ", "onProviderDisabled()  call back received.");
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            return;
        }
        locationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Weather ", "onRequestPermissionsResult() permission granted.");
                getWeatherForCurrentLocation();
            } else {
                Log.d("Weather ", "onRequestPermissionsResult() Permission denied.");
            }
        }
    }

    // TODO: Add letsDoSomeNetworking(RequestParams params) here:
    public void letsDoSomeNetworking(RequestParams requestParams) {
        Log.d("Weather", "letsDoSomeNetworking() call back");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Weather", "OnSuccess() call ");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Weather", "onFailure() call ");
            }
        });
    }


    // TODO: Add updateUI() here:



    // TODO: Add onPause() here:



}
