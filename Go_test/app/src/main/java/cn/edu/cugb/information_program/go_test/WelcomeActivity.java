package cn.edu.cugb.information_program.go_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity implements Runnable
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Thread(this).start();
    }

    public void run()
    {
        try
        {
            Thread.sleep(1500);
            SharedPreferences preferences = getSharedPreferences("count", 0);//获取程序的启动次数
            int count = preferences.getInt("count", 0);

            if (count == 0)                  //如果程序是第一次被打开，那么加载引导界面
            {                               //我没有做引导界面，所以还是跳转到主界面
                Intent intent1 = new Intent();
                intent1.setClass(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent1);     //做好了引导界面后把上面的MainAcivity.class换成引导界面就可以了
            } else                            //程序不是第一次被打开，加载主界面
            {
                Intent intent2 = new Intent();
                intent2.setClass(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent2);
            }
            finish();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("count", 1);
            editor.commit();
        }catch (InterruptedException e)
        {
         //假装在捕获异常
        }
    }
}
