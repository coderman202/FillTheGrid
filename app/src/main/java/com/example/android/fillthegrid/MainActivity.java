package com.example.android.fillthegrid;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner chooseColour;

    TextView current, record;

    GridView gridview;

    String[] coloursAmount, goodLuckMessage;



    ButtonAdapter buttonAdapter = new ButtonAdapter(this);

    static final String STATE_CURRENT_TOTAL = "CurrentTotal";
    static final String STATE_RECORD_SCORE = "RecordScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        current = (TextView) findViewById(R.id.current_total);
        record = (TextView) findViewById(R.id.record_score);

        //Get colours array from strings.xml
        coloursAmount = getResources().getStringArray(R.array.set_colours);

        goodLuckMessage = getResources().getStringArray(R.array.good_luck_message);

        chooseColour = (Spinner) findViewById(R.id.set_colours);

        //Add array to Spinner
        addToSpinner(chooseColour, coloursAmount);

        //Add the listener to the spinner
        addListenerOnSpinnerItemSelection(chooseColour);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(buttonAdapter);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "g" + position, Toast.LENGTH_SHORT).show();

                Button btn = (Button) parent.getChildAt(position);
                btn.setBackgroundResource(R.drawable.buttonbg9);

                Drawable col = parent.getChildAt(position).getBackground();
                Drawable col2 = parent.getChildAt(position+1).getBackground();
                if(col == col2){
                    Toast.makeText(MainActivity.this, "It's a match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Add array to spinners
    public void addToSpinner(Spinner spin, String[] list) {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);
    }

    //Get the selected dropdown list value
    public void addListenerOnSpinnerItemSelection(Spinner spin) {

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, goodLuckMessage[position], Toast.LENGTH_SHORT).show();

                buttonAdapter.setDifficultyLevel(position+3);
                gridview.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }

        });
    }
}
