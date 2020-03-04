package id.putraprima.skorbola;

import androidx.annotation.Nullable;
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
    private static final String STATUS_KEY = "status";

//    public static final String HOME_KEY = "home";
//    public static final String AWAY_KEY = "away";
//    public static final String HOME_SCORE_KEY = "home_score";
//    public static final String AWAY_SCORE_KEY = "away_score";

    private int homeScoreValue, awayScoreValue;
    private TextView homeText;
    private TextView awayText;
    private TextView homeScoreText;
    private TextView awayScoreText;
    private ImageView homeImage;
    private ImageView awayImage;
    private Uri homeUri, awayUri;
    public int homeScore = 0;
    public int awayScore = 0;
    private String name = "";
    private TextView nameScorerHome, nameScorerAway;
    private String returnString;
    String homename;
    String awayname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);


        homeText = findViewById(R.id.txt_home);
        awayText = findViewById(R.id.txt_away);
        homeImage = findViewById(R.id.home_logo);
        awayImage = findViewById(R.id.away_logo);
        homeScoreText = findViewById(R.id.score_home);
        awayScoreText = findViewById(R.id.score_away);
        nameScorerHome = findViewById(R.id.txt_home_scorer);
        nameScorerAway = findViewById(R.id.txt_away_scorer);



        Bundle extras = getIntent().getExtras();
        Uri imageHomeUri = Uri.parse(extras.getString("imageHome"));
        Uri imageAwayUri = Uri.parse(extras.getString("imageAway"));
        if (extras != null ){
            String homename = extras.getString(MainActivity.HOME_KEY);
            String awayname = extras.getString(MainActivity.AWAY_KEY);

            homeText.setText(homename);
            awayText.setText(awayname);
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

        Intent i = new Intent(this, ScorerActivity.class);
        startActivityForResult(i, 1);
//        homeScoreValue = Integer.valueOf(homeScoreText.getText().toString());
//        homeScoreValue++;
//        homeScoreText.setText(String.valueOf(homeScoreValue));
    }


    public void handleAwayScore(View view) {

        Intent i = new Intent(this, ScorerActivity.class);
        startActivityForResult(i, 2);

//        awayScoreValue = Integer.valueOf(awayScoreText.getText().toString());
//        awayScoreValue++;
//        awayScoreText.setText(String.valueOf(awayScoreValue));
    }


//    public void handleResult(View view) {
//        Intent intent = new Intent(this,ResultActivity.class);
//        intent.putExtra("homename", homename);
//        intent.putExtra("awayText", awayname);
//        intent.putExtra("homeScoreText",homeScoreValue);
//        intent.putExtra("awayScoreText",awayScoreValue);
//        startActivity(intent);
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK) {
                homeScore++;
                homeScoreText.setText(String.valueOf(homeScore));

                returnString = data.getStringExtra("scorerName");
                name = returnString + "\n"+nameScorerHome.getText().toString();
                nameScorerHome.setText(name);
            }
        }else if (requestCode == 2){
            if (resultCode == RESULT_OK) {
                awayScore++;
                awayScoreText.setText(String.valueOf(awayScore));

                returnString = data.getStringExtra("scorerName");
                name = returnString + "\n"+nameScorerAway.getText().toString();
                nameScorerAway.setText(name);
            }
        }
    }

    public void handleCek(View view) {
        Intent i = new Intent(this, ResultActivity.class);
//        String status = null;
//        i.putExtra(STATUS_KEY, status);
        if (homeScore == awayScore ){
            i.putExtra(STATUS_KEY, "Draw");
        }else if (homeScore > awayScore){
            i.putExtra(STATUS_KEY,"Name of Winning :"+homeText.getText().toString()+"\n Scorer Name : \n"+nameScorerHome.getText().toString());
        }else if (homeScore < awayScore) {
            i.putExtra(STATUS_KEY, "Name of Winning :" + awayText.getText().toString() + "\n Scorer Name : \n" + nameScorerAway.getText().toString());
        }
        startActivity(i);
    }

}