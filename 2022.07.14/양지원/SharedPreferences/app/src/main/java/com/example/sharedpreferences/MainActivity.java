package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //체크박스를 체크했는지 유무 불러온다.
        ShowCheck shared = new ShowCheck();
        Boolean flag = shared.getFirstLogin(this);

        //체크하지 않았으면 다이얼로그를 실행한다.
        if(!flag){
            AlertDialog.Builder builder = new AlertDialog.Builder (this);
            View view = LayoutInflater.from (this).inflate (R.layout.dialog, null, false);
            builder.setView (view);
            final AlertDialog dialog = builder.create ();
            final CheckBox cb = view.findViewById (R.id.checkBox);
            final Button button = view.findViewById (R.id.btn_close);

            button.setOnClickListener (v -> {
                //체크박스 체크했으면 true 를 저장한다.
                if (cb.isChecked ()) {
                    shared.setFirstLogin (this, cb.isChecked ());
                }
                dialog.dismiss ();
            });
            dialog.show ();
        }
    }
}