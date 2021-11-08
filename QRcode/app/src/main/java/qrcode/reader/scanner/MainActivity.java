package qrcode.reader.scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {
    private CodeScanner codeScanner;
    public TextView tv_result;
    CodeScannerView scannerView;
    Context context = this;
    public TextView linktext;
    TextView deneme;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},100);
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

      /*  List<String> testDeviceIds = Arrays.asList("37D6CEA4193FB625");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //         .setTestDeviceIds(Arrays.asList("37D6CEA4193FB625"))
                //      .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //      .addTestDevice("")
                .build();
        mAdView.loadAd(adRequest);*/

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getSupportActionBar().hide();

        scannerView = findViewById(R.id.scanner_view);
        tv_result = findViewById(R.id.tv_result);
        codeScanner = new CodeScanner(this,scannerView);
        linktext = findViewById(R.id.linktext);

        tv_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ResultActivity.class);
                startActivity(intent);

            }
        });

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        //   linktext.setText(result.getText());
                        tv_result.setText(result.getText());
                        intent.putExtra("Key", tv_result.getText().toString()); //veri gönderiliyor
                        startActivity(intent);
                    }
                });

            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });






    }
    @Override
    protected void onPause(){
        codeScanner.releaseResources();
        super.onPause();
    }
    @Override
    protected  void onResume(){
        codeScanner.startPreview();
        super.onResume();
    }

}