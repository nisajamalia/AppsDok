package com.nisa.bismillah_dokter.tab_layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class DETAIL extends AppCompatActivity {
    TextView txtnama;
    TextView txtalamat;
    TextView txtumur;
    TextView kemampuan;
    AQuery aq;
    String hp;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent a = getIntent();
        id = a.getStringExtra("id_dokter");
        hp = a.getStringExtra("tlp");

        getDetailBerita();

    }

    private void getDetailBerita() {
        String url = "http://192.168.43.34/Dotcher/detail_dokter.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);//yg didalam tanda petik diambil dari id  didatabase
        Toast.makeText(getApplicationContext(), "" + url + "," + params, Toast.LENGTH_SHORT).show();
        ProgressDialog pdialog = new ProgressDialog(DETAIL.this);
        pdialog.setMessage("Loading.... Harap Bersabar");
        aq = new AQuery(DETAIL.this);
        aq.progress(pdialog).ajax(url, params, String.class, new AjaxCallback<String>() {
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
                            JSONArray jarray = jsonobject.getJSONArray("nama_dokter");
                            JSONObject job = jarray.getJSONObject(0);
                            String nama = job.getString("namdok");
                            String gambar = job.getString("gamdok");
                            String umur = job.getString("umdok");
                            String alamat = job.getString("alm");
                            String mampu = job.getString("kmp");
                            ActionGet(nama, gambar, umur, alamat, mampu);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    private void ActionGet(String nama, String gambar, String umur, String alamat, String mampu) {
        txtnama = (TextView) findViewById(R.id.name);
        txtalamat = (TextView) findViewById(R.id.alm);
        txtumur = (TextView) findViewById(R.id.umr);
        kemampuan = (TextView) findViewById(R.id.km);


        Button btnsms = (Button) findViewById(R.id.sms);
        btnsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // kalau ini script untuk sms

                //script 1
//                String number = hp;  // The number on which you want to send SMS
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("SMS", number, null)));

                //script 2
                Uri uri = Uri.parse("smsto:"+hp);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                it.putExtra("sms_body", "Halo Dokter , Saya Ingin Berkonsultasi");
                startActivity(it);

                //script 3
//                Uri smsUri = Uri.parse("tel:"+hp);
//                Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
//                intent.putExtra("sms_body", "sms text");
//                intent.setType("vnd.android-dir/mms-sms");
//                startActivity(intent);

            }

        });


        Button btnTelp = (Button) findViewById(R.id.tlp);
        btnTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telpon = null, gantiForm = null;
                gantiForm = new Intent(Intent.ACTION_DIAL);
                gantiForm.setData(Uri.parse("tel:" + hp));
                telpon = Intent.createChooser(gantiForm, "Telepon Kami");
                startActivity(telpon);

            }

        });

        CircleImageView img = (CircleImageView) findViewById(R.id.imageC);


        txtnama.setText(nama);
        txtalamat.setText(alamat);
        txtumur.setText(umur);
        kemampuan.setText(mampu);


        Picasso.with(DETAIL.this).load("http://192.168.43.34/Dotcher/foto_do/" + gambar).error(R.drawable.lope).into(img);
        return;
    }
    }
