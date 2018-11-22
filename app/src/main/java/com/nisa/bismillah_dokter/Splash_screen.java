 package com.nisa.bismillah_dokter;

 import android.graphics.drawable.AnimationDrawable;
 import android.os.Bundle;
 import android.support.v7.app.AppCompatActivity;
 import android.widget.ProgressBar;

 import com.felipecsl.gifimageview.library.GifImageView;
 import com.nisa.bismillah_dokter.sign.sessionmanager;

 public class Splash_screen extends AppCompatActivity {

     //     RelativeLayout splash;
     private final int SPLASH_DISPLAY_LENGTH = 3000;
     private GifImageView gifImageView;
     AnimationDrawable animationDrawable;

     ProgressBar progressBar;

     sessionmanager sessionmanager;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_splash_screen);

         sessionmanager = new sessionmanager(getApplicationContext());

         progressBar = (ProgressBar) findViewById(R.id.dt);
         new Thread(new Runnable() {
             @Override
             public void run() {
                 for (int progress = 0; progress < 100; progress += 10) {
                     try {
                         Thread.sleep(1000);
                         progressBar.setProgress(progress);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }


                 }
                 sessionmanager.checkLogin();

                 finish();

             }
         }).start();


//         gifImageView = (GifImageView) findViewById(R.id.gift);
//
//
//         try {
//             InputStream inputStream = getAssets().open("pinochio.gif");
//             byte[] bytes = IOUtils.toByteArray(inputStream);
//             gifImageView.setBytes(bytes);
//             gifImageView.startAnimation();
//
//
//         } catch (IOException ex){
//
//
//

     }
 }
//         new Handler().postDelayed(new Runnable() {
//             @Override
//             public void run() {
//                 Splash_screen.this.startActivity(new Intent(Splash_screen.this,Sign_in.class));
//                 Splash_screen.this.finish();
//             }
//
//         },7000);









// ini adalah script membuat tampulan menjadi warna combine/graduen
 //dan di drawable juga di buat layout baru itu untuk graduent warna
//        splash = (RelativeLayout) findViewById(R.id.splash);
//        animationDrawable = (AnimationDrawable) splash.getBackground();
//        animationDrawable.setEnterFadeDuration(4500);
//        animationDrawable.setExitFadeDuration(4500);
//        animationDrawable.start();


//        progressBar = (ProgressBar) findViewById(R.id.dt);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int progress = 0; progress<100; progress+= 10){
//                    try {
//                        Thread.sleep(1000);
//                        progressBar.setProgress(progress);
//                    } catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                }
//                Intent f = new Intent(getApplicationContext(), Log_in.class);
//                startActivity(f);
//                finish();
//            }
//        }).start();

