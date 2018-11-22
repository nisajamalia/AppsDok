package com.nisa.bismillah_dokter.sign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.nisa.bismillah_dokter.MainActivity;

import java.util.HashMap;

/**
 * Created by User on 5/3/18.
 */

public class sessionmanager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    //mode share preferences
    int mode = 0;

    //nama dari share preferences
    //static adalah property(variabel) dan method(function) yang melekat pada class
    private static final String pref_name = "crudpref";

    //kunci share preferences
    private static final String is_login = "islogin";
    public static final String kunci_nama = "keynama";
    public static final String kunci_password = "keypassword";
    public static final String kunci_email = "keyemail";


    //constructor
    public sessionmanager(Context context) {
        //mengakses class ini
        this.context = context;
        //share preferences dari kelas ini /*(nama, mode)*/
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    //method membuat session
    public void createSession(String email, String password) {

        //login value menjadi true
        editor.putBoolean(is_login, true);
        //memasukan email kedalam variable kunci email
        editor.putString(kunci_email, email);
        editor.putString(kunci_password, password);
        editor.commit(); //melakukan/mengerjakan
    }

    //method untuk cek login
    public void checkLogin() {
        //jika login false
        if (!this.is_login()) {
            //ga pindah kemana" tetep di loginActivity
            Intent i = new Intent(context, Sign_up.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            //jika true maka akan ke main activity
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }


    public boolean is_login() {
        return pref.getBoolean(is_login, false);
    }


    //method logout
    public void logout() {
        //hapus semua data dan kunci
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, Sign_in.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserData() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(kunci_email, pref.getString(kunci_email, null));
        user.put(kunci_nama, pref.getString(kunci_nama, null));
        user.put(kunci_password, pref.getString(kunci_password, null));

        return user;
    }
}
