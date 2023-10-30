package com.example.nguoidulich20;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.nguoidulich20.Model.*;

public class FindWayActivity extends AppCompatActivity {
    private TextView tvGraph, tvWay;
    private Button btnReturn;
    private int[][] graph ;
    private int numCities ;
    private boolean[] visited;
//    private TextView[] textViewArray;
    private double minCost = Integer.MAX_VALUE;
    static ArrayList<Integer> bestPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findway_activity);

        this.tvGraph = findViewById(R.id.graph);
        this.tvWay = findViewById(R.id.bestWay);
        this.btnReturn = findViewById(R.id.btnReturn);
        Intent intent1 = getIntent();
        CityDistances ct = intent1.getParcelableExtra("graph");
        this.graph = ct.getDistances();

        TextView[] textViewArray = new TextView[graph.length];

        String strGraph = "";
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                strGraph=strGraph+graph[i][j]+"    ";

            }
            strGraph+="\n";
        }
        this.tvGraph.setText(strGraph);
        numCities = graph.length;
        thebestway();
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindWayActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }
    void thebestway(){
            visited = new boolean[numCities];
            visited[0] = true;
            bestPath = new ArrayList<>(Arrays.asList(0));
            tsp(0, 1, new ArrayList<>(Arrays.asList(0)));
            String bestway;
            if (bestPath.size()<numCities+1){
                 bestway ="Không tìm được đường đi!";
            }
            else {
                 bestway ="Best path: "+ bestPath.toString();
            }

            this.tvWay.setText(bestway);
        }
    void tsp(int currentCity, int count, ArrayList<Integer> path) {
        GridLayout gridLayout = findViewById(R.id.graphContainer);
        gridLayout.setColumnCount(2);

        if (count == numCities) {
            if (graph[currentCity][0] > 0 ) {

                bestPath = new ArrayList<>(path);
                bestPath.add(0);
            }
            return;
        }

        for (int nextCity = 0; nextCity < numCities; nextCity++) {
            if (!visited[nextCity] && graph[currentCity][nextCity] > 0) {
                visited[nextCity] = true;

                ArrayList<Integer> newPath = new ArrayList<>(path);
                newPath.add(nextCity);

                //show step
                TextView textView = new TextView(this);

                textView.setText(newPath.toString());


                // Tạo LayoutParams cho TextView để đặt kích thước cột
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Chia đều các cột
                textView.setLayoutParams(params);
                gridLayout.addView(textView);

                //tiep tuc thuat toan

                tsp(nextCity, count + 1, newPath);
                visited[nextCity] = false;
            }
        }
    }
//    void tsp(int currentCity, int count, int cost, ArrayList<Integer> path) {
//
//        if (count == numCities) {
//            if (graph[currentCity][0] > 0 && cost + graph[currentCity][0] < minCost) {
//                minCost = cost + graph[currentCity][0];
//                bestPath = new ArrayList<>(path);
//                bestPath.add(0);
//            }
//            return;
//        }
//
//        for (int nextCity = 0; nextCity < numCities; nextCity++) {
//            if (!visited[nextCity] && graph[currentCity][nextCity] > 0) {
//                visited[nextCity] = true;
//
//                ArrayList<Integer> newPath = new ArrayList<>(path);
//                newPath.add(nextCity);
//
//                tsp(nextCity, count + 1, cost + graph[currentCity][nextCity], newPath);
//                visited[nextCity] = false;
//            }
//        }
//    }
    private void drawGridLayout(int numCities, TextView[] textViewArray){
        GridLayout gridLayout = findViewById(R.id.graphContainer);

        gridLayout.setColumnCount(2);

        for (int i = 0; i < numCities; i++) {
            TextView textView = new TextView(this);
            int city = i+1;
            textView.setText("Thành phố " + city);
            textViewArray[i] = textView;

            // Tạo LayoutParams cho TextView để đặt kích thước cột
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Chia đều các cột

            textView.setLayoutParams(params);

            gridLayout.addView(textView);
        }

    }
   
    private void drawLineBetweenTextViews(TextView textView1, TextView textView2) {
        // Lấy tọa độ x, y của TextView1 và TextView2
        int[] location1 = new int[2];
        int[] location2 = new int[2];
        textView1.getLocationOnScreen(location1);
        textView2.getLocationOnScreen(location2);

        // Tạo một ImageView để vẽ đường thẳng
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(getResources().getColor(android.R.color.black)); // Màu của đường thẳng
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2)); // Độ dày và chiều rộng của đường thẳng

        // Tính toán tọa độ của ImageView để vẽ đường thẳng giữa TextView1 và TextView2
        int startX = location1[0] + textView1.getWidth() / 2;
        int startY = location1[1] + textView1.getHeight() / 2;
        int endX = location2[0] + textView2.getWidth() / 2;
        int endY = location2[1] + textView2.getHeight() / 2;

        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);

        int left = Math.min(startX, endX);
        int top = Math.min(startY, endY);

        // Đặt các thuộc tính của ImageView để vẽ đường thẳng
        imageView.setX(left);
        imageView.setY(top);
        imageView.setRotation(getAngle(startX, startY, endX, endY));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        // Thêm ImageView vào ViewGroup (LinearLayout hoặc RelativeLayout)
        GridLayout layout = findViewById(R.id.graphContainer); // Thay thế bằng ID của layout chứa các TextView
        layout.addView(imageView);
    }

    private float getAngle(float x1, float y1, float x2, float y2) {
        float angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

//    private void drawGraph(double[][] distances) {
//
//        LinearLayout graphContainer = findViewById(R.id.graphContainer);
//        GraphView graphView = new GraphView(this, distances);
//        graphContainer.addView(graphView);
//    }
//    private class GraphView extends View {
//        private double[][] distances;
//
//        public GraphView(Context context, double[][] distances) {
//            super(context);
//            this.distances = distances;
//        }
//
//        @Override
//        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
//
//
//            Paint paint = new Paint();
//            paint.setColor(Color.BLACK);
//            paint.setStrokeWidth(2);
//
//
//            for (int i = 0; i < numCities; i++) {
//                for (int j = 0; j < numCities; j++) {
//                    if (i != j) {
//                        int startX = i * 50;
//                        int startY = j * 50;
//                        int endX = (i + 1) * 50;
//                        int endY = (j + 1) * 50;
//                        float distance = (float) distances[i][j];
//
//                        canvas.drawLine(startX, startY, endX, endY, paint);
//                        canvas.drawText(String.format("%.2f", distance), (startX + endX) / 2, (startY + endY) / 2, paint);
//                    }
//                }
//            }
//        }
//    }
}
