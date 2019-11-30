package com.example.assignment02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        CustomView d = (CustomView) findViewById(R.id.CustomView);
        d.setReference(this);


        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomView draughts = (CustomView) findViewById(R.id.CustomView);
                draughts.reset();

            }
        });

    }


    public void updateTurn(int turn) {
        pTurn.setText("Turn of player " + turn);
    }
}
