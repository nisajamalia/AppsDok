package com.nisa.bismillah_dokter.sign;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 5/3/18.
 */

public class Helper {
    public static boolean isEmailValid(EditText email){

        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email.getText().toString();
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        //jika not false
        if (matcher.matches()){
            //jika valid maka tidak akan dikembalikan
            isValid = true;
        }
        return isValid;
    }

    //method untuk mengecek inputan ada atau tidak
    public static boolean isEmpty(EditText text){
        //jika text tidak kosong
        if (text.getText().toString().trim().length() > 0){
            //tidak dikembalikan
            return false;
        } else {
            return true;
        }
    }

    //method untuk mengecek kesamaan inputan password
    public static boolean isCompare(EditText text1, EditText text2){
        String a = text1.getText().toString();
        String b = text2.getText().toString();
        //jika variabel a dan b sama persis
        if (a.equals(b)){
            //tidak dikembalikan
            return false;
        } else {
            return true;
        }
    }

}


