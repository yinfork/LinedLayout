package com.yinfork.linedlayout;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Lined Animate RelativeLayout
 */
public class LinedRelativeLayout extends RelativeLayout {

    // CanvasView is the top view to draw line
    private CanvasView mCanvasView;

    private boolean mIsIgnoreFirstFocus = false;
    private boolean mIsHadFirstFocus = false;

    public LinedRelativeLayout(Context context) {
        super(context);
    }

    public LinedRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinedRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPaint(Paint paint) {
        if (null != mCanvasView) {
            mCanvasView.setPaint(paint);
        }
    }

    public void setLineColor(int color) {
        if (null != mCanvasView) {
            mCanvasView.setLineColor(color);
        }
    }

    public void setLineWidth(float width) {
        if (null != mCanvasView) {
            mCanvasView.setLineWidth(width);
        }
    }

    public void setBendLength(float bendLength) {
        if (null != mCanvasView) {
            mCanvasView.setBendLength(bendLength);
        }
    }

    public void setLinePaddingBottom(int linePaddingBottom) {
        if (null != mCanvasView) {
            mCanvasView.setLinePaddingBottom(linePaddingBottom);
        }
    }

    public void setLinePaddingLeft(int linePaddingLeft) {
        if (null != mCanvasView) {
            mCanvasView.setLinePaddingLeft(linePaddingLeft);
        }
    }

    public void setLinePaddingRight(int linePaddingRight) {
        if (null != mCanvasView) {
            mCanvasView.setLinePaddingRight(linePaddingRight);
        }
    }

    public void setAnimDuration(long duration) {
        if (null != mCanvasView) {
            mCanvasView.setAnimDuration(duration);
        }
    }

    public void setInterpolator(Interpolator interpolator) {
        if (null != mCanvasView) {
            mCanvasView.setInterpolator(interpolator);
        }
    }

    public void setIgnoreFirstFocus(boolean isIgnore) {
        mIsIgnoreFirstFocus = isIgnore;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        OnFocusChangeListener listener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    obtainFoucedAnim(v);
                } else {
                    loseFoucedAnim(v);
                }
            }
        };

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (null != view && view instanceof EditText) {
                view.setOnFocusChangeListener(listener);
            }
        }

        if (null == mCanvasView) {
            mCanvasView = new CanvasView(getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            addView(mCanvasView, params);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (null != mCanvasView) {
            if (0 == mCanvasView.getHeight()) {
                ViewGroup.LayoutParams params = mCanvasView.getLayoutParams();
                if (null != params) {
                    params.height = b - t;
                    mCanvasView.setLayoutParams(params);
                    requestLayout();
                }
            }
        }
    }

    private void obtainFoucedAnim(View view) {
        if (mIsIgnoreFirstFocus && !mIsHadFirstFocus) {
            mIsHadFirstFocus = true;
            mCanvasView.obtainFoucedAnim(view, false);
        } else {
            mCanvasView.obtainFoucedAnim(view, true);
        }
    }

    private void loseFoucedAnim(View view) {
        mCanvasView.loseFoucedAnim(view);
    }
}
