package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String HOME_KEY = "home";
    public static final String AWAY_KEY = "away";
    public static final String IMAGEHOME_KEY = "imageHome";
    public static final String IMAGEAWAY_KEY = "imageAway";


    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final int HOME_REQUEST_CODE = 1;
    private static final int AWAY_REQUEST_CODE = 2;

    private ImageView homeLogo;
    private ImageView awayLogo;

    private EditText homeTeam;
    private EditText awayTeam;

    private Uri homeUri;
    private Uri awayUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeTeam = findViewById(R.id.home_team);
        awayTeam = findViewById(R.id.away_team);
        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);

        //TODO
        //Fitur Main Activity
        //5. Next Button Pindah Ke MatchActivity
    }

    //1. Validasi Input Home Team
    //2. Validasi Input Away Team

    public void handleNext(View view) {
        if(homeTeam.equals("") && awayTeam.equals("")){
            Toast.makeText(getApplicationContext(), "Isi Team Terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
        else {
            String home = homeTeam.getText().toString();
            String away = awayTeam.getText().toString();
            String imageHome = homeUri.toString();
            String imageAway = awayUri.toString();
            Intent intent = new Intent(this, MatchActivity.class);
            intent.putExtra(HOME_KEY, home); //memanggil data
            intent.putExtra(AWAY_KEY, away);
            intent.putExtra(IMAGEHOME_KEY, imageHome);
            intent.putExtra(IMAGEAWAY_KEY, imageAway);
            startActivity(intent);
        }
    }
    //3. Ganti Logo Home Team
    public void handleImageHome(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, HOME_REQUEST_CODE);
    }
    //4. Ganti Logo Away Team
    public void handleImageAway(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, AWAY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == HOME_REQUEST_CODE) {
            if (data != null) {
                try {
                    homeUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeUri);
                    homeLogo.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        else if (requestCode == AWAY_REQUEST_CODE) {
            if (data != null) {
                try {
                    awayUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awayUri);
                    awayLogo.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}
