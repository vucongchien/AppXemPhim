package com.example.appxemphim.UI.Utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class PageTransformer implements ViewPager2.PageTransformer{
    private static final float MIN_SCALE=0.85F;
    private static final float MIN_ALPHA=0.5f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth= page.getWidth();
        int pageHeight=page.getHeight();

        if (position<-1){
            page.setAlpha(0f);
        } else if (position<=1) {
            float scaleFactor=Math.max(MIN_SCALE,1-Math.abs(position));
            float vertMargin=pageHeight*(1-scaleFactor)/2;
            float horzMargin=pageWidth*(1-scaleFactor)/2;

            if (position<0){
                page.setTranslationX(horzMargin-vertMargin/2);
            }else {
                page.setTranslationX(-horzMargin+vertMargin/2);
            }

            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            page.setAlpha(MIN_SCALE+(scaleFactor-MIN_SCALE)/(1-MIN_SCALE)*(1-MIN_ALPHA));
        }else {
            page.setAlpha(0f);
        }

    }
}
