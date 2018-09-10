package cn.edu.cugb.information_program.go_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public EditText origin;
    public EditText destination;
    public EditText month;
    public EditText day;
    public EditText number;
    public EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        origin= (EditText) findViewById(R.id.editText2);
        destination= (EditText) findViewById(R.id.editText3);
        number=(EditText) findViewById(R.id.editText4);
        month=(EditText) findViewById(R.id.editText5);
        day=(EditText) findViewById(R.id.editText6);
        comment=(EditText)findViewById(R.id.editText7);
        Button button_matching = (Button) findViewById(R.id.button_matching);
        button_matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "正在匹配，请稍等...", Toast.LENGTH_SHORT).show();
                Intent intent_matching = new Intent(MainActivity.this, MatchingActivity.class);
                String originstr= origin.getText().toString();
                String destinationstr= destination.getText().toString();
                String numberstr= number.getText().toString();
                String monthstr= month.getText().toString();
                String daystr= day.getText().toString();
                String commentstr=comment.getText().toString();
                intent_matching.putExtra("origin",originstr);
                intent_matching.putExtra("destination",destinationstr);
                intent_matching.putExtra("number",numberstr);
                intent_matching.putExtra("month",monthstr);
                intent_matching.putExtra("day",daystr);
                intent_matching.putExtra("comment",commentstr);
                startActivity(intent_matching);
            }
        });
        Button button_menu = (Button) findViewById(R.id.button_menu);
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_menu = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent_menu);
            }
        });
        Button button_introduce = (Button) findViewById(R.id.button_introduce);
        button_introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_introduce = new Intent(MainActivity.this, IntroduceActivity.class);
                startActivity(intent_introduce);
            }
        });
        Button button_myline = (Button) findViewById(R.id.button_myline);
        button_myline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_myline = new Intent(MainActivity.this, MylineActivity.class);
                startActivity(intent_myline);
            }
        });
        Button button_find = (Button) findViewById(R.id.button_find);
        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_find = new Intent(MainActivity.this, FindActivity.class);
                startActivity(intent_find);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //---------------------------------------------
}