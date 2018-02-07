package anyan.com.baseframe.ui.customWeight;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import anyan.com.baseframe.R;

/**
 * @author anyanyan
 * @date 2018-2-7
 */


public class BubbleView extends View implements ValueAnimator.AnimatorUpdateListener {
    private int width;
    private int height;
    private int waveColor1;//后面波浪线颜色
    private int waveColor2;//前面波浪线颜色
    private Path wavePath1;//后面波浪线路径
    private Path wavePath2;//前面波浪线路径
    private Paint paint;//画笔
    private int textSize;
    private Path mPath;//M字符的路径
    private int mWith;//M的线条宽度

    private int waveRadius;
    private int waveHeight = 15;
    private int baseLine;
    private int waveNum = 5;//一屏显示的完整波封数
    private int offWave;

    private static final int NUM_SNOWFLAKES = 30;

    private SnowFlake[] snowflakes;

    public BubbleView(Context context) {
        super(context);
        init(context, null);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    //初始化自定义属性等
    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BubbleView);
        waveColor1 = typedArray.getColor(R.styleable.BubbleView_waveColor1, Color.GREEN);
        waveColor2 = typedArray.getColor(R.styleable.BubbleView_waveColor2, Color.BLUE);
        textSize = typedArray.getDimensionPixelSize(R.styleable.BubbleView_textSize,sp2px(context,30));
        typedArray.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wavePath1 = new Path();
        wavePath2 = new Path();
        mPath = new Path();
        mWith = dp2px(context, 30f);
    }

    //设置最小宽高为200dp
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        paint.setTextSize(textSize);
//        if (widthMode != MeasureSpec.EXACTLY) {
//            width = Math.min(width, dp2px(getContext(), 200));
//        }
//        if (heightMode != MeasureSpec.EXACTLY) {
//            height = Math.min(height, dp2px(getContext(), 200));
//        }

        width = (int) paint.measureText("M");
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        height = (int) (fontMetrics.descent  - fontMetrics.ascent);
        this.width = width;
        this.height = height;
        baseLine = this.height - waveHeight;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, this.width * 2 / waveNum);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(this);
        valueAnimator.start();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        evaluteMPath();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("M",0,height,paint);
        paint.getTextPath("M",0,1,0.0f, 0.0f,mPath);
        paint.reset();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2px(getContext(), 3));
        canvas.drawPath(mPath, paint);
//        canvas.save();
//        //画波浪和气泡
//        canvas.clipPath(mPath);
//        drawWave(canvas);
//        drawWave2(canvas);
//        for (SnowFlake snowFlake : snowflakes) {
//            snowFlake.draw(canvas);
//        }
//        canvas.restore();
    }

    protected void resize(int width, int height) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(waveColor1);
        paint.setStyle(Paint.Style.FILL);
        snowflakes = new SnowFlake[NUM_SNOWFLAKES];
        for (int i = 0; i < NUM_SNOWFLAKES; i++) {
            snowflakes[i] = SnowFlake.create(width, height - waveHeight, height + waveHeight, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            resize(w, h);
        }
    }

    private void drawWave(Canvas canvas) {
        waveRadius = width / waveNum / 2;
        wavePath1.reset();
        int startX = -(waveNum + 1) * waveRadius * 2;//手机屏幕左侧
        wavePath1.moveTo(startX, baseLine);
        for (int i = -(waveNum + 1); i < waveNum + 1; i++) {
            int controlY = i % 2 == 0 ? baseLine - waveHeight : baseLine + waveHeight;
            int curStartX = i * 2 * waveRadius;
            wavePath1.quadTo(curStartX + waveRadius + offWave, controlY, curStartX + waveRadius * 2 + offWave, baseLine);
        }
        wavePath1.lineTo(width, height);
        wavePath1.lineTo(0, height);
        wavePath1.close();
        paint.setColor(waveColor1);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(wavePath1, paint);
    }

    private void drawWave2(Canvas canvas) {
        waveRadius = width / waveNum / 2;
        wavePath2.reset();
        int startX = -(waveNum + 1) * waveRadius * 2;//手机屏幕左侧
        wavePath2.moveTo(startX, baseLine);
        for (int i = -(waveNum + 1); i < waveNum + 1; i++) {
            int controlY = i % 2 == 0 ? baseLine - waveHeight : baseLine + waveHeight;
            int curStartX = i * 2 * waveRadius;
            wavePath2.quadTo(curStartX + waveRadius + offWave+waveRadius, controlY, curStartX + waveRadius * 2 + offWave+waveRadius, baseLine);
        }
        wavePath2.lineTo(width, height);
        wavePath2.lineTo(0, height);
        wavePath2.close();
        paint.setColor(waveColor2);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(wavePath2, paint);
    }

    //计算M路径
    private void evaluteMPath() {
//        mPath.reset();
//        int centerX = width / 2;
//        int centerY = height / 2;
//        double angle = Math.atan(width*1.0/4/height);
//        int h = (int) (mWith/Math.sin(angle));
//        int y = (int) (mWith/Math.cos(angle));
////        int h = 4 * height * mWith / width;
//        mPath.moveTo(centerX, centerY);
//        mPath.lineTo(centerX + width / 4, 0);
//        mPath.lineTo(width, height);
//        mPath.lineTo(width - y, height);
//        mPath.lineTo(centerX + width / 4, h);
//        mPath.lineTo(centerX,centerY+h);
//        mPath.lineTo(width / 4, h);
//        mPath.lineTo(y, height);
//        mPath.lineTo(0, height);
//        mPath.lineTo(width / 4, 0);
//        mPath.close();
    }

    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int offset = (int) animation.getAnimatedValue();
        this.offWave = offset;
        invalidate();
    }
}
