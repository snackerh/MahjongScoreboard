package snacker.mahjongscoreboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static snacker.mahjongscoreboard.R.id.btn_half;

public class MainActivity extends AppCompatActivity {

    TextView a;
    TextView b;
    TextView ret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a =(TextView) findViewById(R.id.roundText);
        b =(TextView) findViewById(R.id.startText);
        ret = (TextView) findViewById(R.id.returnText);

        findViewById(R.id.btn_half).setOnClickListener(mClickListener);
        findViewById(R.id.btn_quarter).setOnClickListener(mClickListener);
        findViewById(R.id.btn_whole).setOnClickListener(mClickListener);
        findViewById(R.id.btn_custom).setOnClickListener(mClickListener);
    }



    Button.OnClickListener mClickListener = new View.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, Board.class);
            switch (v.getId()){
                case R.id.btn_half:
                    intent.putExtra("maxRound", 8);
                    intent.putExtra("initScore",30000);
                    intent.putExtra("resultScore",30000);
                    startActivity(intent);
                    break;
                case R.id.btn_quarter:
                    intent.putExtra("maxRound", 4);
                    intent.putExtra("initScore",25000);
                    intent.putExtra("resultScore",30000);
                    startActivity(intent);
                    break;
                case R.id.btn_whole:
                    intent.putExtra("maxRound", 16);
                    intent.putExtra("initScore",30000);
                    intent.putExtra("resultScore",30000);
                    startActivity(intent);
                    break;
                case R.id.btn_custom:
                    String as = a.getText().toString();
                    String bs = b.getText().toString();
                    String cs = ret.getText().toString();
                    String err = "입력한 정보가 올바르지 않습니다.";
                    int ai, bi, ci;
                    if(as.equals("")) {
                        ai = 8;
                    }
                    else{
                        ai = Integer.parseInt(as);
                    }
                    if(bs.equals("")) {
                        bi = 30000;
                    }
                    else{
                        bi = Integer.parseInt(bs);
                    }
                    if(cs.equals("")) {
                        ci = 30000;
                    }
                    else{
                        ci = Integer.parseInt(cs);
                    }
                    AlertDialog.Builder bld = new AlertDialog.Builder(MainActivity.this);
                    bld.setTitle("오류");
                    if(ai < 1 || ai > 16){
                        err += "\n국수: 1부터 16사이의 숫자를 입력하여 주십시오.";
                    }
                    if(bi < 0 || bi > 250000){
                        err += "\n시작점수: 1부터 250000사이의 숫자를 입력하여 주십시오.";
                    }
                    if(ci < bi){
                        err += "\n반환점수는 시작점수보다 같거나 많아야 합니다.";
                    }
                    if(!err.equals("입력한 정보가 올바르지 않습니다.")){
                    bld.setMessage(err);
                    bld.show();
                        break;
                    }
                    else {
                        intent.putExtra("maxRound", ai);
                        intent.putExtra("initScore", bi);
                        //intent.putExtra("resultScore",ci);
                        startActivity(intent);
                    }
            }

        }
    };
}
