package rafsanches.com.br.dmic_aula05_OO_reciclagemView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import rafsanches.com.br.dmic_aula05_OO_reciclagemView.R;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int REQUEST_CODE_GPS_PERMISSION = 1001;
    private ArrayList<String> historyPos = new ArrayList<>();

    private EditText searchEditText;
    private TextView locationTextView;

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latitude = location.getLatitude();
                longitude = location.getLongitude();
                if(historyPos.size() >= 50) {
                    historyPos.remove(0);
                }
                historyPos.add(String.format("Lat: %f, Long: %f", latitude, longitude));

//                locationTextView.setText(String.format("Lat: %f, Long: %f", latitude, longitude));

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        setContentView(R.layout.activity_main);
    }

    public void MostraLista (View view) {
        Intent intent = new Intent (this, ListaLocaisActivity.class);
        intent.putExtra("historyPos", historyPos);
        startActivity(intent);
    }

    @Override
    public void onStart(){

        super.onStart();

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120, 200, locationListener);
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS_PERMISSION);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == REQUEST_CODE_GPS_PERMISSION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1,1,locationListener);
                }
            }
            else{
                Toast.makeText(this, getString(R.string.sem_gps_nao_rola), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

}