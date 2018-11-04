package cn.edu.cugb.information_program.go_test;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.Random;
import java.util.regex.Pattern;


public class FindpasswordActivity extends AppCompatActivity
{
    /***********************************************************************************/
    class TimeCount extends CountDownTimer
    {
        public TimeCount(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);//参数为 总时长,计时的时间间隔
        }

        @Override
        public void onFinish()
        {//计时完毕时触发
            getcode.setText("获取验证码");
            getcode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {//计时过程显示
            getcode.setEnabled(false);
            getcode.setText("等待" + millisUntilFinished / 1000 + "秒");
        }
    }

    /************************************************************************************/

    private EditText mail;
    private EditText password;
    private EditText makesure_password;
    private EditText inputcode;
    private Button getcode;
    private Button submit;
    private String checkcode = "s5k(-@x+%D6E47cj";        //用于判断验证码是否正确的字符串
    private TimeCount time;
    private TextView hint1;
    private TextView hint2;
    private String address = "";
    private String userID = "";
    private String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);

        mail = (EditText) findViewById(R.id.find_userName);         //邮箱控件
        password = (EditText) findViewById(R.id.find_password);    //用户密码控件
        makesure_password = (EditText) findViewById(R.id.find_password2);       //确认密码输入正确控件
        inputcode = (EditText) findViewById(R.id.find_code);         //输入验证码的输入框控件
        getcode = (Button) findViewById(R.id.find_getcode);          //获取验证码的按钮控件
        submit = (Button) findViewById(R.id.find_find);       //注册按钮控件
        hint1 = (TextView) findViewById(R.id.find_somehint);
        hint2 = (TextView) findViewById(R.id.find_somehint2);

        time = new TimeCount(30 * 1000, 1000);    //30*1000毫秒，每1000毫秒计时一次



        getcode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                address = mail.getText().toString();
                pass = password.getText().toString();
                String passcheck2 = makesure_password.getText().toString();
                if (!isEmail(address))
                {
                    Toast.makeText(FindpasswordActivity.this, "邮箱格式有误", Toast.LENGTH_SHORT).show();
                }
                else if(false)      //括号里的false要改成：判断数据库里是否存在这个邮箱
                {
                    Toast.makeText(FindpasswordActivity.this, "该邮箱尚未注册", Toast.LENGTH_SHORT).show();
                }
                else if (!isPassword(pass))
                {
                    Toast.makeText(FindpasswordActivity.this, "密码格式有误", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(passcheck2))
                {
                    Toast.makeText(FindpasswordActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                } else
                {
                    Random ran = new Random();
                    int code = ran.nextInt(9000) + 1000;          //验证码范围,[1000,9999]
                    String str_code = Integer.toString(code);
                    checkcode = str_code;
                    sendMessage("\n找回密码！\n您的验证码是:" + str_code, address);
                    Toast.makeText(FindpasswordActivity.this, "请前往您的邮箱查看验证码", Toast.LENGTH_SHORT).show();

                    hint1.setVisibility(View.VISIBLE);              //设置提示为可见
                    hint2.setVisibility(View.VISIBLE);
                    time.start();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String tocheckcode = inputcode.getText().toString();
                if (tocheckcode.equals(checkcode))
                {
                    //   找回密码完成
                    //   address是用户的邮箱
                    //   pass是密码
                    //   都是字符串类型
                    //   后端根据用户邮箱，找到目标数据，把密码替换一下就行

                    /*****************/


                    /*****************/
                    Toast.makeText(FindpasswordActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                    finish();   //注册完成，销毁活动，退回到登录界面
                } else
                {
                    Toast.makeText(FindpasswordActivity.this, "验证码有误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /************************************************************************************/
    //使用正则表达式判断email格式,密码格式是否正确
    public boolean isEmail(String email)
    {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isPassword(String password)
    {
        String str = "^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,16}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /*************************************************************************************/


    private void sendMessage(final String msg, final String target)    //发邮件的函数，用到了MailSenderInfo  MyAuthenticator  SimpleMailSender三个类
    // msg就是邮件的内容, target是目标邮箱的地址
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                MailSenderInfo mailInfo = new MailSenderInfo();
                mailInfo.setMailServerHost("smtp.163.com");     //smtp.163.com  163邮箱的smtp服务器，如果要用其他邮箱，需要更改这个服务器
                mailInfo.setMailServerPort("25");                //163 smtp服务器的端口
                mailInfo.setValidate(true);
                mailInfo.setUserName("18800168297@163.com");      //输入你的163邮箱账号
                mailInfo.setPassword("yiersan23");// 您的邮箱密码      //163邮箱的授权码（需要打开smtp服务，怎么开启服务请百度）
                mailInfo.setFromAddress("18800168297@163.com");       //邮箱名，和邮箱账号一致就行
                mailInfo.setToAddress(target);         //目标邮箱
                mailInfo.setSubject("感谢您的支持");                    //邮件的标题
                mailInfo.setContent(msg);
                SimpleMailSender sms = new SimpleMailSender();
                boolean isSuccess = sms.sendTextMail(mailInfo);// 发送文体格式
                if (isSuccess)
                {
                    Log.i("go_test", "发送成功");
                } else
                {
                    Log.i("go_test", "发送失败");
                }
            }
        }).start();
    }
}