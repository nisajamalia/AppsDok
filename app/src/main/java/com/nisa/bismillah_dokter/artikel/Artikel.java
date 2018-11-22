package com.nisa.bismillah_dokter.artikel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.nisa.bismillah_dokter.MainActivity;
import com.nisa.bismillah_dokter.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Artikel extends AppCompatActivity {
    ListView list;
    ArrayList<HashMap<String,String>> data;
    AQuery aq;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);
        data = new ArrayList<HashMap<String, String>>();
        AmbilData();
        list = (ListView) findViewById(R.id.listArtikel);

    }

    private void AmbilData() {
        String url = "http://192.168.43.34/Dotcher/artikel.php";

        aq = new AQuery(Artikel.this);
        progressDialog = new ProgressDialog(Artikel.this);
        progressDialog.setMessage("Loading....Harap Tunggu");//sampai sini
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        aq.progress(progressDialog).ajax(url, String.class, new AjaxCallback<String>(){
            @Override
            public void callback(String url, String object, AjaxStatus status) {
                if (object!=null){
                    Log.d("Respon", object);
                    try { /// mengecek data

                        JSONObject json =new  JSONObject(object);
                        String result = json.getString("pesan");
                        String Sukses = json.getString("sukses");
                        // jika datangnya sukses
                        if (Sukses.equalsIgnoreCase("true")) {
                            JSONArray jsonArray = json.getJSONArray("artikel");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //ambil attribut masing masing item
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nama = jsonObject.getString("judul");
                                String id_artikel = jsonObject.getString("id");
                                String gambar = jsonObject.getString("gmbr");
                                String deskripsi = jsonObject.getString("isi");

                                //diletakan di hasmap
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("judul", nama);
                                map.put("id", id_artikel);
                                map.put("gmbr", gambar);
                                map.put("isi", deskripsi);
                                data.add(map);

                                setListAdapter(data);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void setListAdapter(final ArrayList<HashMap<String, String>> data) {

        CustomAdapter adapter = new CustomAdapter(this, data);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String>map = data.get(i);
                Intent a = new Intent(getApplicationContext(), Detail_Artikel.class);
                a.putExtra("id", map.get("id"));
                Toast.makeText(getApplicationContext(), ""+map.get("id"), Toast.LENGTH_SHORT).show();
                startActivity(a);

            }

        });
    }

    private class CustomAdapter extends BaseAdapter {
        Activity activity;
        ArrayList<HashMap<String, String>> data2;
        LayoutInflater layoutInflater;

        public CustomAdapter(Activity artikel,
                             ArrayList<HashMap<String, String>> data2) {
            this. activity = artikel;
            this.data2 = data;//tadinya = data,terus nisa ganti jadi data2

        }

        @Override
        public int getCount() {
            return data2.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(R.layout.listitem_artikel, null);
            TextView jdl = (TextView) v.findViewById(R.id.nama_judul);
            ImageView img = (ImageView) v.findViewById(R.id.itemImg);

            HashMap<String,String> data = new HashMap<String, String>();
            data = data2.get(i);
            jdl.setText(data.get("judul"));
            //img.setImageResource(Integer.parseInt(data.get("gmbr")));
            String url_foto = "http://192.168.43.34/Dotcher/foto_do/";
            Picasso.with(Artikel.this).load(url_foto+data.get("gmbr")).error(R.drawable.photo_camera).into(img);

            return v;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}
