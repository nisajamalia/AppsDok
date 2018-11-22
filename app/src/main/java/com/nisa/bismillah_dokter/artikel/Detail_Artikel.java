package com.nisa.bismillah_dokter.artikel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.nisa.bismillah_dokter.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Detail_Artikel extends AppCompatActivity {
    TextView txtjudul,txtisi;
    ImageView img;
    AQuery aq;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__artikel);

        Intent a = getIntent();
        id = a.getStringExtra("id");
        getDetailBerita();

    }

    private void getDetailBerita() {

        String url = "http://192.168.43.34/Dotcher/detail_artikel.php";
        HashMap<String,String> params = new HashMap<>();
        params.put("id_artikel", id);
        Toast.makeText(getApplicationContext(), ""+ url +","+ params, Toast.LENGTH_SHORT).show();
        ProgressDialog pdialog = new ProgressDialog(Detail_Artikel.this);
        pdialog.setMessage("Loading.... Harap Bersabar");
        aq = new AQuery(Detail_Artikel.this);
        aq.progress(pdialog).ajax(url, params, String.class, new AjaxCallback<String>(){
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                //jika datanya ada
                if (object != null) {
                    Log.d("Respon Detail:", object);
                    try {
                        JSONObject jsonobject = new JSONObject(object);
                        String result = jsonobject.getString("pesan");
                        String sukses = jsonobject.getString("sukses");
                        if (sukses.equalsIgnoreCase("true")) {
                            JSONArray jarray = jsonobject.getJSONArray("artikel");
                            JSONObject job = jarray.getJSONObject(0);
                            String jdl = job.getString("judul");
                            String gambar = job.getString("gmbr");
                            String isi_artikel = job.getString("isi");
                            ActionGet(jdl,gambar,isi_artikel);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    private void ActionGet(final String jdl, String gambar, final String isi_artikel) {
        txtjudul = (TextView) findViewById(R.id.textD);
        img = (ImageView) findViewById(R.id.imgart);
        txtisi = (TextView) findViewById(R.id.textIsi);
        txtjudul.setText(jdl);
        txtisi.setText(isi_artikel);
        Picasso.with(Detail_Artikel.this).load("http://192.168.43.34/Dotcher/foto_do/"+gambar).error(R.drawable.dokteunik).into(img);


        final Button BtnS = (Button) findViewById(R.id.share);





        BtnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = isi_artikel;
                String shareSub = jdl;

                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(myIntent, "Share with"));
            }
        });

    }



}

