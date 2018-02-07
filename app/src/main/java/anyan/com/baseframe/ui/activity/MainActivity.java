package anyan.com.baseframe.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;

import anyan.com.baseframe.R;
import anyan.com.baseframe.ui.customWeight.BubbleView;

public class MainActivity extends AppCompatActivity {
    private BubbleView bubbleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bubbleView = findViewById(R.id.bubbleView);
    }

    public void onClick(View view) {
    }
}
