package com.nisa.bismillah_dokter.tab_layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.nisa.bismillah_dokter.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 5/14/18.
 */

public class spesialis_anak extends Fragment {

    ListView list;
    ArrayList<HashMap<String, String>> data;//array list itu menyimpan banyak data dalam bentuk list
    AQuery aq;//aquery itu android query yang berhubungan dengan database yang menghubungkan database dengan android studio
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View V = inflater.inflate(R.layout.activity_spesialis_anak, null);
        data = new ArrayList<HashMap<String, String>>();//ini buat menyimpan data di hasmap
        AmbilData();
        list = (ListView) V.findViewById(R.id.listView);//memanggil list

        return V;
    }

    private void AmbilData() {
        String url = "http://192.168.43.34/Dotcher/dokter.php";//string url itu untuk memanggil php nya

        aq = new AQuery(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....Harap Tunggu");//sampai sini
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        aq.progress(progressDialog).ajax(url, String.class, new AjaxCallback<String>() {

            public void callback(String url, String object, AjaxStatus status) {
                if (object != null) {
                    Log.d("Respon", object);
                    try { /// mengecek data

                        JSONObject json = new JSONObject(object);
                        String result = json.getString("pesan");
                        String Sukses = json.getString("sukses");
                        // jika datangnya sukses
                        if (Sukses.equalsIgnoreCase("true")) {
                            JSONArray jsonArray = json.getJSONArray("nama_dokter");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //ambil attribut masing masing item
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nama = jsonObject.getString("namdok");
                                String idok = jsonObject.getString("id_dokter");
                                String gambar = jsonObject.getString("gamdok");
                                String telpon = jsonObject.getString("tlp");




                                //diletakan di hasmap
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("namdok", nama);
                                map.put("id_dokter", idok);
                                map.put("gamdok", gambar);
                                map.put("tlp", telpon);

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
        CustomAdapter adapter = new CustomAdapter(getContext(), data);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> map = data.get(i);
                Intent a = new Intent(getActivity(), DETAIL.class);
                a.putExtra("id_dokter", map.get("id_dokter"));
                a.putExtra("tlp", map.get("tlp"));

                Toast.makeText(getActivity(), "" + map.get("id_dokter"), Toast.LENGTH_SHORT).show();
                startActivity(a);
            }
        });
    }

    private class CustomAdapter extends BaseAdapter {
        Context activity;
        ArrayList<HashMap<String, String>> data2;
        LayoutInflater layoutInflater;

        public CustomAdapter(Context mainActivity,
                             ArrayList<HashMap<String, String>> data2) {
            this.activity = mainActivity;
            this.data2 = data;//tadinya = data,terus nisa ganti jadi data2

        }

        @Override
        public int getCount() {
            return data2.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(R.layout.activity_list_item, null);
            TextView jdl = (TextView) v.findViewById(R.id.textjudul);
            TextView id = (TextView) v.findViewById(R.id.idresep);
            ImageView img = (ImageView) v.findViewById(R.id.imagelist);

            HashMap<String, String> data = new HashMap<String, String>();
            data = data2.get(position);

            jdl.setText(data.get("namdok"));
            id.setText(data.get("id_dokter"));
            String url_foto = "http://192.168.43.34/Dotcher/foto_do/";
            Picasso.with(getActivity()).load(url_foto + data.get("gamdok")).error(R.drawable.animasi_muslimah).into(img);

            return v;


        }
    }
}
