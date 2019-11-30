package com.example.assignment02;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {

    private static final String TAG = CustomView.class.getSimpleName();
    private int nSquares;
    private int squareDim;


    public CustomView(Context c) {
        super(c);
        init();
    }

    public CustomView(Context c, AttributeSet as) {
        super(c, as);
        init();
    }

    public CustomView(Context c, AttributeSet as, int default_style) {
        super(c, as, default_style);
        init();
    }

    private void init() {
        nSquares = 8;

    }

    @Override
    public void onMeasure(int widthMeasureSpace, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpace);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int d = (width == 0) ? height : (height == 0) ? width : (width < height) ? width : height;
        setMeasuredDimension(d, d);
        squareDim = width / nSquares;

    }
}

