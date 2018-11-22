package com.nisa.bismillah_dokter.sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.nisa.bismillah_dokter.MainActivity;
import com.nisa.bismillah_dokter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Sign_in extends AppCompatActivity {
    private EditText regEmail, Password;
    private Button Signin;
    private TextView Txtsignup;

    AQuery aQuery;

    sessionmanager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sessionManager = new sessionmanager(getApplicationContext());

        setView();

    }

    private void setView() {
        regEmail = (EditText) findViewById(R.id.regemail);
        Password = (EditText) findViewById(R.id.password);
        Signin = (Button) findViewById(R.id.signin);
        Txtsignup = (TextView) findViewById(R.id.txtsignup);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aQuery = new AQuery(Sign_in.this);
                loginUser();
            }
        });

        Txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sign_up.class));
            }
        });
    }

    private void loginUser() {
        regEmail.setError(null);
        Password.setError(null);

        //cek inputan
        if (Helper.isEmpty(regEmail)) {
            regEmail.setError("Email Tidak Boleh Kosong");
            regEmail.requestFocus();
        } else if (!Helper.isEmailValid(regEmail)) {
            regEmail.setError("Format Email Salah");
            regEmail.requestFocus();
        } else if (Helper.isEmpty(Password)) {
            Password.setError("Password Tidak Boleh Kosong");
            Password.requestFocus();
        } else {
            //proses login
            //Dotcher dari foldernya di htdocs
            //Dotcher sign_in dari nama phpnya
            //login dari nama function
            String url = "http://192.168.43.34/Dotcher/index.php/Dotcher_Sign_In/login";

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("email", regEmail.getText().toString());
            params.put("password", Password.getText().toString());

            //Toast.makeText(this, "" + regEmail.getText().toString() + Password.getText().toString(), Toast.LENGTH_SHORT).show();
            ProgressDialog progress = new ProgressDialog(Sign_in.this);
            progress.setMessage("Login. . .");
            progress.setCancelable(false);
            progress.setIndeterminate(false);

            Toast.makeText(this, ""+params, Toast.LENGTH_SHORT).show();

            aQuery.progress(progress).ajax(url, params, String.class, new AjaxCallback<String>() {
                @Override
                public void callback(String url, String object, AjaxStatus status) {
                    Toast.makeText(Sign_in.this, ""+object, Toast.LENGTH_SHORT).show();
                        if (object != null) {

                            Toast.makeText(Sign_in.this, "data ditemukan", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject json = new JSONObject(object);
                                String hasil = json.getString("result");
                                String pesan = json.getString("pesan");

                                if (hasil.equalsIgnoreCase("true")) {
                                    //buat session
                                    JSONArray jsonArray = json.getJSONArray("Sign_Up");

                                    for (int a = 0; a < jsonArray.length(); a++) {
                                        JSONObject object1 = jsonArray.getJSONObject(a);
                                        sessionManager.createSession(object1.getString("email"), object1.getString("password"));
                                    }

                                    Toast.makeText(Sign_in.this, pesan, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(Sign_in.this, pesan, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Sign_in.this, "gagal mengambil json", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Sign_in.this, "data tidak ada", Toast.LENGTH_SHORT).show();
                        }

                }

            });
        }
    }

}
