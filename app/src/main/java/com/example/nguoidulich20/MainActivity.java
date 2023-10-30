package com.example.nguoidulich20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.nguoidulich20.Model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText numcities;
    private Button next;
    private Button chooseFileButton;
    private static final int READ_REQUEST_CODE = 42;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numcities = findViewById(R.id.numCitiesEditText);
        next = findViewById(R.id.showChartButton);
        chooseFileButton = findViewById(R.id.chooseFileButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = numcities.getText().toString();
                String error = "Sô lượng thanh phố nhập vào phải lớn hơn 1";
                if (Integer.parseInt(numcities.getText().toString())<=1){
                    Toast.makeText(view.getContext(), error,Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, InputData.class);
                    intent.putExtra("numcities",num);
                    startActivity(intent);
                }

            }
        });
        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();

            }
        });
    }
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                // Đọc nội dung của file và tạo mảng số nguyên 2 chiều từ đây
                int[][] integerArray = readArrayFromInputStream(inputStream);
                Intent intent1 = new Intent(this, FindWayActivity.class);


                CityDistances cityDistances = new CityDistances(integerArray);
                intent1.putExtra("graph",cityDistances);
                startActivity(intent1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private int[][] readArrayFromInputStream(InputStream inputStream) throws IOException {
        // Đọc nội dung của file và chuyển nó thành mảng số nguyên 2 chiều
        // Cách chuyển đổi phụ thuộc vào định dạng dữ liệu trong tệp của bạn.
        // Đây là một ví dụ đơn giản:

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<int[]> rows = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(" "); // Phân tách các giá trị trong mỗi dòng
            int[] row = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                row[i] = Integer.parseInt(values[i]);
            }
            rows.add(row);
        }

        int numRows = rows.size();
        int numCols = rows.get(0).length;
        int[][] result = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            result[i] = rows.get(i);
        }

        return result;
    }

}