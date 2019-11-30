package com.example.assignment02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {

    private static final String TAG = CustomView.class.getSimpleName();
    private int nSquares;
    private int colorH;
    private int squareDim;

    private int[] player1;
    private int[] player2;
    private int[] square1;
    private int[] square2;

    private Paint paint;
    private Paint paintPiece;
    private Paint paintHighlight;

    private GameBoard game;


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
        colorH = Color.YELLOW;
        player1 = new int[]{255, 255, 255};
        player2 = new int[]{255, 0, 0};
        square2 = new int[]{0, 0, 0};
        square1 = new int[]{128, 128, 128};

        paint = new Paint();
        paintPiece = new Paint();
        paintHighlight = new Paint();
        game = new GameBoard();

    }

    @Override
    public void onMeasure(int widthMeasureSpace, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpace);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int d = (width == 0) ? height : (height == 0) ? width : (width < height) ? width : height;
        setMeasuredDimension(d, d);
        squareDim = width / nSquares;

    }

    @Override
    protected void onDraw(Canvas canvas) {


        int counter = 0;
        for (int row = 0; row < nSquares; row++) {
            if ((row & 1) == 0) {
                paint.setARGB(200, square1[0], square1[1], square1[2]);
                counter = 1;
            } else {
                paint.setARGB(200, square2[0], square2[1], square2[2]);
                counter = 2;
            }
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paintHighlight.setColor(colorH);

            for (int col = 0; col < nSquares; col++) {
                int a = col * squareDim;
                int b = row * squareDim;
                int half = (squareDim / 2);
                canvas.drawRect(a, b, a + squareDim, b + squareDim, paint);
                if (counter == 2) {
                    paint.setARGB(200, square1[0], square1[1], square1[2]);
                    counter = 1;
                } else {
                    paint.setARGB(200, square2[0], square2[1], square2[2]);
                    counter = 2;
                }

                if (game.getBoard(row, col) == 1) {

                    if (game.getPiece(row, col).getPlayer() == 1)
                        // paintPiece.setColor(Color.WHITE);
                        paintPiece.setARGB(200, player1[0], player1[1], player1[2]);
                    else
                        //paintPiece.setColor(Color.RED);
                        paintPiece.setARGB(200, player2[0], player2[1], player2[2]);

                    paintPiece.setFlags(Paint.ANTI_ALIAS_FLAG);
                    canvas.drawCircle(a + half, b + half, half * 2 / 3, paintPiece);

                    if (game.getPiece(row, col).isType()) {
                        paintPiece.setColor(Color.GREEN);
                        canvas.drawCircle(a + half, b + half, half * 1 / 3, paintPiece);
                    }
                }

            }


        }
    }
}

