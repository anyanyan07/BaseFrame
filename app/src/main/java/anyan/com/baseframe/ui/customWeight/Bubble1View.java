package anyan.com.baseframe.ui.customWeight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * 气泡动画
 *
 * @author anyanyan
 * @date 2018-2-7
 */


public class Bubble1View extends View {
    private Paint paint;
    private int greenColor = Color.GREEN;
    private int blueColor = Color.BLUE;
    private int width, height;
    private int waveRadius;
    private int waveHeight = 30;
    private int baseLine;
    private Path wavePath, wavePath2;
    private int waveNum = 4;//一屏显示的完整波封数


    public Bubble1View(Context context) {
        super(context);
        init();
    }

    public Bubble1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Bubble1View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Bubble1View(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wavePath = new Path();
        wavePath2 = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        baseLine = height - waveHeight;
    }

    public void setOffWave(int offWave) {
        this.offWave = offWave;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画底部波浪
        drawWave(canvas);
        drawBlueWave(canvas);
        //画气泡
        canvas.save();
        Path path = new Path();
        path.addCircle(width/2,height/2,200, Path.Direction.CCW);
        canvas.clipPath(path);
        for (SnowFlake snowFlake : snowflakes) {
            snowFlake.draw(canvas);
        }
        canvas.restore();
        getHandler().postDelayed(runnable, DELAY);
    }

    private int offWave;


    private void drawWave(Canvas canvas) {
        waveRadius = width / waveNum / 2;
        wavePath.reset();
        int startX = -(waveNum + 1) * waveRadius * 2;//手机屏幕左侧
        wavePath.moveTo(startX, baseLine);
        for (int i = -(waveNum+1); i <  waveNum + 1; i++) {
            int controlY = i % 2 == 0 ? baseLine - waveHeight : baseLine + waveHeight;
            int curStartX = i * 2 * waveRadius;
            wavePath.quadTo(curStartX + waveRadius + offWave, controlY, curStartX + waveRadius * 2 + offWave, baseLine);
        }
        wavePath.lineTo(width,height);
        wavePath.lineTo(0, height);
        wavePath.close();
        paint.reset();
        paint.setColor(greenColor);
        canvas.drawPath(wavePath, paint);
    }

    private void drawBlueWave(Canvas canvas) {
        waveRadius = width / waveNum / 2;
        wavePath.reset();
        int startX = -(waveNum + 1) * waveRadius * 2;//手机屏幕左侧
        wavePath.moveTo(startX, baseLine);
        for (int i = -(waveNum+1); i <  waveNum + 1; i++) {
            int controlY = i % 2 == 0 ? baseLine - waveHeight : baseLine + waveHeight;
            int curStartX = i * 2 * waveRadius;
            wavePath.quadTo(curStartX + waveRadius + offWave, controlY, curStartX + waveRadius * 2 + offWave, baseLine);
        }
        wavePath.lineTo(width,height);
        wavePath.lineTo(0, height);
        wavePath.close();
        paint.reset();
        paint.setColor(blueColor);
        canvas.drawPath(wavePath, paint);
    }


    private static final int NUM_SNOWFLAKES = 60;
    private static final int DELAY = 5;

    private SnowFlake[] snowflakes;

    protected void resize(int width, int height) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        snowflakes = new SnowFlake[NUM_SNOWFLAKES];
//        for (int i = 0; i < NUM_SNOWFLAKES; i++) {
//            snowflakes[i] = SnowFlake.create(width, height, paint);
//        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            resize(w, h);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
}
