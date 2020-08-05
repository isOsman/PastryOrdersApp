package com.example.osman.orders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 1000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);


        drawTextView();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,TopLevelActivity.class));
                finish();
            }
        },SPLASH_TIME_OUT);
    }

    private void drawTextView(){
        TextView textView = (TextView) findViewById(R.id.partnerText);
        TextPaint paint = textView.getPaint();
        float width = paint.measureText("Совместо с @SHIRIN_CAKES_");

        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#FDB54E"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);
    }

}
