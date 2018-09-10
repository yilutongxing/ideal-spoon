package cn.edu.cugb.information_program.go_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class judgement {
    String Loginusername="0";
    String Loginpassword="0";
    String Loginjg = "1";
}

public class LoginActivity extends AppCompatActivity
{
    TextView v ;
    judgement lg = new judgement();
    private EditText userName;
    private EditText password;
    private Button   login;
    private Button   zhuce;
    private ProgressBar progress_login;
    private Random random;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPassword;
    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        v = (TextView)findViewById(R.id.ttt);
        userName = (EditText) findViewById(R.id.et_userName);
        password = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.btn_login);
        zhuce = (Button) findViewById(R.id.btn_zhuce);
        progress_login = (ProgressBar)findViewById(R.id.login_progress) ;
        random = new Random();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPassword = (CheckBox)findViewById(R.id.check_remember);
        boolean isRemembered = pref.getBoolean("remembered?",false);
        if(isRemembered)
        {
            String str_usernamere = pref.getString("username","");          //  username re--remembered
            String str_passwordre = pref.getString("password","");
            userName.setText(str_usernamere);
            password.setText(str_passwordre);
            rememberPassword.setChecked(true);
        }
        zhuce.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        /************************************************************************************/

        login.setOnClickListener(new View.OnClickListener()                     //登录按钮点击事件
        {
            @Override
            public void onClick(View view)
            {
                //从后端获取用户名和密码的数据

                final String str_username = userName.getText().toString();
                String str_password = password.getText().toString();
                HttpUtil.sendLoginRequest(str_username,str_password,new HttpCallbackListener()
                {
                    @Override
                    public void onFinish(String response) {
                        if (response.toString().equals("11")) {
                            lg.Loginusername = "1";
                            lg.Loginpassword = "1";
                        }
                    }
                    public void onError(Exception e){
                        e.printStackTrace();
                    }
                });
                try {
                    Thread.currentThread().sleep(100);//阻断0.1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ("1"==lg.Loginusername&&"1"==lg.Loginpassword)                      //如果用户名正确
                {
                    User.username=str_username;
                    editor = pref.edit();
                    if(rememberPassword.isChecked())
                    {
                        editor.putBoolean("remembered?",true);
                        editor.putString("username",str_username);
                        editor.putString("password",str_password);
                    }
                    else
                    {
                        editor.clear();
                    }
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "正在登陆，请稍后...", Toast.LENGTH_SHORT).show();
                    progress_login.setVisibility(View.VISIBLE);
                    timer.schedule(task,random.nextInt(900)+200);     //模拟登陆耗时
                } else
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
    TimerTask task = new TimerTask()
    {
        @Override
        public void run()
        {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            timer.cancel();
            finish();
        }
    };
}


