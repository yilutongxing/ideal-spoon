package cn.edu.cugb.information_program.go_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MatchingActivity extends AppCompatActivity {

    private List<matching> matchingList = new ArrayList<matching>();
    public EditText origin;
    public EditText destination;
    public EditText month;
    public EditText day;
    public EditText number;
    public EditText comment;
    public static String []usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        Intent intent = getIntent();
        String origin = intent.getStringExtra("origin");
        String destination = intent.getStringExtra("destination");
        String number = intent.getStringExtra("number");
        String month = intent.getStringExtra("month");
        String day = intent.getStringExtra("day");
        String comment=intent.getStringExtra("comment");
        String date="2018-"+month+"-"+day;
       HttpUtil.sendTravelInformation(origin,destination,number,date,comment,new HttpCallbackListener()
        {
            @Override
            public void onFinish(String response) {
                if(response!=""){
                usernames=response.split(" ");
                initusers();}
            }public void onError(Exception e){
                e.printStackTrace();
              }
        });
        try {
            Thread.currentThread().sleep(100);//阻断0.1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MatchingAdapter adapter = new MatchingAdapter(MatchingActivity.this, R.layout.user_item,  matchingList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                matching user = matchingList.get(position);
                Intent intent_introduce = new Intent(MatchingActivity.this, introduction.class);
                intent_introduce.putExtra("username",user.getName());
                startActivity(intent_introduce);
            }
        });
    }
    private void initusers() {
       matching []a=new matching[usernames.length];
        for(int j=0;j<usernames.length;j++) {
            a[j] = new matching(usernames[j]);
            matchingList.add(a[j]);
        }
    }
}

