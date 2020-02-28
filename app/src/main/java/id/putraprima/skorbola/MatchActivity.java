package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MatchActivity extends AppCompatActivity {

    public static final String HOME_KEY = "home";
    public static final String AWAY_KEY = "away";
    public static final String HOME_SCORE_KEY = "home_score";
    public static final String AWAY_SCORE_KEY = "away_score";

    private int homeScoreValue, awayScoreValue;

    private TextView homeText;
    private TextView awayText;
    String homeName;
    String awayName;

    private TextView homeScoreText;
    private TextView awayScoreText;

    private ImageView homeImage;
    private ImageView awayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        homeImage = findViewById(R.id.home_logo);
        awayImage = findViewById(R.id.away_logo);
        homeText = findViewById(R.id.txt_home);
        awayText = findViewById(R.id.txt_away);


        homeScoreText = findViewById(R.id.score_home);
        awayScoreText = findViewById(R.id.score_away);
        Bundle extras = getIntent().getExtras();
        Uri imageHomeUri = Uri.parse(extras.getString("imageHome"));
        Uri imageAwayUri = Uri.parse(extras.getString("imageAway"));
        if (extras != null ){
            String home = extras.getString(MainActivity.HOME_KEY);
            String away = extras.getString(MainActivity.AWAY_KEY);
            homeText.setText(home);
            awayText.setText(away);
            try {
                Bitmap homeImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageHomeUri);
                Bitmap awayImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageAwayUri);
                homeImage.setImageBitmap(homeImageBitmap);
                awayImage.setImageBitmap(awayImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        //TODO
        //1.Menampilkan detail match sesuai data dari main activity

        //2.Tombol add score menambahkan satu angka dari angka 0, setiap kali di tekan
        //3.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang ke ResultActivity, jika seri di kirim text "Draw"
    }


    public void handleHomeScore(View view) {
//        homeScoreValue = Integer.valueOf(homeScoreText.getText().toString());
//        homeScoreValue++;
//        homeScoreText.setText(String.valueOf(homeScoreValue));
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, HOME_ACTIVITY_REQUEST_CODE);
    }


    public void handleAwayScore(View view) {
//        awayScoreValue = Integer.valueOf(awayScoreText.getText().toString());
//        awayScoreValue++;
//        awayScoreText.setText(String.valueOf(awayScoreValue));
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, HOME_ACTIVITY_REQUEST_CODE);
    }


    public void handleResult(View view) {
        Intent i = new Intent(MatchActivity.this, ResultActivity.class);
        i.putExtra("homename", homeName);
        i.putExtra("awayname", awayName);


        if (homeScoreValue > awayScoreValue){
            i.putExtra("End game", "The Winner is" + homeText.getText().toString());
        } else if (homeScoreValue < awayScoreValue) {
            i.putExtra("End game", "The Winner is" + awayText.getText().toString());
        } else {
            i.putExtra("End game", "Draw");
        }
        startActivity(i);
    }
}
