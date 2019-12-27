package com.example.customview.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;

import com.example.customview.R;

import java.util.ArrayList;

public class MyFrameLayout extends ViewGroup {


    private static boolean sUseZeroUnspecifiedMeasureSpec;
    private boolean sPreserveMarginParamsInLayoutParamConversion;


    public MyFrameLayout(Context context) {
        super(context);
        initViewGroup(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewGroup(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewGroup(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViewGroup(context);
    }


    private static final int DEFAULT_CHILD_GRAVITY = Gravity.TOP | Gravity.START;

    @ViewDebug.ExportedProperty(category = "measurement")
    boolean mMeasureAllChildren = false;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingLeft = 0;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingTop = 0;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingRight = 0;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingBottom = 0;

    //保存设置大小为MatchParent的view
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);


    private void initViewGroup(Context context) {
        sPreserveMarginParamsInLayoutParamConversion = context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.N;
        sUseZeroUnspecifiedMeasureSpec = context.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.M;
    }


    /**
     * Describes how the foreground is positioned. Defaults to START and TOP.
     *
     * @param foregroundGravity See {@link android.view.Gravity}
     * @attr ref android.R.styleable#View_foregroundGravity
     * @see #getForegroundGravity()
     */

    public void setForegroundGravity(int foregroundGravity) {
        if (getForegroundGravity() != foregroundGravity) {
            super.setForegroundGravity(foregroundGravity);

            // calling get* again here because the set above may apply default constraints
            final Drawable foreground = getForeground();
            if (getForegroundGravity() == Gravity.FILL && foreground != null) {
                Rect padding = new Rect();
                if (foreground.getPadding(padding)) {
                    mForegroundPaddingLeft = padding.left;
                    mForegroundPaddingTop = padding.top;
                    mForegroundPaddingRight = padding.right;
                    mForegroundPaddingBottom = padding.bottom;
                }
            } else {
                mForegroundPaddingLeft = 0;
                mForegroundPaddingTop = 0;
                mForegroundPaddingRight = 0;
                mForegroundPaddingBottom = 0;
            }

            requestLayout();
        }
    }

    /**
     * Returns a set of layout parameters with a width of
     * {@link android.view.ViewGroup.LayoutParams#MATCH_PARENT},
     * and a height of {@link android.view.ViewGroup.LayoutParams#MATCH_PARENT}.
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    int getPaddingLeftWithForeground() {
        return getPaddingLeft() + mForegroundPaddingLeft;
        //FIXME
//        return isForegroundInsidePadding() ? Math.max(getPaddingLeft(), mForegroundPaddingLeft) : getPaddingLeft() + mForegroundPaddingLeft;
    }

    int getPaddingRightWithForeground() {
        return getPaddingRight() + mForegroundPaddingRight;
        //FIXME
//        return isForegroundInsidePadding() ? Math.max(getPaddingRight(), mForegroundPaddingRight) : getPaddingRight() + mForegroundPaddingRight;
    }

    private int getPaddingTopWithForeground() {
        return getPaddingTop() + mForegroundPaddingTop;
        //FIXME
//        return isForegroundInsidePadding() ? Math.max(getPaddingTop(), mForegroundPaddingTop) : getPaddingTop() + mForegroundPaddingTop;
    }

    private int getPaddingBottomWithForeground() {
        return getPaddingBottom() + mForegroundPaddingBottom;
        //FIXME
//        return isForegroundInsidePadding() ? Math.max(getPaddingBottom(), mForegroundPaddingBottom) : getPaddingBottom() + mForegroundPaddingBottom;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        final boolean measureMatchParentChildren = MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY || MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        mMatchParentChildren.clear();


        Log.e("FFF", "MyFrameLayout 宽度测量规格---->" + getStringMode(widthMeasureSpec));
        Log.e("FFF", "MyFrameLayout 高度测量规格---->" + getStringMode(heightMeasureSpec));

        //最大高度
        int maxHeight = 0;
        //最大宽度
        int maxWidth = 0;
        int childState = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (mMeasureAllChildren || child.getVisibility() != GONE) {
                //把我自己的测量模式传递给子View，好让子View知道你到底最大能多少
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);

                Log.e("FFF", "子view的高度为--------->"+child.getMeasuredHeight());

                childState = combineMeasuredStates(childState, child.getMeasuredState());
                if (measureMatchParentChildren) {
                    if (lp.width == LayoutParams.MATCH_PARENT || lp.height == LayoutParams.MATCH_PARENT) {
                        mMatchParentChildren.add(child);
                    }
                }
            }
        }

        // Account for padding too
        maxWidth += getPaddingLeftWithForeground() + getPaddingRightWithForeground();
        maxHeight += getPaddingTopWithForeground() + getPaddingBottomWithForeground();

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        // Check against our foreground's minimum height and width
        final Drawable drawable = getForeground();
        if (drawable != null) {
            maxHeight = Math.max(maxHeight, drawable.getMinimumHeight());
            maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
        }

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState), resolveSizeAndState(maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));

        //这里是为那些MatchParent的子view重新设置高度和宽度。因为运行到上面的代码已经把当前ViewGroup的大小确定了，所以这里就要开始为
        //那些MatchParent的子view重新设置当前ViewGroup的高度或者宽度
        count = mMatchParentChildren.size();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                final View child = mMatchParentChildren.get(i);
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                final int childWidthMeasureSpec;
                //如果这个view的宽度是MatchParent
                if (lp.width == LayoutParams.MATCH_PARENT) {
                    //设置宽度为父view的宽度
                    final int width = Math.max(0, getMeasuredWidth() - getPaddingLeftWithForeground() - getPaddingRightWithForeground() - lp.leftMargin - lp.rightMargin);
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
                } else {
                    //宽度
                    childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeftWithForeground() + getPaddingRightWithForeground() + lp.leftMargin + lp.rightMargin, lp.width);
                }

                final int childHeightMeasureSpec;
                //如果高度为matchparent
                if (lp.height == LayoutParams.MATCH_PARENT) {
                    final int height = Math.max(0, getMeasuredHeight() - getPaddingTopWithForeground() - getPaddingBottomWithForeground() - lp.topMargin - lp.bottomMargin);
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
                } else {
                    childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTopWithForeground() + getPaddingBottomWithForeground() + lp.topMargin + lp.bottomMargin, lp.height);
                }

                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        //子View自己的测量规格
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin + widthUsed, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed, lp.height);
        Log.e("FFF", "childHeightMeasureSpec------->"+childHeightMeasureSpec);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    /**
     * @param spec           父View的测量规格
     * @param padding        已经占用了多少空间
     * @param childDimension 子View自己的测量规格
     * @return
     */
    public static int getChildMeasureSpec(int spec, int padding, int childDimension) {


        //测量规格
        int specMode = MeasureSpec.getMode(spec);
        //父View还剩下多少空间
        int specSize = MeasureSpec.getSize(spec);

        //子View最大大小
        int size = Math.max(0, specSize - padding);

        int resultSize = 0;
        int resultMode = 0;

        switch (specMode) {
            //父类的测量规格是确定的大小
            case MeasureSpec.EXACTLY:
                //子view的大小自己制定了
                if (childDimension >= 0) {
                    //子view的大小就是自己指定的那个大小
                    resultSize = childDimension;//如果父view只有100px，但是我自己指定了200px呢，这样就会有问题，但是这个问题是由于开发者自己指定的错误大小导致的
                    //FIXME 如果子view的大小大于了父view的大小，那么子view会什么不会覆盖父view呢
                    //由于子view自己指定了大小，所以它的测量规格就是Exactly
                    resultMode = MeasureSpec.EXACTLY;
                }
                //如果子view的大小是父类大小
                else if (childDimension == LayoutParams.MATCH_PARENT) {
                    //那子view的大小就是父view的大小
                    resultSize = size;
                    //子view的大小指定为父类的大小，父类又是确定的 ，则测量规格就是Exactly
                    resultMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                    //子view要求自己是包裹内容，则大小是父view的大小，测量规格就是AT_MOST，代表你是包裹内容，但是你最大不能超过父view给你的大小
                    //有可能子view用不了那么多，那我们就有剩下的空间
                    resultSize = size;
                    resultMode = MeasureSpec.AT_MOST;
                }
                break;

            // 父view是包裹内容
            case MeasureSpec.AT_MOST:
                if (childDimension >= 0) {
                    //父view是包裹的，但是我是指定的大小，那我的大小就是我自己指定的大小。
                    resultSize = childDimension;//如果父view只有100px，但是我自己指定了200px呢，这样就会有问题，但是这个问题是由于开发者自己指定的错误大小导致的，这里的程序还是正确的
                    resultMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                    //父view是包裹内容，但是我给自己指定的是父view的大小，那我自己的大小还是只能是最大是父view那么大
                    resultSize = size;
                    resultMode = MeasureSpec.AT_MOST;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                    //如果父view是包裹内容，我给我自己也是指定的包裹内容，设定自己最大是父view那么大
                    resultSize = size;
                    resultMode = MeasureSpec.AT_MOST;
                }
                break;

            //父view的大小是无限大
            case MeasureSpec.UNSPECIFIED:
                if (childDimension >= 0) {
                    //子view的大小还是自己设定的大小
                    resultSize = childDimension;
                    resultMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                    // Child wants to be our size... find out how big it should
                    // be
                    resultSize = sUseZeroUnspecifiedMeasureSpec ? 0 : size;
                    resultMode = MeasureSpec.UNSPECIFIED;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                    // Child wants to determine its own size.... find out how
                    // big it should be
                    resultSize = sUseZeroUnspecifiedMeasureSpec ? 0 : size;
                    resultMode = MeasureSpec.UNSPECIFIED;
                }
                break;
        }
        //noinspection ResourceType
        return MeasureSpec.makeMeasureSpec(resultSize, resultMode);
    }

    /**
     * @param size               我自己测量的大小
     * @param measureSpec        父类给我的测量大小
     * @param childMeasuredState
     * @return
     */
    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        final int result;
        switch (specMode) {
            //我给我自己指定的大小是包裹内容
            case MeasureSpec.AT_MOST:
                //父view剩下的空间小于我自己测量出来的空间
                if (specSize < size) {
                    //结果为父view剩下的大小
                    result = specSize | MEASURED_STATE_TOO_SMALL;
                } else {
                    //大小为我自己指定的大小
                    result = size;
                }
                break;
            //测量规格是我给我自己指定了大小
            case MeasureSpec.EXACTLY:
                //大小就是我自己指定的小
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                //未指定的话，那大小就是我自己测量出来的大小
                result = size;
        }
        return result | (childMeasuredState & MEASURED_STATE_MASK);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutChildren(left, top, right, bottom, false /* no force left gravity */);
        Log.e("FFF", "left-->" + left);
        Log.e("FFF", "top-->" + top);
        Log.e("FFF", "right-->" + right);
        Log.e("FFF", "bottom-->" + bottom);
    }

    void layoutChildren(int left, int top, int right, int bottom, boolean forceLeftGravity) {
        final int count = getChildCount();

        //paddingLeft
        final int parentLeft = getPaddingLeftWithForeground();
        //距离右边还剩下多少空间
        final int parentRight = right - left - getPaddingRightWithForeground();

        //paddingTop
        final int parentTop = getPaddingTopWithForeground();
        //距离底部还剩下多少距离
        final int parentBottom = bottom - top - getPaddingBottomWithForeground();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                //子view的测量宽度
                final int width = child.getMeasuredWidth();
                //子view的测量高度
                final int height = child.getMeasuredHeight();

                int childLeft;
                int childTop;

                int gravity = lp.gravity;
                if (gravity == -1) {
                    gravity = DEFAULT_CHILD_GRAVITY;
                }

                final int layoutDirection = getLayoutDirection();
                final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
                final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

                //水平的gravity
                switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                    //水平居中
                    case Gravity.CENTER_HORIZONTAL:
                        childLeft = parentLeft + (parentRight - parentLeft - width) / 2 + lp.leftMargin - lp.rightMargin;
                        break;
                    case Gravity.RIGHT:
                        if (!forceLeftGravity) {
                            childLeft = parentRight - width - lp.rightMargin;
                            break;
                        }
                    case Gravity.LEFT:
                    default:
                        childLeft = parentLeft + lp.leftMargin;
                }

                switch (verticalGravity) {
                    case Gravity.TOP:
                        childTop = parentTop + lp.topMargin;
                        break;
                    case Gravity.CENTER_VERTICAL:
                        childTop = parentTop + (parentBottom - parentTop - height) / 2 + lp.topMargin - lp.bottomMargin;
                        break;
                    case Gravity.BOTTOM:
                        childTop = parentBottom - height - lp.bottomMargin;
                        break;
                    default:
                        childTop = parentTop + lp.topMargin;
                }

                child.layout(childLeft, childTop, childLeft + width, childTop + height);
            }
        }
    }

    /**
     * Sets whether to consider all children, or just those in
     * the VISIBLE or INVISIBLE state, when measuring. Defaults to false.
     *
     * @param measureAll true to consider children marked GONE, false otherwise.
     *                   Default value is false.
     * @attr ref android.R.styleable#FrameLayout_measureAllChildren
     */

    public void setMeasureAllChildren(boolean measureAll) {
        mMeasureAllChildren = measureAll;
    }

    /**
     * Determines whether all children, or just those in the VISIBLE or
     * INVISIBLE state, are considered when measuring.
     *
     * @return Whether all children are considered when measuring.
     * @deprecated This method is deprecated in favor of
     * {@link #getMeasureAllChildren() getMeasureAllChildren()}, which was
     * renamed for consistency with
     * {@link #setMeasureAllChildren(boolean) setMeasureAllChildren()}.
     */
    @Deprecated
    public boolean getConsiderGoneChildrenWhenMeasuring() {
        return getMeasureAllChildren();
    }

    /**
     * Determines whether all children, or just those in the VISIBLE or
     * INVISIBLE state, are considered when measuring.
     *
     * @return Whether all children are considered when measuring.
     */
    public boolean getMeasureAllChildren() {
        return mMeasureAllChildren;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyFrameLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (sPreserveMarginParamsInLayoutParamConversion) {
            if (lp instanceof LayoutParams) {
                return new LayoutParams((LayoutParams) lp);
            } else if (lp instanceof MarginLayoutParams) {
                return new LayoutParams((MarginLayoutParams) lp);
            }
        }
        return new LayoutParams(lp);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return MyFrameLayout.class.getName();
    }

    private String getStringMode(int measure) {
        String mode = "";
        if (MeasureSpec.getMode(measure) == MeasureSpec.EXACTLY) {
            mode = "我给我自己设置的测量规格是---->指定的大小---->" + MeasureSpec.getSize(measure);
        } else if (MeasureSpec.getMode(measure) == MeasureSpec.AT_MOST) {
            mode = "我给我自己设置的测量规格是---->包裹内容---->" + MeasureSpec.getSize(measure);
        } else {
            mode = "我给我自己设置的测量规格是---->无限大小---->" + MeasureSpec.getSize(measure);
        }
        return mode;
    }
//    /**
//     * @hide FIXME
//     */
//    @Override
//    protected void encodeProperties(@NonNull ViewHierarchyEncoder encoder) {
//        super.encodeProperties(encoder);
//
//        encoder.addProperty("measurement:measureAllChildren", mMeasureAllChildren);
//        encoder.addProperty("padding:foregroundPaddingLeft", mForegroundPaddingLeft);
//        encoder.addProperty("padding:foregroundPaddingTop", mForegroundPaddingTop);
//        encoder.addProperty("padding:foregroundPaddingRight", mForegroundPaddingRight);
//        encoder.addProperty("padding:foregroundPaddingBottom", mForegroundPaddingBottom);
//    }

    /**
     * Per-child layout information for layouts that support margins.
     * See { android.R.styleable#FrameLayout_Layout FrameLayout Layout Attributes}
     * for a list of all child view attributes that this class supports.
     *
     * @attr ref android.R.styleable#FrameLayout_Layout_layout_gravity
     */
    public static class LayoutParams extends MarginLayoutParams {
        /**
         * Value for {@link #gravity} indicating that a gravity has not been
         * explicitly specified.
         */
        public static final int UNSPECIFIED_GRAVITY = -1;

        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         * <p>
         * The default value is {@link #UNSPECIFIED_GRAVITY}, which is treated
         * by FrameLayout as {@code Gravity.TOP | Gravity.START}.
         *
         * @attr ref android.R.styleable#FrameLayout_Layout_layout_gravity
         * @see android.view.Gravity
         */
        public int gravity = UNSPECIFIED_GRAVITY;

        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);
            final TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.MyFrameLayout_Layout);
            gravity = a.getInt(R.styleable.MyFrameLayout_Layout_my_layout_gravity, UNSPECIFIED_GRAVITY);
            a.recycle();

        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        /**
         * Creates a new set of layout parameters with the specified width, height
         * and weight.
         *
         * @param width   the width, either {@link #MATCH_PARENT},
         *                {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param height  the height, either {@link #MATCH_PARENT},
         *                {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param gravity the gravity
         * @see android.view.Gravity
         */
        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
            this.gravity = gravity;
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(@NonNull ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        /**
         * Copy constructor. Clones the width, height, margin values, and
         * gravity of the source.
         *
         * @param source The layout params to copy from.
         */
        public LayoutParams(@NonNull LayoutParams source) {
            super(source);

            this.gravity = source.gravity;
        }

    }
}
