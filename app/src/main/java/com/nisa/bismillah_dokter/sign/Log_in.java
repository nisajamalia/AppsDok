package com.nisa.bismillah_dokter.sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nisa.bismillah_dokter.R;

public class Log_in extends AppCompatActivity {
    Button btnin , btnout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        btnin = (Button) findViewById(R.id.btnlog);
        btnout = (Button) findViewById(R.id.btnlog2);

        btnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Sign_in.class);
                startActivity(i);
            }
        });
        btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Sign_up.class);
                startActivity(i);

                finish();
            }

        });
    }
}
