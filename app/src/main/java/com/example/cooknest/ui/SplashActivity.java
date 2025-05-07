package com.example.cooknest.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.cooknest.R;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Typewriter Effect for Welcome Message
        TextView tvWelcomeMsg = findViewById(R.id.tvWelcomeMsg);
        String welcomeText = "Don't know how to cook?\nI got you. Don't worry!";
        final int delay = 50;
        final Handler handler = new Handler();
        final int[] index = {0};

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index[0] <= welcomeText.length()) {
                    tvWelcomeMsg.setText(welcomeText.substring(0, index[0]));
                    index[0]++;
                    handler.postDelayed(this, delay);
                }
            }
        }, 1000);

        TextView appNameText = findViewById(R.id.appName);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(appNameText, View.ALPHA, 0f, 1f);
        fadeIn.setDuration(2000); // 2 seconds
        fadeIn.setStartDelay(500); // delay the start by 500ms for looks
        fadeIn.start();
        final LottieAnimationView lottieAnimationView = findViewById(R.id.splashAnimation);
        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }
}
