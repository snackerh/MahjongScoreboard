package snacker.mahjongscoreboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class Board extends AppCompatActivity {
    public int score[] = {0, 0, 0, 0};
    final public String wind[] = {"東", "南", "西", "北"};
    public int diff[] = {0, 0, 0, 0};
    public int round = 0;

    public int extend = 0;
    public int vault = 0;
    public int max = 0;

    Button east;
    Button south;
    Button west;
    Button north;
    Button status;
    Button btnextend;
    Button chonbo;
    Button cancel;

    TextView eastlight;
    TextView southlight;
    TextView westlight;
    TextView northlight;

    int initScore;
    int returnScore;
    int maxRound;
    int firstbtn = -1;
    int secondbtn = -1;
    boolean drawstatus = false;
    boolean tenpai[] = {false, false, false, false};
    int pan = 0;
    int boo = 0;
    int jackpot = 0;
    int oyajackpot = 0;

    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        fa = this;

        east = (Button) findViewById(R.id.btn_east);
        south = (Button) findViewById(R.id.btn_south);
        west = (Button) findViewById(R.id.btn_west);
        north = (Button) findViewById(R.id.btn_north);
        status = (Button) findViewById(R.id.status);
        btnextend = (Button) findViewById(R.id.btn_extend);
        chonbo = (Button) findViewById(R.id.foul);
        cancel = (Button) findViewById(R.id.btn_cancel);

        eastlight = (TextView) findViewById(R.id.eastlight);
        southlight = (TextView) findViewById(R.id.southlight);
        westlight = (TextView) findViewById(R.id.westlight);
        northlight = (TextView) findViewById(R.id.northlight);

        Intent intent = getIntent();

        maxRound = intent.getIntExtra("maxRound", 8);
        initScore = intent.getIntExtra("initScore", 30000);
        returnScore = intent.getIntExtra("returnScore", 30000);

        for (int i = 0; i < 4; i++) {
            score[i] = initScore;
        }

        textUpdate();

        east.setOnClickListener(mClick);
        south.setOnClickListener(mClick);
        west.setOnClickListener(mClick);
        north.setOnClickListener(mClick);
        status.setOnClickListener(mClick);
        chonbo.setOnClickListener(mClick);
        btnextend.setOnClickListener(mClick);
        cancel.setOnClickListener(mClick);
    }

    public void textUpdate() {
        max = findMax(score);
        calculateDiff(max);

        east.setText(wind[(16 - round) % 4] + " - " + score[0] + "(" + diff[0] + ")");
        south.setText(wind[(17 - round) % 4] + " - " + score[1] + "(" + diff[1] + ")");
        west.setText(wind[(18 - round) % 4] + " - " + score[2] + "(" + diff[2] + ")");
        north.setText(wind[(19 - round) % 4] + " - " + score[3] + "(" + diff[3] + ")");

        eastlight.setBackgroundColor(Color.WHITE);
        southlight.setBackgroundColor(Color.WHITE);
        westlight.setBackgroundColor(Color.WHITE);
        northlight.setBackgroundColor(Color.WHITE);

        switch(round % 4){
            case 0:
                eastlight.setBackgroundColor(Color.RED);
                break;
            case 1:
                southlight.setBackgroundColor(Color.RED);
                break;
            case 2:
                westlight.setBackgroundColor(Color.RED);
                break;
            case 3:
                northlight.setBackgroundColor(Color.RED);
                break;
        }

        if (round == maxRound || score[0] < 0 || score[1] < 0  || score[2] < 0  || score[3] < 0 ) {
            if(vault != 0) {
                score[max] += vault;
                vault = 0;
                calculateDiff(max);
            }
            status.setText("대국종료");
            AlertDialog.Builder bld = new AlertDialog.Builder(this);
            bld.setTitle("대국종료");
            bld.setMessage("대국이 종료되었습니다.");
            bld.setPositiveButton("결과확인", dialogClick);
            bld.show();

        } else if (round < 4) {
            status.setText("동" + (round + 1) + "국 " + extend + "본장\n공탁:" + vault);
        } else if (round < 8){
            status.setText("남" + (round - 3) + "국 " + extend + "본장\n공탁:" + vault);
        } else if (round < 12){
            status.setText("서" + (round - 7) + "국 " + extend + "본장\n공탁:" + vault);
        } else if (round < 16){
            status.setText("북" + (round - 11) + "국 " + extend + "본장\n공탁:" + vault);
        }
    }

    protected void buttonUpdate(){
        east.setBackgroundColor(Color.LTGRAY);
        east.setTextColor(Color.BLACK);
        south.setBackgroundColor(Color.LTGRAY);
        south.setTextColor(Color.BLACK);
        west.setBackgroundColor(Color.LTGRAY);
        west.setTextColor(Color.BLACK);
        north.setBackgroundColor(Color.LTGRAY);
        north.setTextColor(Color.BLACK);

        if(drawstatus){
            btnextend.setBackgroundColor(Color.YELLOW);
        }
        else{
            btnextend.setBackgroundColor(Color.LTGRAY);
        }

            if(tenpai[0]){
                east.setBackgroundColor(Color.WHITE);
                east.setTextColor(Color.RED);
            }
            if(tenpai[1]){
                south.setBackgroundColor(Color.WHITE);
                south.setTextColor(Color.RED);
            }
            if(tenpai[2]){
                west.setBackgroundColor(Color.WHITE);
                west.setTextColor(Color.RED);
            }
            if(tenpai[3]){
                north.setBackgroundColor(Color.WHITE);
                north.setTextColor(Color.RED);
            }

            if(firstbtn != -1 || drawstatus){
                cancel.setVisibility(View.VISIBLE);
            }
            else{
                cancel.setVisibility(View.GONE);
            }

        switch(firstbtn){
            case 0:
                east.setBackgroundColor(Color.CYAN);
                break;
            case 1:
                south.setBackgroundColor(Color.CYAN);
                break;
            case 2:
                west.setBackgroundColor(Color.CYAN);
                break;
            case 3:
                north.setBackgroundColor(Color.CYAN);
                break;
        }
    }

    protected int findMax(int score[]) {
        int maxIndex = 0;

        for (int i = 1; i < 4; i++) {
            if (score[maxIndex] < score[i]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    protected void calculateDiff(int maxIndex){
        for(int i = 0; i < 4; i++){
            diff[i] = (score[i] - score[maxIndex]);
        }
    }

    DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent matchend = new Intent(Board.this, FinalScore.class);
            matchend.putExtra("east", score[0]);
            matchend.putExtra("south", score[1]);
            matchend.putExtra("west", score[2]);
            matchend.putExtra("north", score[3]);
            matchend.putExtra("eastdiff", diff[0]);
            matchend.putExtra("southdiff", diff[1]);
            matchend.putExtra("westdiff", diff[2]);
            matchend.putExtra("northdiff", diff[3]);
            matchend.putExtra("res", returnScore);
            startActivity(matchend);
        }
    };

    Button.OnClickListener mClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_east:
                    if (!drawstatus){
                    if (firstbtn == -1) {
                        firstbtn = 0;
                        buttonUpdate();
                    }
                    else {
                        secondbtn = 0;
                        showDialog();
                    }
                    }
                    else{
                        tenpai[0] ^= true;
                        buttonUpdate();
                    }
                    break;
                case R.id.btn_south:
                    if(!drawstatus){
                    if (firstbtn == -1) {
                        firstbtn = 1;
                        buttonUpdate();
                    }
                    else {
                        secondbtn = 1;
                        showDialog();
                    }
                    }
                    else{
                        tenpai[1] ^= true;
                        buttonUpdate();
                    }
                    break;
                case R.id.btn_west:
                    if(!drawstatus){
                    if (firstbtn == -1) {
                        firstbtn = 2;
                        buttonUpdate();
                    }
                    else {
                        secondbtn = 2;
                        showDialog();
                    }
                    }
                    else{
                        tenpai[2] ^= true;
                        buttonUpdate();
                    }
                    break;
                case R.id.btn_north:
                    if(!drawstatus) {
                        if (firstbtn == -1) {
                            firstbtn = 3;
                            buttonUpdate();
                        } else {
                            secondbtn = 3;
                            showDialog();
                        }
                    }
                    else{
                        tenpai[3] ^= true;
                        buttonUpdate();
                    }
                    break;
                case R.id.status:
                    if(!drawstatus) {
                        if (firstbtn == -1)
                            Toast.makeText(Board.this, "리치를 하시려면 자기 위치를 터치한 후 중앙을 클릭하십시오.", Toast.LENGTH_LONG).show();
                        else {
                            if(tenpai[firstbtn]) {
                                Toast.makeText(Board.this, "당신은 이미 리치 상태입니다.", Toast.LENGTH_LONG).show();
                                firstbtn = -1;
                                buttonUpdate();
                            }
                            else{
                            vault += 1000;
                            score[firstbtn] -= 1000;
                            tenpai[firstbtn] = true;
                            textUpdate();
                            Toast.makeText(Board.this, "리치 완료", Toast.LENGTH_LONG).show();
                            firstbtn = -1;
                            buttonUpdate();
                            }
                        }
                    }
                    else{
                        // 유국일 때 처리할 것들
                        int tennum = findtenpai();
                        switch(tennum){
                            case 4:
                                break;
                            case 3:
                                for(int i = 0; i < 4; i++){
                                    if(tenpai[i]) score[i] += 1000;
                                    else score[i] -= 3000;
                                }
                                break;
                            case 2:
                                for(int i = 0; i < 4; i++){
                                    if(tenpai[i]) score[i] += 1500;
                                    else score[i] -= 1500;
                                }
                                break;
                            case 1:
                                for(int i = 0; i < 4; i++){
                                    if(tenpai[i]) score[i] += 3000;
                                    else score[i] -= 1000;
                                }
                                break;
                        }
                        if(!tenpai[round % 4]) round++;
                        extend++;
                        Arrays.fill(tenpai, false);
                        drawstatus = false;
                        textUpdate();
                        buttonUpdate();
                    }
                    break;
                case R.id.foul:
                    if (firstbtn == -1)
                        Toast.makeText(Board.this, "쵼보인 사람을 선택한 후 이 버튼을 다시 터치하십시오.", Toast.LENGTH_LONG).show();
                    else {
                        chonbo(firstbtn);
                    }
                    break;
                case R.id.btn_extend:
                    drawstatus = true;
                    Toast.makeText(Board.this, "텐파이인 사람을 모두 선택한 후 중앙을 터치하십시오.", Toast.LENGTH_LONG).show();
                    buttonUpdate();
                    break;
                case R.id.btn_cancel:
                    cancel();
                    break;
            }
        }
    };

    protected int findtenpai(){
        int tenpainum = 0;
        for(int i = 0; i < 4; i++){
            if(tenpai[i]) tenpainum++;
        }
        return tenpainum;
    }

    private void showDialog() {
        LayoutInflater dialog = LayoutInflater.from(this);
        final View dialoglayout = dialog.inflate(R.layout.score, null);
        final Dialog call = new Dialog(this);

        call.setContentView(dialoglayout);
        Spinner span = (Spinner) call.findViewById(R.id.pan);
        Spinner sboo = (Spinner) call.findViewById(R.id.boo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pan_s, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.boo_s, android.R.layout.simple_spinner_item);
        span.setAdapter(adapter);
        sboo.setAdapter(adapter2);
        Button ok = (Button) call.findViewById(R.id.btn_ok);
        Button cancel_dialog = (Button) call.findViewById(R.id.btn_cancel2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner span = (Spinner) call.findViewById(R.id.pan);
                Spinner sboo = (Spinner) call.findViewById(R.id.boo);

                switch (span.getSelectedItem().toString()) {
                    case "1":
                        pan = 1;
                        break;
                    case "2":
                        pan = 2;
                        break;
                    case "3":
                        pan = 3;
                        break;
                    case "4":
                        pan = 4;
                        break;
                    case "滿貫":
                        pan = 5;
                        break;
                    case "跳満":
                        pan = 6;
                        break;
                    case "倍滿":
                        pan = 7;
                        break;
                    case "三倍滿":
                        pan = 8;
                        break;
                    case "役滿":
                        pan = 9;
                        break;
                    case "ダブル役滿":
                        pan = 10;
                        break;
                    case "トリプル役滿":
                        pan = 11;
                        break;
                    case "クワッドラッフル役滿":
                        pan = 12;
                        break;
                }

                boo = Integer.parseInt(sboo.getSelectedItem().toString());

                if (firstbtn == secondbtn) {
                    tsumo(firstbtn);
                }
                else{
                    ron(firstbtn, secondbtn);
                }

                call.dismiss();
            }
        });
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.cancel();
            }
        });
        call.show();
    }

    private void tsumo(int wind) {
        if (wind == round % 4) {
            if (pan >= 1 && pan <= 4) {
                if (boo == 25) {
                    pan--;
                    boo = 50;
                }
                jackpot = (int) ((Math.ceil((boo * Math.pow(2, pan+2) * 2) / 100)) * 100);
                Toast.makeText(Board.this, String.valueOf(jackpot), Toast.LENGTH_SHORT).show();
                if (jackpot >= 4000) {
                    jackpot = 4000;
                }

                jackpot += 100 * extend;
            } else if (pan == 5) {
                jackpot = 4000 + (100 * extend);
            } else if (pan == 6) {
                jackpot = 6000 + (100 * extend);
            } else if (pan == 7) {
                jackpot = 8000 + (100 * extend);
            } else if (pan == 8) {
                jackpot = 12000 + (100 * extend);
            } else if (pan == 9) {
                jackpot = 16000 + (100 * extend);
            } else if (pan == 10) {
                jackpot = 32000 + (100 * extend);
            } else if (pan == 11) {
                jackpot = 48000 + (100 * extend);
            } else if (pan == 12) {
                jackpot = 64000 + (100 * extend);
            }
        } else {
            if (pan >= 1 && pan <= 4) {
                if (boo == 25) {
                    pan--;
                    boo = 50;
                }
                jackpot = (int) ((Math.ceil((boo * Math.pow(2, pan+2) * 1) / 100)) * 100);
                oyajackpot = (int) ((Math.ceil((boo * Math.pow(2, pan+2) * 2) / 100)) * 100);
                if (oyajackpot >= 4000) {
                    jackpot = 2000;
                    oyajackpot = 4000;
                }

                jackpot += 100 * extend;
            } else if (pan == 5) {
                jackpot = 2000 + (100 * extend);
                oyajackpot = 4000 + (100 * extend);
            } else if (pan == 6) {
                jackpot = 3000 + (100 * extend);
                oyajackpot = 6000 + (100 * extend);
            } else if (pan == 7) {
                jackpot = 4000 + (100 * extend);
                oyajackpot = 8000 + (100 * extend);
            } else if (pan == 8) {
                jackpot = 6000 + (100 * extend);
                oyajackpot = 12000 + (100 * extend);
            } else if (pan == 9) {
                jackpot = 8000 + (100 * extend);
                oyajackpot = 16000 + (100 * extend);
            } else if (pan == 10) {
                jackpot = 16000 + (100 * extend);
                oyajackpot = 32000 + (100 * extend);
            } else if (pan == 11) {
                jackpot = 24000 + (100 * extend);
                oyajackpot = 48000 + (100 * extend);
            } else if (pan == 12) {
                jackpot = 32000 + (100 * extend);
                oyajackpot = 64000 + (100 * extend);
            }
        }

        for(int i = 0; i < 4; i++){
            score[wind] += vault;
            vault = 0;
            if(i==wind){
                continue;
            }
            else{
                if(i == round % 4){
                    score[i] -= oyajackpot;
                    score[wind] += oyajackpot;
                }
                else {
                    score[i] -= jackpot;
                    score[wind] += jackpot;
                }
            }
        }

        jackpot = 0;
        oyajackpot = 0;

        if(wind == round % 4){
            extend++;
        }
        else{
            extend = 0;
            round++;
        }
        firstbtn = -1;
        Arrays.fill(tenpai,false);
        textUpdate();
        buttonUpdate();
    }

    private void ron(int wind, int loser) {
        if (wind == round % 4) {
            if (pan >= 1 && pan <= 4) {
                if (boo == 25) {
                    pan--;
                    boo = 50;
                }
                jackpot = (int) ((Math.ceil((boo * Math.pow(2, pan+2) * 6) / 100)) * 100);
                Toast.makeText(Board.this, String.valueOf(jackpot), Toast.LENGTH_SHORT).show();
                if (jackpot >= 12000) {
                    jackpot = 12000;
                }

                jackpot += 300 * extend;
            } else if (pan == 5) {
                jackpot = 12000 + (300 * extend);
            } else if (pan == 6) {
                jackpot = 18000 + (300 * extend);
            } else if (pan == 7) {
                jackpot = 24000 + (300 * extend);
            } else if (pan == 8) {
                jackpot = 36000 + (300 * extend);
            } else if (pan == 9) {
                jackpot = 48000 + (300 * extend);
            } else if (pan == 10) {
                jackpot = 96000 + (300 * extend);
            } else if (pan == 11) {
                jackpot = 144000 + (300 * extend);
            } else if (pan == 12) {
                jackpot = 192000 + (300 * extend);
            }
        } else {
            if (pan >= 1 && pan <= 4) {
                if (boo == 25) {
                    pan--;
                    boo = 50;
                }
                jackpot = (int) ((Math.ceil((boo * Math.pow(2, pan+2) * 4) / 100)) * 100);
                Toast.makeText(Board.this, String.valueOf(jackpot), Toast.LENGTH_SHORT).show();
                if (jackpot >= 8000) {
                    jackpot = 8000;
                }

                jackpot += 300 * extend;
            } else if (pan == 5) {
                jackpot = 8000 + (300 * extend);
            } else if (pan == 6) {
                jackpot = 12000 + (300 * extend);
            } else if (pan == 7) {
                jackpot = 16000 + (300 * extend);
            } else if (pan == 8) {
                jackpot = 24000 + (300 * extend);
            } else if (pan == 9) {
                jackpot = 32000 + (300 * extend);
            } else if (pan == 10) {
                jackpot = 64000 + (300 * extend);
            } else if (pan == 11) {
                jackpot = 96000 + (300 * extend);
            } else if (pan == 12) {
                jackpot = 128000 + (300 * extend);
            }
        }

        score[wind] += vault;
        vault = 0;

        score[loser] -= jackpot;
        score[wind] +=  jackpot;
        jackpot = 0;

        if(wind == round % 4){
            extend++;
        }
        else{
            extend = 0;
            round++;
        }
        firstbtn = -1;
        Arrays.fill(tenpai,false);
        textUpdate();
        buttonUpdate();
    }

    private void chonbo(int wind) {
        if (wind == round % 4) {
                jackpot = 4000;
        } else {
                jackpot = 2000;
                oyajackpot = 4000;
        }

        for(int i = 0; i < 4; i++){
            if(tenpai[i]){
                vault -= 1000;
                score[i] += 1000;
            }
            if(i==wind){
                continue;
            }
            else{
                if(i == round % 4){
                    score[wind] -= oyajackpot;
                    score[i] += oyajackpot;
                }
                else {
                    score[wind] -= jackpot;
                    score[i] += jackpot;
                }
            }
        }

        jackpot = 0;
        oyajackpot = 0;

        firstbtn = -1;
        Arrays.fill(tenpai,false);
        textUpdate();
        buttonUpdate();
    }

    private void cancel(){
        drawstatus = false;

        firstbtn = -1;
        textUpdate();
        buttonUpdate();
    }
}