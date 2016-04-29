package com.yinfork.linedlayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CanvasView extends View {

    private final static int DEFAULT_PAINT_WIDTH = 3;
    private final static int DEFAULT_PAINT_COLOR = Color.BLUE;

    private final static int FOCUS_IN = 100;
    private final static int FOCUS_NEXT = 200;
    private final static int FOCUS_OUT = 300;
    private final static int FOCUS_NONE = 400;

    @IntDef({FOCUS_IN, FOCUS_NEXT, FOCUS_OUT, FOCUS_NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusMode {
    }

    @FocusMode
    int mDrawMode = FOCUS_NONE;


    private Paint mPaint;
    private float mProgress = 0;
    private float mBendLength = 80;
    private int mLinePaddingBottom = 10;
    private int mLinePaddingLeft = 10;
    private int mLinePaddingRight = 10;
    private long mDuration = 300;
    private Interpolator mInterpolator = new AccelerateInterpolator();

    private View mLastFocusView;
    private View mCurFocusView;


    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(DEFAULT_PAINT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DEFAULT_PAINT_WIDTH);
    }

    public void setPaint(Paint paint) {
        if (null != paint) {
            mPaint = paint;
        }
    }

    public void setLineColor(int color) {
        if (null != mPaint) {
            mPaint.setColor(color);
        }
    }

    public void setLineWidth(float width) {
        if (null != mPaint) {
            mPaint.setStrokeWidth(width);
        }
    }

    public void setBendLength(float bendLength) {
        this.mBendLength = bendLength;
    }

    public void setLinePaddingBottom(int linePaddingBottom) {
        this.mLinePaddingBottom = linePaddingBottom;
    }

    public void setLinePaddingLeft(int linePaddingLeft) {
        this.mLinePaddingLeft = linePaddingLeft;
    }

    public void setLinePaddingRight(int linePaddingRight) {
        this.mLinePaddingRight = linePaddingRight;
    }

    public void setAnimDuration(long duration) {
        this.mDuration = duration;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public void startAnimate(boolean isIn) {
        float begin = isIn ? 0 : 1;
        float end = isIn ? 1 : 0;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(begin, end).setDuration(mDuration);
        valueAnimator.setInterpolator(mInterpolator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (mDrawMode) {
            case FOCUS_NONE:
                break;
            case FOCUS_IN:
                if (null != mCurFocusView) {
                    float x = mCurFocusView.getX() + mLinePaddingLeft;
                    float y = mCurFocusView.getY() + mCurFocusView.getHeight() - mPaint.getStrokeWidth() - mLinePaddingBottom;
                    float width = (mCurFocusView.getWidth() - mLinePaddingRight - mLinePaddingLeft) * mProgress;
                    float height = y;

                    canvas.drawLine(x, y, width, height, mPaint);
                }

                break;

            case FOCUS_NEXT:
                if (null != mCurFocusView && null != mLastFocusView) {
                    float beginX = mLastFocusView.getX() + mLinePaddingLeft;
                    float endX = mCurFocusView.getX() + mLinePaddingLeft;
                    float beginY = mLastFocusView.getY() + mLastFocusView.getHeight() - mPaint.getStrokeWidth() - mLinePaddingBottom;
                    float endY = mCurFocusView.getY() + mCurFocusView.getHeight() - mPaint.getStrokeWidth() - mLinePaddingBottom;
                    float beginLength = mLastFocusView.getWidth() - mLinePaddingRight - mLinePaddingLeft;
                    float endLength = mLastFocusView.getWidth() - mLinePaddingRight - mLinePaddingLeft;

                    float beginTailX = beginX + beginLength;
                    float endTailX = endX + endLength;
                    float maxTailX = beginTailX >= endTailX ? beginTailX : endTailX;
                    float bendX = beginTailX >= endTailX ? mBendLength + beginTailX : mBendLength + endTailX;
                    float bendY = beginY + (endY - beginY) / 2;

                    Path path = new Path();
                    path.moveTo(beginX, beginY);
                    path.lineTo(maxTailX, beginY);
                    path.quadTo(bendX, beginY, bendX, bendY);
                    path.quadTo(bendX, endY, maxTailX, endY);
                    path.lineTo(endX, endY);

                    Path mRenderPaths = new Path();

                    PathMeasure mPathMeasure = new PathMeasure(path, false);

                    float pathOffset = (mPathMeasure.getLength() - endLength) * mProgress;

                    if (mPathMeasure.getSegment(pathOffset, pathOffset + endLength, mRenderPaths, true)) {
                        canvas.drawPath(mRenderPaths, mPaint);
                    }
                }
                break;

            case FOCUS_OUT:
                if (null != mLastFocusView) {
                    float x = mLastFocusView.getX() + mLinePaddingLeft;
                    float y = mLastFocusView.getY() + mLastFocusView.getHeight() - mPaint.getStrokeWidth() - mLinePaddingBottom;
                    float width = (mLastFocusView.getWidth() - mLinePaddingRight) * mProgress + mLinePaddingLeft;
                    float height = y;

                    canvas.drawLine(x, y, width, height, mPaint);
                }
                break;
        }
    }


    public void obtainFoucedAnim(View view, boolean isStartAnimate) {
        if (view == null) return;

        if (null == mCurFocusView) {
            mDrawMode = FOCUS_IN;
            mCurFocusView = view;
        } else {
            mDrawMode = FOCUS_NEXT;
            mLastFocusView = mCurFocusView;
            mCurFocusView = view;
        }

        if (isStartAnimate) {
            startAnimate(true);
        } else {
            mProgress = 1;
            invalidate();
        }
    }

    public void loseFoucedAnim(final View view) {
        if (view == null) return;

        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view == mCurFocusView) {
                    mDrawMode = FOCUS_OUT;
                    mCurFocusView = null;
                    mLastFocusView = view;
                    startAnimate(false);
                }

            }
        }, 50);
    }
}
