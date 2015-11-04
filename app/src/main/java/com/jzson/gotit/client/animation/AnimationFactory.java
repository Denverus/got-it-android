package com.jzson.gotit.client.animation;

import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * Created by Denis on 11/3/2015.
 */
public class AnimationFactory {

    // To animate view slide out from top to bottom
    public static void slideToBottom(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }

    // To animate view slide out from bottom to top
    public static void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,-view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
}
