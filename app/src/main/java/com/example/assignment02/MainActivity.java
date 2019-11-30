package com.example.assignment02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView p1;
    private TextView p2;
    private TextView pTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        pTurn = findViewById(R.id.pTurn);

        CustomView d = findViewById(R.id.CustomView);
        d.setReference(this);

        updateText(12, 12);
        updateTurn(1);

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomView draughts = findViewById(R.id.CustomView);
                draughts.reset();

            }
        });

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomView draughts = findViewById(R.id.CustomView);
                clickBox(draughts);

            }
        });

    }

    public void updateText(int count1, int count2) {
        Log.d("CustomView", "Updated text ");
        CustomView draughts = findViewById(R.id.CustomView);
        String s1 = "Player 1 pieces: " + count1;
        p1.setText(s1);
        int[] player1 = draughts.getColorP1();
        p1.setTextColor(Color.argb(255, player1[0], player1[1], player1[2]));
        String s2 = "Player 2 pieces: " + count2;
        p2.setText(s2);
        int[] player2 = draughts.getColorP2();
        p2.setTextColor(Color.argb(255, player2[0], player2[1], player2[2]));
    }

    public void updateTurn(int turn) {
        String s = "Turn of player " + turn;
        pTurn.setText(s);
    }

    public void clickBox(CustomView d) {
        final CustomView draughts = d;
        final EditText et1 = new EditText(this);
        et1.setHint("Enter the Red color value (0 - 255)");
        et1.setSingleLine();
        final EditText et2 = new EditText(this);
        et2.setHint("Enter the Green color value (0 - 255)");
        et2.setSingleLine();
        final EditText et3 = new EditText(this);
        et3.setHint("Enter the Blue color value (0 - 255)");
        et3.setSingleLine();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Change color of?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Squares",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                        alert.setTitle("Choose which square ");

                        alert.setPositiveButton("Square 2", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                alert.setTitle("Enter the RGB Value");

                                LinearLayout layout = new LinearLayout(getBaseContext());
                                layout.setOrientation(LinearLayout.VERTICAL);

                                layout.addView(et1);
                                layout.addView(et2);
                                layout.addView(et3);

                                alert.setView(layout);
                                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        String rString = et1.getText().toString();
                                        String gString = et2.getText().toString();
                                        String bString = et3.getText().toString();

                                        if ((rString.isEmpty() || gString.isEmpty() || bString.isEmpty())) {
                                            Toast.makeText(getBaseContext(), "RGB values cannot be empty ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            try {
                                                int r = Integer.parseInt(et1.getText().toString());
                                                int g = Integer.parseInt(et2.getText().toString());
                                                int b = Integer.parseInt(et3.getText().toString());

                                                if ((r <= 255 && r >= 0) && (g <= 255 && g >= 0) && (b <= 255 && b >= 0)) {
                                                    draughts.setSquare2(r, g, b);
                                                } else {
                                                    Toast.makeText(getBaseContext(), "RGB values have to be between 0 to 255 ", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                Toast.makeText(getBaseContext(), "RGB values have to be integers ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });

                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                });

                                alert.show();
                                dialog.cancel();
                            }
                        });

                        alert.setNegativeButton("Square 1", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                alert.setTitle("Enter the RGB Value");

                                LinearLayout layout = new LinearLayout(getBaseContext());
                                layout.setOrientation(LinearLayout.VERTICAL);

                                layout.addView(et1);
                                layout.addView(et2);
                                layout.addView(et3);

                                alert.setView(layout);
                                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        String rString = et1.getText().toString();
                                        String gString = et2.getText().toString();
                                        String bString = et3.getText().toString();

                                        if ((rString.isEmpty() || gString.isEmpty() || bString.isEmpty())) {
                                            Toast.makeText(getBaseContext(), "RGB values cannot be empty ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            try {
                                                int r = Integer.parseInt(et1.getText().toString());
                                                int g = Integer.parseInt(et2.getText().toString());
                                                int b = Integer.parseInt(et3.getText().toString());

                                                if ((r <= 255 && r >= 0) && (g <= 255 && g >= 0) && (b <= 255 && b >= 0)) {
                                                    draughts.setSquare1(r, g, b);
                                                } else {
                                                    Toast.makeText(getBaseContext(), "RGB values have to be between 0 to 255 ", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                Toast.makeText(getBaseContext(), "RGB values have to be integers ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });

                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                });

                                alert.show();
                                dialog.cancel();
                            }
                        });

                        alert.show();

                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Pieces",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                        alert.setTitle("Which player's pieces ?");

                        alert.setPositiveButton("Player 2", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                alert.setTitle("Enter the RGB Value");

                                LinearLayout layout = new LinearLayout(getBaseContext());
                                layout.setOrientation(LinearLayout.VERTICAL);

                                layout.addView(et1);
                                layout.addView(et2);
                                layout.addView(et3);

                                alert.setView(layout);
                                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {


                                        String rString = et1.getText().toString();
                                        String gString = et2.getText().toString();
                                        String bString = et3.getText().toString();

                                        if ((rString.isEmpty() || gString.isEmpty() || bString.isEmpty())) {
                                            Toast.makeText(getBaseContext(), "RGB values cannot be empty ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            try {
                                                int r = Integer.parseInt(et1.getText().toString());
                                                int g = Integer.parseInt(et2.getText().toString());
                                                int b = Integer.parseInt(et3.getText().toString());

                                                if ((r <= 255 && r >= 0) && (g <= 255 && g >= 0) && (b <= 255 && b >= 0)) {
                                                    draughts.setPlayer2(r, g, b);
                                                } else {
                                                    Toast.makeText(getBaseContext(), "RGB values have to be between 0 to 255 ", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                Toast.makeText(getBaseContext(), "RGB values have to be integers ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });

                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                });

                                alert.show();
                                dialog.cancel();
                            }
                        });

                        alert.setNegativeButton("Player 1", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                alert.setTitle("Enter the RGB Value");

                                LinearLayout layout = new LinearLayout(getBaseContext());
                                layout.setOrientation(LinearLayout.VERTICAL);

                                layout.addView(et1);
                                layout.addView(et2);
                                layout.addView(et3);

                                alert.setView(layout);
                                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        String rString = et1.getText().toString();
                                        String gString = et2.getText().toString();
                                        String bString = et3.getText().toString();

                                        if ((rString.isEmpty() || gString.isEmpty() || bString.isEmpty())) {
                                            Toast.makeText(getBaseContext(), "RGB values cannot be empty ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            try {
                                                int r = Integer.parseInt(et1.getText().toString());
                                                int g = Integer.parseInt(et2.getText().toString());
                                                int b = Integer.parseInt(et3.getText().toString());

                                                if ((r <= 255 && r >= 0) && (g <= 255 && g >= 0) && (b <= 255 && b >= 0)) {
                                                    draughts.setPlayer1(r, g, b);
                                                } else {
                                                    Toast.makeText(getBaseContext(), "RGB values have to be between 0 to 255 ", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                Toast.makeText(getBaseContext(), "RGB values have to be integers ", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    }
                                });

                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                });

                                alert.show();
                                dialog.cancel();
                            }
                        });

                        alert.show();

                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public void winPlayer(int player) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        alert.setTitle("GAME OVER! Player " + player + " wins!!!");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }
}
