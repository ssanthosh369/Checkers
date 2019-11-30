package com.example.assignment02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CustomView extends View {

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
    private float[] lastTouchDownXY = new float[2];
    private ArrayList<Integer> move;
    private ArrayList<Integer> temp;
    private boolean touching;
    private int x;
    private int y;
    private Piece clickedPiece;
    private int turn;
    private MainActivity main;


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
        move = new ArrayList<>();
        temp = new ArrayList<>();
        touching = false;
        this.setDrawingCacheEnabled(true);
        clickedPiece = null;
        turn = 1;

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
                        paintPiece.setARGB(200, player1[0], player1[1], player1[2]);
                    else
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
        if(touching) {
            clearArrays();
            int r = x * squareDim;
            int c = y * squareDim;
            int half = (squareDim / 2);
            paintHighlight.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawCircle(c + half, r + half, half * 2 / 3, paintHighlight);
            PosMove clicked = checkMovement(x,y);
            if(clicked.checkCapture()) {
                for(int i: clicked.getMove()) {
                    int canvasR = (i / 10) * squareDim;
                    int canvasC = (i % 10) * squareDim;
                    canvas.drawRect(canvasC,canvasR,canvasC + squareDim, canvasR + squareDim, paintHighlight);
                }
            } else {
                for(int i: clicked.getCapture()) {
                    int canvasR = (i / 10) * squareDim;
                    int canvasC = (i % 10) * squareDim;
                    canvas.drawRect(canvasC,canvasR,canvasC + squareDim, canvasR + squareDim, paintHighlight);
                }
            }
            move = clicked.getMove();
            temp = clicked.getCapture();
        }else {
            clearArrays();

        }
    }

    public void clearArrays() {
        move.clear();
        temp.clear();
    }

    public boolean onTouchEvent(MotionEvent event) {

        // Toast.makeText(this,TAG, Toast.LENGTH_SHORT).show();
        //Log.d(TAG,"clicked on tile : ");
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            lastTouchDownXY[0] = event.getX();
            lastTouchDownXY[1] = event.getY();
        }


        y = (int) lastTouchDownXY[0] / squareDim;
        x = (int) lastTouchDownXY[1] / squareDim;



        if (game.getBoard(x, y) == 1 && game.getPiece(x, y).getPlayer() == turn) {
            PosMove clicked = checkMovement(x,y);
            if(checkValidTouch(x,y,game.getPiece(x, y).getPlayer()) || !clicked.checkCapture()) {
                touching = !touching;
                if(touching) {
                    checkMovesPossible(game.getPiece(x, y).getPlayer());
                    move = clicked.getMove();
                    temp = clicked.getCapture();
                }
                invalidate();
            }
        } else {
            ArrayList<Integer> trial;
            if(temp.isEmpty())
                trial = move;
            else
                trial = temp;
            String s = "";
            for(int i: trial) {
                s += i + " ";
            }
            int removeCounter = 0;
            for(int i: trial) {
                if( (x * 10) + y == i) {
                    touching = false;
                    if(Math.abs(clickedPiece.getRow() - x) == 2 && Math.abs(clickedPiece.getCol() - y) == 2) {
                        if(clickedPiece.getRow() > x ) {
                            if(clickedPiece.getCol() > y) {
                                removePiece(clickedPiece.getRow() - 1,clickedPiece.getCol() - 1);
                            } else
                                removePiece(clickedPiece.getRow() - 1,clickedPiece.getCol() + 1);
                        }else {
                            if(clickedPiece.getCol() > y) {
                                removePiece(clickedPiece.getRow() + 1,clickedPiece.getCol() - 1);
                            } else
                                removePiece(clickedPiece.getRow() + 1,clickedPiece.getCol() + 1);
                        }
                        removeCounter = 1;
                        int count1 = game.count(1);
                        int count2 = game.count(2);

                        if(main != null)
                          //  main.updateText(count1,count2);

                        if(count1 == 0) {
                         //   main.winPlayer(2);
                        } else
                        if(count2 == 0) {
                        //    main.winPlayer(1);
                        }

                    }
                    game.setPiece(clickedPiece.getRow(),clickedPiece.getCol(),x,y);
                    if(removeCounter == 1) {
                        PosMove check = checkMovement(x,y);
                        if(check.checkCapture()) {
                            clearArrays();
                            changeTurn();
                            invalidate();
                        }
                        else
                            touching = true;
                    } else {
                        changeTurn();
                    }
                    postInvalidate();

                    break;
                }
            }


            //  Log.i(TAG, "No piece here!");

        }

        return false;
    }

    public PosMove checkMovement(int x,int y) {

        clickedPiece = game.getPiece(x, y);
        int player = game.getPiece(x, y).getPlayer();
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();

        int hRow;
        if (!game.getPiece(x, y).isType()) {
            if (player == 1) {
                hRow = (x + 1) * squareDim;
            } else {
                hRow = (x - 1) * squareDim;
            }

            if ((hRow / squareDim) >= 0 && (hRow / squareDim) <= 7) {
                if (y + 1 < 8) {
                    if (game.getBoard(hRow / squareDim, y + 1) == 0) {
                        a.add((hRow / squareDim) * 10 + (y + 1));
                    } else if (game.getBoard(hRow / squareDim, y + 1) == 1 && game.getPiece(hRow / squareDim, y + 1).getPlayer() != player) {
                        int cRow;
                        if (player == 1) {
                            cRow = (hRow / squareDim) + 1;
                        } else
                            cRow = (hRow / squareDim) - 1;
                        if (y + 2 < 8 && (cRow >= 0 && cRow < 8)) {
                            if (game.getBoard(cRow, y + 2) == 0) {
                                b.add((cRow * 10) + (y + 2));
                            }
                        }
                    }
                }

                if (y - 1 >= 0) {
                    if (game.getBoard(hRow / squareDim, y - 1) == 0) {
                        a.add((hRow / squareDim) * 10 + (y - 1));
                    } else if (game.getBoard(hRow / squareDim, y - 1) == 1 && game.getPiece(hRow / squareDim, y - 1).getPlayer() != player) {
                        int cRow;
                        if (player == 1) {
                            cRow = (hRow / squareDim) + 1;
                        } else
                            cRow = (hRow / squareDim) - 1;

                        if (y - 2 >= 0 && (cRow >= 0 && cRow < 8)) {
                            if (game.getBoard(cRow, y - 2) == 0) {
                                b.add((cRow * 10) + (y - 2));
                            }
                        }
                    }
                }
            }
        } else {

            int kRow = x + 1;
            int kCol = y + 1;
            if (kRow < 8 && kCol < 8) {

                if (game.getBoard(kRow, kCol) == 0) {
                    a.add((kRow) * 10 + kCol);
                } else if (game.getBoard(kRow, kCol) == 1 && game.getPiece(kRow, kCol).getPlayer() != player) {
                    kRow = x + 2;
                    kCol = y + 2;
                    if (kCol < 8 && (kRow >= 0 && kRow < 8)) {
                        if (game.getBoard(kRow, kCol) == 0) {
                            b.add((kRow * 10) + kCol);
                        }
                    }
                }
            }

            kRow = x + 1;
            kCol = y - 1;
            if (kRow < 8 && kCol >= 0) {

                if (game.getBoard(kRow, kCol) == 0) {
                    a.add((kRow * 10) + kCol);
                } else if (game.getBoard(kRow, kCol) == 1 && game.getPiece(kRow, kCol).getPlayer() != player) {
                    kRow = x + 2;
                    kCol = y - 2;
                    if (kCol >= 0 && (kRow >= 0 && kRow < 8)) {
                        if (game.getBoard(kRow, kCol) == 0) {
                            b.add((kRow * 10) + kCol);
                        }
                    }
                }
            }


            kRow = x - 1;
            kCol = y - 1;
            if (kRow >= 0 && kCol >= 0) {

                if (game.getBoard(kRow, kCol) == 0) {
                    a.add((kRow * 10) + kCol);
                } else if (game.getBoard(kRow, kCol) == 1 && game.getPiece(kRow, kCol).getPlayer() != player) {
                    kRow = x - 2;
                    kCol = y - 2;
                    if (kCol >= 0 && (kRow >= 0 && kRow < 8)) {
                        if (game.getBoard(kRow, kCol) == 0) {
                            b.add((kRow * 10) + kCol);
                        }
                    }
                }
            }

            kRow = x - 1;
            kCol = y + 1;
            if (kRow >= 0 && kCol < 8) {

                if (game.getBoard(kRow, kCol) == 0) {
                    a.add((kRow * 10) + kCol);
                } else if (game.getBoard(kRow, kCol) == 1 && game.getPiece(kRow, kCol).getPlayer() != player) {
                    kRow = x - 2;
                    kCol = y + 2;
                    if (kCol < 8 && (kRow >= 0 && kRow < 8)) {
                        if (game.getBoard(kRow, kCol) == 0) {
                            b.add((kRow * 10) + kCol);
                        }
                    }
                }
            }


        }

        return new PosMove(a,b);
    }

    public boolean checkValidTouch(int x, int y, int player) {
        boolean counter = true;
        for (Piece i : game.getList()) {
            if (!(i.getRow() == x && i.getCol() == y) && i.getPlayer() == player) {
                PosMove p = checkMovement(i.getRow(), i.getCol());
                if (!p.checkCapture()) {
                    counter = false;
                    break;
                }
            }
        }
        return counter;
    }

    private void changeTurn() {
        if(turn ==1)
            turn = 2;
        else
        if(turn ==2)
            turn = 1;
        //main.updateTurn(turn);
    }

    public void removePiece(int row,int col) {
        game.removePiece(row,col);
    }

    public void checkMovesPossible(int player) {
        boolean counter = true;
        for(Piece i: game.getList()) {
            if ( i.getPlayer() == player) {
                PosMove p = checkMovement(i.getRow(), i.getCol());
                if (!p.checkMove()) {
                    counter = false;
                    break;
                }
            }
        }
        if(counter) {
            if(player == 1) {
               // main.winPlayer(2);
            } else {
                if(player == 2) {
                    //  main.winPlayer(1);
                }
            }
        }
    }
}

