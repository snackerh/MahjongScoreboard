package snacker.mahjongscoreboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FinalScore extends AppCompatActivity {

    int init = 0;
    int score[] = {0,0,0,0};
    int diff[] = {0,0,0,0};
    double diffpoint[] = {0.0,0.0,0.0,0.0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        Intent intent = getIntent();
        init = intent.getIntExtra("res", 30000);
        score[0] = intent.getIntExtra("east", 30000);
        score[1] = intent.getIntExtra("south", 30000);
        score[2] = intent.getIntExtra("west", 30000);
        score[3] = intent.getIntExtra("north", 30000);

        diff[0] = intent.getIntExtra("eastdiff", 0);
        diff[1] = intent.getIntExtra("southdiff", 0);
        diff[2] = intent.getIntExtra("westdiff", 0);
        diff[3] = intent.getIntExtra("northdiff", 0);

        for(int i = 0; i < 4; i++){
            diffpoint[i] = diff[i] / 1000;
        }

        TextView east = (TextView) findViewById(R.id.eastresult);
        TextView south = (TextView) findViewById(R.id.southresult);
        TextView west = (TextView) findViewById(R.id.westresult);
        TextView north = (TextView) findViewById(R.id.northresult);

        if(score[0] > 30000) {
            east.setText("東: +" + (score[0] - init) + " (1위와의 차이:" + diff[0] + ")");
        }
        else{
            east.setText("東: " + (score[0] - init) + " (1위와의 차이:" + diff[0] + ")");
        }
        if(score[1] > 30000) {
            south.setText("南: +" + (score[1] - init) + " (1위와의 차이:" + diff[1] + ")");
        }
        else{
            south.setText("南: " + (score[1] - init) + " (1위와의 차이:" + diff[1] + ")");
        }
        if(score[2] > 30000) {
            west.setText("西: +" + (score[2] - init) + " (1위와의 차이:" + diff[2] + ")");
        }
        else{
            west.setText("西: " + (score[2] - init) + " (1위와의 차이:" + diff[2] + ")");
        }
        if(score[3] > 30000) {
            north.setText("北: +" + (score[3] - init) + " (1위와의 차이:" + diff[3] + ")");
        }
        else{
            north.setText("北: " + (score[3] - init) + " (1위와의 차이:" + diff[3] + ")");
        }
        Board.fa.finish();
    }
}
