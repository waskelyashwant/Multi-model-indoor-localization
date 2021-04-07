package com.pranjal.wifidata;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
// New addition
//import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.*;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private CheckBox wifiButton;
    private WifiManager wifiManager;
    private WifiRepository wifiRepository;
    //private SensorManager sensorManager;
    private BroadcastReceiver wifiScanReceiver;
    private CheckBox magneticButton;
    Context context = this;
    private CheckBox gsmButton;
    private TelephonyManager telephonyManager;
    private Button syncButton;
    private Button startButton;
    private Button stopButton;
    private String location;
    private CheckBox gpsbutton;
    private EditText locationEditText;
    // New addition
    // System sensor manager instance.
    private SensorManager mSensorManager;

    // Accelerometer and magnetometer sensors, as retrieved from the
    // sensor manager.
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;

    // Current data from accelerometer & magnetometer.  The arrays hold values
    // for X, Y, and Z.
    private float[] mAccelerometerData = new float[3];
    private float[] mMagnetometerData = new float[3];

    // TextViews to display current sensor values.
    private TextView mTextSensorAzimuth;
    private TextView mTextSensorPitch;
    private TextView mTextSensorRoll;

    // ImageView drawables to display spots.
    private ImageView mSpotTop;
    private ImageView mSpotBottom;
    private ImageView mSpotLeft;
    private ImageView mSpotRight;
    private double MinMagx = 400, MaxMagx = -400, MinMagy = 400, MaxMagy = -400, MinMagz = 400, MaxMagz = -400;
    private double Xsf, Ysf, Zsf, Xoff, Yoff, Zoff;
    private double sumx = 0, sumy = 0, sumz = 0, MX, MY, MZ;
    public static int cnti = 0;
    // System display. Need this for determining rotation.
    private Display mDisplay;

    // Very small values for the accelerometer (on all three axes) should
    // be interpreted as 0. This value is the amount of acceptable
    // non-zero drift.
    private static final float VALUE_DRIFT = 0.05f;

    // Magnetometer
    TextView azi;
    TextView pit;
    TextView rol;
    TextView f00, f01, f02, f10, f11, f12, f20, f21, f22;

    private FusedLocationProviderClient client;


    private static SensorManager sensorManager;
    private Sensor sensor; // Magnetometer End

    double lat=0,lon=0;

    //close


    String[] appPermissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForPermissions();

        wifiButton = findViewById(R.id.startwifi);
        wifiRepository = new WifiRepository(getApplication());
        wifiManager = (WifiManager)
                context.getSystemService(Context.WIFI_SERVICE);

        wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                scanSuccess();
            }
        };
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magneticButton = findViewById(R.id.startmagnetic);
        gsmButton = findViewById(R.id.startgsm);
        gpsbutton=findViewById(R.id.startgps);
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        syncButton = findViewById(R.id.sync);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sync();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        startButton = findViewById(R.id.start);
        stopButton  = findViewById(R.id.stop);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
        locationEditText = findViewById(R.id.location);

        // New addition
//        mTextSensorAzimuth = (TextView) findViewById(R.id.value_azimuth);
//        mTextSensorPitch = (TextView) findViewById(R.id.value_pitch);
//        mTextSensorRoll = (TextView) findViewById(R.id.value_roll);
//        mSpotTop = (ImageView) findViewById(R.id.spot_top);
//        mSpotBottom = (ImageView) findViewById(R.id.spot_bottom);
//        mSpotLeft = (ImageView) findViewById(R.id.spot_left);
//        mSpotRight = (ImageView) findViewById(R.id.spot_right);

        // Get accelerometer and magnetometer sensors from the sensor manager.
        // The getDefaultSensor() method returns null if the sensor
        // is not available on the device.
        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer = mSensorManager.getDefaultSensor(
                Sensor.TYPE_MAGNETIC_FIELD);

        // Get the display from the window manager (for rotation).
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = wm.getDefaultDisplay();



        // Magnetometer
        // super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

//        textView = (TextView) findViewById(R.id.textView);
//        azi = (TextView)findViewById(R.id.azi);
//        pit = (TextView)findViewById(R.id.pit);
//        rol = (TextView)findViewById(R.id.rol);
//
//        f00 = (TextView)findViewById(R.id.f00);
//        f01 = (TextView)findViewById(R.id.f01);
//        f02 = (TextView)findViewById(R.id.f02);
//        f10 = (TextView)findViewById(R.id.f10);
//        f11 = (TextView)findViewById(R.id.f11);
//        f12 = (TextView)findViewById(R.id.f12);
//        f20 = (TextView)findViewById(R.id.f20);
//        f21 = (TextView)findViewById(R.id.f21);
//        f22 = (TextView)findViewById(R.id.f22);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); // Magnetometer end

//        requestPermission();
//        client = LocationServices.getFusedLocationProviderClient(this);

        //close
    }

    //New Addition
    // Magnetometer

    @Override
    protected void onResume() {
        super.onResume();

        if(sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Not supported", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }
    // Magnetometer End



    /**
     * Listeners for the sensors are registered in this callback so that
     * they can be unregistered in onStop().
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Listeners for the sensors are registered in this callback and
        // can be unregistered in onStop().
        //
        // Check to ensure sensors are available before registering listeners.
        // Both listeners are registered with a "normal" amount of delay
        // (SENSOR_DELAY_NORMAL).
        if (mSensorAccelerometer != null) {
            mSensorManager.registerListener(this, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorMagnetometer != null) {
            mSensorManager.registerListener(this, mSensorMagnetometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unregister all sensor listeners in this callback so they don't
        // continue to use resources when the app is stopped.
        mSensorManager.unregisterListener(this);
    }

    //close


    private void stop() {
        if(wifiButton.isChecked()){
            context.unregisterReceiver(wifiScanReceiver);
        }
        if(magneticButton.isChecked()) {
            /** Xsf = MaxMagy-MinMagy/MaxMagx-MinMagx;
             Ysf = MaxMagx - MinMagx/MaxMagy -MinMagy;
             Zsf = MaxMagz - MinMagz/MaxMagz - MinMagz;
             Xoff = ((MaxMagx - MinMagx/2) -MaxMagx)*Xsf;
             Yoff = ((MaxMagy - MinMagy/2) - MaxMagy)*Ysf;
             Zoff = (((MaxMagz - MinMagz)/2) - MaxMagz)*Zsf ;
             MX = Xsf * (sumx/cnti) + Xoff;
             MY = Ysf * (sumy/cnti )+ Yoff;
             MZ = Zsf * (sumz/cnti) + Zoff;
             double magnitude;
             magnitude=Math.sqrt((MX*MX)+(MY*MY)+(MZ*MZ));
             Magnetic m = new Magnetic(MX, MY, MZ,MX, MY, MZ,magnitude,getTime(),location);
             wifiRepository.insertMagnetic(m);**/
            mSensorManager.unregisterListener(this);
        }
        startButton.setVisibility(View.VISIBLE);
    }

    private void start() {
        if(wifiButton.isChecked()){
            getWifiData();
        }
        if(magneticButton.isChecked()) {
            getSensorData();
        }
        if(gpsbutton.isChecked()){
            getGPSData();
        }
        startButton.setVisibility(View.INVISIBLE);
        location = locationEditText.getText().toString();
    }



    private void sync() throws ExecutionException, InterruptedException {
        List<wifi> l = wifiRepository.getallwifi(location);
        List<Magnetic> l2 = wifiRepository.getallmagnetic(location);
        List<gsm> l3 = wifiRepository.getallgsm(location);
        //List<gps> l4 = wifiRepository.getallgps(location);
        new Writer(l,l2,l3,context,location).execute();
        Toast.makeText(this,"Exported",Toast.LENGTH_LONG).show();
    }


    private void checkForPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();
        for(String per:appPermissions){
            if(ContextCompat.checkSelfPermission(context,per)!=PackageManager.PERMISSION_GRANTED){
                permissionsNeeded.add(per);
            }
        }
        if(!permissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,permissionsNeeded.toArray(new String[permissionsNeeded.size()]),123);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getGsmData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
        int t= 0;
        int cid=0;
        if (cellInfo instanceof CellInfoCdma) {
            t = ((CellInfoCdma) cellInfo).getCellSignalStrength().getDbm();
            cid = ((CellInfoCdma) cellInfo).getCellIdentity().getBasestationId();
        }
        if (cellInfo instanceof CellInfoGsm) {
            t =  ((CellInfoGsm) cellInfo).getCellSignalStrength().getDbm();
            cid = ((CellInfoGsm) cellInfo).getCellIdentity().getCid();
        }
        if (cellInfo instanceof CellInfoLte) {
            t =  ((CellInfoLte) cellInfo).getCellSignalStrength().getDbm();
            cid = ((CellInfoLte) cellInfo).getCellIdentity().getCi();
        }

        gsm g = new gsm(cid,t,getTime(),location);
        wifiRepository.insertGSM(g);
    }

    private void getWifiData() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, intentFilter);
        boolean success = wifiManager.startScan();
        if (!success) {
            // scan failure handling
            Toast.makeText(this,"Scan Failed",Toast.LENGTH_LONG).show();
            scanFailure();
        }
    }


    private void getGPSData(){
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

            if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                return;
            }
            client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location !=null) {
                        //String s1 = String.valueOf(location.getLatitude());
                        //String s2 = String.valueOf(location.getLongitude());
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
            });
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private void getSensorData(){
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    private void scanSuccess(){
        List<ScanResult> results = wifiManager.getScanResults();
        if(results.size()==0){
            return;
        }
        for (ScanResult s: results){
            String ssid = s.SSID;
            int frequency = s.frequency;
            int level = s.level;
            String bssid = s.BSSID;
            wifi w = new wifi(bssid,ssid,frequency,level,getTime(),location);
            wifiRepository.insert(w);
        }

    }

    private void scanFailure() {

        wifiButton.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensorType = sensorEvent.sensor.getType();
        if(sensorType==Sensor.TYPE_ACCELEROMETER)
        {   mAccelerometerData[0]=sensorEvent.values[0];
            mAccelerometerData[1]= sensorEvent.values[1];
            mAccelerometerData[2]= sensorEvent.values[2];
        }
        // Magnetometer
        else if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
            mMagnetometerData[0] = sensorEvent.values[0];
            mMagnetometerData[1] = sensorEvent.values[1];
            mMagnetometerData[2] = sensorEvent.values[2];
            // Rotation  matrix

            float[] rotationMatrix = new float[9];
            SensorManager.getRotationMatrix(rotationMatrix,
                    null, mAccelerometerData, mMagnetometerData);


            float[] final_matrix = new float[3];

            int cnt = 0;
            for (int i = 0; i < 9; i++) {
                float sum = 0;
                int k;
                for (k = 0; k < 3; k++) {
                    sum += mMagnetometerData[k] * rotationMatrix[i + k];
                }

                final_matrix[cnt] = sum;
                cnt++;
                i += (k - 1);
            }
            double magX = final_matrix[0];
            double magY = final_matrix[1];
            double magZ = final_matrix[2];

            /**if (magX < MinMagx)
             MinMagx = magX;
             if (magX > MaxMagx)
             MaxMagx = magX;
             if (magY < MinMagy)
             MinMagy = magY;
             if (magY > MaxMagy)
             MaxMagy = magY;
             if (magZ < MinMagz)
             MinMagz = magZ;
             if (magZ > MaxMagz)
             MaxMagz = magZ;
             sumx += magX;
             sumy += magY;
             sumz += magZ;**/
            double magnitude=Math.sqrt((magX*magX)+(magY*magY)+(magZ*magZ));
            double orientation = Math.atan((Math.sqrt((magX*magX)+(magY*magY)))/magZ);
            double Bh=magnitude*Math.cos(orientation);
            double Bv=magnitude*Math.sin(orientation);

            Magnetic m = new Magnetic(Bh,Bv,magnitude,lat, lon, getTime(),location);
            wifiRepository.insertMagnetic(m);
        }

        if(gsmButton.isChecked()){
            getGsmData();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public String getTime(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String str = dateFormat.format(date);
        return str;
    }


}