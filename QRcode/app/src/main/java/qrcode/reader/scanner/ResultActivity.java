package qrcode.reader.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class ResultActivity extends AppCompatActivity {
    ActivityResult binding;
    private InterstitialAd mInterstitialAd;
    TextView linktextt;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().hide();

        linktextt=(TextView)findViewById(R.id.linktext);
        Bundle bnd = getIntent().getExtras();
        String veri = bnd.getString("Key");
        linktextt.setText(veri);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-2251958122131191/5026810487", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                //  super.onAdLoaded(interstitialAd);
                System.out.println("Ad Loaded");
                mInterstitialAd = interstitialAd;

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(ResultActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                //super.onAdFailedToLoad(loadAdError);
                System.out.println("Ad Load Failed");
                mInterstitialAd = null;

            }

        });

        //   final TextView sTextView = findViewById(R.id.textView);
        ImageView share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sTextView
                String s = linktextt.getText().toString();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, s);
                startActivity(Intent.createChooser(sharingIntent, "Share text via"));
            }
        });

        TextView web = (TextView) findViewById(R.id.linktext);

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                web.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });

        ImageView web1 = (ImageView) findViewById(R.id.browser);

        web1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    web.setMovementMethod(LinkMovementMethod.getInstance());
                /*    Log.d(getClass().getSimpleName(), "in onClick");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url_to_website));
                startActivity(browserIntent);*/
                web.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        web.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                });
            }
        });


       /* List<String> testDeviceIds = Arrays.asList("37D6CEA4193FB625");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder()
                //         .setTestDeviceIds(Arrays.asList("37D6CEA4193FB625"))
                //      .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //      .addTestDevice("")
                .build();
        mAdView.loadAd(adRequest1);
*/
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest1);

    }




}

