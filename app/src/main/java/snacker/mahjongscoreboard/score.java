package snacker.mahjongscoreboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Snacker on 2017-09-25.
 */

public class score extends Dialog {

    public NumberPicker sboo;
    public Button ok;
    public Button cancel;
    public NumberPicker pan_p;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("HOO");
        setContentView(R.layout.score);

        //span = (Spinner) findViewById(R.id.pan);
        sboo = (NumberPicker) findViewById(R.id.boo);
        ok =(Button) findViewById(R.id.btn_ok);
        cancel = (Button) findViewById(R.id.btn_cancel2);
        pan_p = (NumberPicker) findViewById(R.id.panp);

        ok.setOnClickListener(mLeftListener);
        cancel.setOnClickListener(mRightListener);
        /*final String[] values= {"1", "2","3","4","滿貫","跳満","倍滿","三倍滿","役滿","ダブル役滿","トリプル役滿","クワッドラッフル役滿"};
        pan_p.setMinValue(0);
        pan_p.setMaxValue(values.length-1);
        pan_p.setDisplayedValues(values);
        pan_p.setWrapSelectorWheel(true);*/
    }

    Button.OnClickListener mLeftListener = new View.OnClickListener(){
        public void onClick(View v){

            dismiss();
        }
    };
    Button.OnClickListener mRightListener = new View.OnClickListener(){
        public void onClick(View v){
            cancel();
        }
    };

    public score(@NonNull Context context) {
        super(context);
    }
}
