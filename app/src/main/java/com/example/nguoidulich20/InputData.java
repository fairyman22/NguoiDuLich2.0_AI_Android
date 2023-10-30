package com.example.nguoidulich20;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nguoidulich20.Model.*;

import java.util.ArrayList;

public class InputData extends AppCompatActivity {

    private LinearLayout edt;
    private int[][] data;
    private EditText[][] listEdt ;
    private Spinner[][] listSpinner;
    private int numcities;
    private Button submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_input_data);

            this.edt = findViewById(R.id.edt);
            Intent intent = getIntent();
            String strNumcities = intent.getStringExtra("numcities");

            numcities = Integer.parseInt(strNumcities);
//            this.listEdt = new EditText[numcities][numcities];
//            this.data = new double[numcities][numcities];
//            this.submit = findViewById(R.id.submit);
//        for (int i = 0; i < numcities; i++) {
//            for (int j = i+1; j < numcities; j++) {
//                EditText editText = new EditText(this);
//
//
//                editText.setHint("Distance from City " + (i+1)  + " to City " + (j+1));
//                editText.setId(i+j);
//                listEdt[i][j] = editText;
//                edt.addView(editText);
//            }
//        }
        this.listSpinner = new Spinner[numcities][numcities];
        this.data = new int[numcities][numcities];
        this.submit = findViewById(R.id.submit);

        for (int i = 0; i < numcities; i++) {
            for (int j = i+1; j < numcities; j++) {
                Spinner spinner = new Spinner(this);
                TextView textView = new TextView(this);
                textView.setText("Select value from City " + i + " to City " + j);


                String[] items = {"0", "1"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setPrompt("Chọn khoảng cách từ thành phố " + (i + 1) + " đến thành phố " + (j + 1));
                spinner.setId(i + j);
                listSpinner[i][j] = spinner;
                edt.addView(textView);
                edt.addView(spinner);
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                Intent intent1 = new Intent(InputData.this, FindWayActivity.class);


                CityDistances cityDistances = new CityDistances(data);
                intent1.putExtra("graph",cityDistances);
                startActivity(intent1);

            }
        });

    }
//    private void setData(){
//        for (int i = 0; i < numcities; i++) {
//            data[i][i]=0;
//            for (int j = i+1; j <numcities ; j++) {
//                String tmp = this.listEdt[i][j].getText().toString();
//                if (!tmp.isEmpty()) {
//                    double dst = Double.parseDouble(tmp);
//                    data[i][j]=dst;
//                    data[j][i]=dst;
//                }
//
//            }
//        }
//    }
private void setData() {
    for (int i = 0; i < numcities; i++) {
        data[i][i] = 0;
        for (int j = i + 1; j < numcities; j++) {
            String selectedItem = listSpinner[i][j].getSelectedItem().toString();
            int value = Integer.parseInt(selectedItem);
            data[i][j] = value;
            data[j][i] = value;
        }
    }
}


}

