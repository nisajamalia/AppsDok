package com.nisa.bismillah_dokter.sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.nisa.bismillah_dokter.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Sign_up extends AppCompatActivity {
    private MaterialEditText ednama, edemail, edpass, edpass2, provinsi, kota, kecamatan;
    private Button SIgnup;
    private TextView Txtsignin;

    AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setView();
    }

    private void setView() {
        ednama = (MaterialEditText) findViewById(R.id.regnama);
        edemail = (MaterialEditText) findViewById(R.id.regemail2);
        edpass = (MaterialEditText) findViewById(R.id.passwordUp);
        edpass2 = (MaterialEditText) findViewById(R.id.confirmpassword);
        provinsi = (MaterialEditText) findViewById(R.id.prov);
        kota = (MaterialEditText) findViewById(R.id.kota);
        kecamatan = (MaterialEditText) findViewById(R.id.kec);
        SIgnup = (Button) findViewById(R.id.buttonSU);
        Txtsignin = (TextView) findViewById(R.id.txtsignin);

        SIgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aQuery = new AQuery(Sign_up.this);
                registerUser();
            }
        });
        Txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sign_in.class));
            }
        });
    }

    private void registerUser() {
        //semua kolom isian belum ada error
        ednama.setError(null);
        edemail.setError(null);
        edpass.setError(null);
        edpass2.setError(null);
        provinsi.setError(null);
        kota.setError(null);
        kecamatan.setError(null);

        //cek inputannya
        if (Helper.isEmpty(edemail)) {
            //jika email masih kosong
            edemail.setError("Email Tidak Boleh Kosong!");
            edemail.requestFocus();
        } else if (!Helper.isEmailValid(edemail)) {
            //jika email tidak valid atau formatnya salah
            edemail.setError("Format Email Salah");
            edemail.requestFocus();

        } else if(Helper.isEmpty(edemail)) {
            //jika tidak sama
            edemail.setError("Email Sudah Ada Yang Pernah Pakai");
            edemail.requestFocus();

        } else if (Helper.isEmpty(ednama)) {
            //jika nama masih kosong
            ednama.setError("Nama Tidak Boleh Kosong!");
            ednama.requestFocus();
        } else if (Helper.isEmpty(edpass)) {
            //jika pass1 masih kosong
            edpass.setError("Password Tidak Boleh Kosong!");
            edpass.requestFocus();

        } else if (Helper.isEmpty(edpass2)) {
            //jika pass2 masih kosong
            edpass2.setError("Password Tidak Boleh Kosong!");
            edpass2.requestFocus();

        } else if (Helper.isCompare(edpass, edpass2)) {
            //jika tidak sama
            edpass2.setError("Password Tidak Sama");
            edpass2.requestFocus();

        } else if (Helper.isEmpty(provinsi)) {
            //jika tidak sama
            provinsi.setError("Harus Diisi");
            provinsi.requestFocus();

        } else if(Helper.isEmpty(kota)) {
            //jika tidak sama
            kota.setError("Tidak Boleh Kosong");
            kota.requestFocus();

        } else if(Helper.isEmpty(kecamatan)) {
            //jika tidak sama
            kecamatan.setError("Tidak Boleh Kosong");
            kecamatan.requestFocus();


        } else{
            //proses login
            //dotcher adalah nama : diambil dari file htdocs nya
            //
            String url = "http://192.168.43.34/Dotcher/index.php/Dotcher_Sign_In/daftar";

            HashMap<String, String> params = new HashMap<String, String>();
            //bawa nilai(kunci, nilai yang akan dikirim)
            params.put("email", edemail.getText().toString());
            params.put("nama", ednama.getText().toString());
            params.put("password", edpass.getText().toString());
            params.put("confirm_password", edpass2.getText().toString());
            params.put("provinsi", provinsi.getText().toString());
            params.put("kota", kota.getText().toString());
            params.put("kecamatan", kecamatan.getText().toString());


            ProgressDialog progress = new ProgressDialog(Sign_up.this);
            progress.setMessage("Register. . .");
            progress.setCancelable(false);
            progress.setIndeterminate(false);


            aQuery.progress(progress).ajax(url,params,String.class, new AjaxCallback<String>() {
                @Override
                public void callback(String url, String object, AjaxStatus status) {
                    if (object != null) {
                        Toast.makeText(Sign_up.this, object, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject json = new JSONObject(object);
                            String hasil = json.getString("result");
                            String pesan = json.getString("pesan");

                            if (hasil.equalsIgnoreCase("true")) {
                                //jika berhasil maka pindah ke LoginActivity
                                Toast.makeText(Sign_up.this, pesan, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Sign_in.class));
                                finish();
                            } else {
                                //jika gagal keluar toast
                                Toast.makeText(Sign_up.this, pesan, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Sign_up.this, "Email Sudah Digunakan", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                }
            });
        }
    }
}
