package cn.edu.cugb.information_program.go_test;

import android.content.Intent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class introduction extends AppCompatActivity {
    private Button  invite;
    private String dateStr,realname,sexvalue,birth,introduction,majorstr;
    private TextView name, phone, major, introduce,sex;
    private TextView birthday;
    private static String information[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        invite=(Button) findViewById(R.id.info_submit);
        final Intent intent_introduce=getIntent();
        String username=intent_introduce.getStringExtra("username");
        TextView tv=(TextView)findViewById(R.id.theme);
        tv.setText(username+"的个人信息");
        Intent intent = getIntent();
        String currentname = intent.getStringExtra("username");
        HttpUtil.getOthersInformation(currentname,new HttpCallbackListener()
        {
            @Override
            public void onFinish(String response) {
                information=response.split(" ");
            }
            public void onError(Exception e){
            e.printStackTrace();}
        });
        try {
            Thread.currentThread().sleep(100);//阻断0.1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发送请求从individual_information表读取username的信息,读取realname，phone，major，introduction，sex，birth
        realname=information[0];
        birth=information[2];
        sexvalue=information[3];
        majorstr=information[4];
        introduction=information[5];
        name=(TextView)findViewById(R.id.info_name);
        name.setText("真实姓名："+realname);
        major=(TextView)findViewById(R.id.info_major);
        major.setText("专业："+majorstr);
        sex=(TextView)findViewById(R.id.info_sex);
        sex.setText("性别："+sexvalue);
        birthday=(TextView)findViewById(R.id.info_birthday);
        birthday.setText("生日："+birth);
        introduce=(TextView)findViewById(R.id.info_introduce);
        introduce.setText("个人简介："+introduction);
        invite.setOnClickListener(new View.OnClickListener()          //找回密码
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(introduction.this, "已发送邀请", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
