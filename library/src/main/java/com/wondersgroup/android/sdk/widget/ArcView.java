package com.wondersgroup.android.sdk.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.utils.LogUtil;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:自定义弧形 IView
 */
public class ArcView extends View {

    private static final String TAG = "ArcView";
    /**
     * 整个 View 的宽度
     */
    private int mWidth;
    /**
     * 整个 View 的高度
     */
    private int mHeight;
    /**
     * 弧形高度
     */
    private int mArcHeight;
    /**
     * 背景颜色
     */
    private int mBgColor;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 矩形框
     */
    private Rect mRect;
    /**
     * 绘制的路径
     */
    private Path mPath;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcView_arcHeight, 0);
        mBgColor = typedArray.getColor(R.styleable.ArcView_bgColor, Color.parseColor("#1E90FF"));
        typedArray.recycle();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.i(TAG, "onDraw()");
        // 先绘制一个矩形框
        canvas.drawRect(mRect, mPaint);
        // 再根据计算的路径绘制弧形
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.i(TAG, "onMeasure()");
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);

        initPaint();

        calculateShape();
    }

    private void initPaint() {
        mPaint = new Paint();
        // 参数一为渐变起初点坐标 x 位置，参数二为 y 轴位置，参数三和四分辨对应渐变终点，最后参数为平铺方式，这里设置为镜像
        LinearGradient lg = new LinearGradient(0, 0, mWidth, 0,
                Color.parseColor("#4796FB"), Color.parseColor("#5AB4F9"),
                Shader.TileMode.CLAMP);
        // 刚才已经讲到 Gradient 是基于 Shader 类，所以我们通过 Paint 的 setShader 方法来设置这个渐变
        mPaint.setShader(lg);
        mPaint.setStyle(Paint.Style.FILL);
        // 设置了 Shader 就不需要重复设置背景色了
        //mPaint.setColor(mBgColor);
        mPaint.setAntiAlias(true);
    }

    private void calculateShape() {
        mRect = new Rect(0, 0, mWidth, mHeight - mArcHeight);

        mPath = new Path();
        mPath.moveTo(0, mHeight - mArcHeight);
        mPath.quadTo(mWidth / 2, mHeight, mWidth, mHeight - mArcHeight);
    }
}
