package com.nisa.bismillah_dokter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nisa.bismillah_dokter.sign.sessionmanager;

import java.util.HashMap;

import static com.nisa.bismillah_dokter.R.id.txtemail;

public class profile extends AppCompatActivity {

    ImageView imglogout;
    sessionmanager sessionmanager;
    TextView email, password , name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionmanager = new sessionmanager(getApplicationContext());

        email = (TextView) findViewById(txtemail);
        password = (TextView) findViewById(R.id.txtpass);
        imglogout = (ImageView) findViewById(R.id.out);


        sessionmanager = new sessionmanager(getApplicationContext());
        HashMap<String, String> user = sessionmanager.getUserData();
        String email1 = user.get(sessionmanager.kunci_email);
        String password1 = user.get(sessionmanager.kunci_password);
        //nama.setText(sessionmanager.getUserData().get("nama"));
        email.setText(Html.fromHtml("<b>"+ email1 + "<b>"));
        password.setText(Html.fromHtml("<b>"+ password1 + "<b>"));

        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionmanager.logout();
                finish();
            }
        });


    }
}
