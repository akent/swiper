package org.akent.swiper;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    final static String TAG = "Swiper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View top = findViewById(R.id.top);
        final View swipe = findViewById(R.id.swipe);
        final GestureDetector gd = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float y = e2.getY() - e1.getY();
                swipe.setTranslationY(y);
                if (swipe.getY() > 0.2 * top.getHeight()) {
                    swipe.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                } else if (swipe.getY() < -0.2 * top.getHeight()) {
                    swipe.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    swipe.setBackgroundColor(getResources().getColor(android.R.color.black));
                }
                return true;
            }
        });
        top.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                gd.onTouchEvent(e);
                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_UP:
                        int swipeOffset = 0;
                        if (swipe.getY() > 0.2 * top.getHeight()) {
                            swipeOffset = top.getHeight();
                        } else if (swipe.getY() < -0.2 * top.getHeight()) {
                            swipeOffset = -top.getHeight();
                        }
                        if (swipeOffset != 0) {
                            swipe.animate().translationY(swipeOffset).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    swipe.setBackgroundColor(getResources().getColor(android.R.color.black));
                                    swipe.animate().translationY(0);
                                }
                            });
                            break;
                        } else {
                            // fall through, cancel
                        }
                    case MotionEvent.ACTION_CANCEL:
                        swipe.animate().translationY(0);
                        break;
                }
                return true;
            }
        });
        Log.d(TAG, "App running");
    }

}
