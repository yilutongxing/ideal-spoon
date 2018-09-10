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

import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.Random;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RegisterActivity extends AppCompatActivity
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
    TextView v;
    private EditText mail;
    private EditText id;
    private EditText password;
    private EditText makesure_password;
    private EditText inputcode;
    private Button getcode;
    private Button register;
    private Button findpassword;
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
        setContentView(R.layout.activity_register);
        v=(TextView)findViewById(R.id.test);
        mail = (EditText) findViewById(R.id.register_userName);         //邮箱控件
        id = (EditText) findViewById(R.id.register_userName2);          //用户ID控件
        password = (EditText) findViewById(R.id.register_password);    //用户密码控件
        makesure_password = (EditText) findViewById(R.id.register_password2);       //确认密码输入正确控件
        inputcode = (EditText) findViewById(R.id.register_code);         //输入验证码的输入框控件
        getcode = (Button) findViewById(R.id.register_getcode);          //获取验证码的按钮控件
        register = (Button) findViewById(R.id.register_register);       //注册按钮控件
        findpassword=(Button) findViewById(R.id.register_findpassword);
        hint1 = (TextView) findViewById(R.id.register_somehint);
        hint2 = (TextView) findViewById(R.id.register_somehint2);
        time = new TimeCount(30 * 1000, 1000);    //30*1000毫秒，每1000毫秒计时一次
        findpassword.setOnClickListener(new View.OnClickListener()          //找回密码
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this,FindpasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });


        getcode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                address = mail.getText().toString();
                userID = id.getText().toString();
                pass = password.getText().toString();
                String passcheck2 = makesure_password.getText().toString();
                if (!isEmail(address))
                {
                    Toast.makeText(RegisterActivity.this, "邮箱格式有误", Toast.LENGTH_SHORT).show();
                }
                //else if(!iscugbEmail(address)){
                  //  Toast.makeText(RegisterActivity.this, "邮箱类型有误", Toast.LENGTH_SHORT).show();}
                else if (getWordCount(userID) < 4)
                {
                    Toast.makeText(RegisterActivity.this, "用户名格式有误", Toast.LENGTH_SHORT).show();
                } else if (!isPassword(pass))
                {
                    Toast.makeText(RegisterActivity.this, "密码格式有误", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(passcheck2))
                {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                } else
                {
                    Random ran = new Random();
                    int code = ran.nextInt(9000) + 1000;          //验证码范围,[1000,9999]
                    String str_code = Integer.toString(code);
                    checkcode = str_code;
                    sendMessage("\n感谢您的注册！\n您的验证码是:" + str_code, address);
                    Toast.makeText(RegisterActivity.this, "请前往您的邮箱查看验证码", Toast.LENGTH_SHORT).show();

                    hint1.setVisibility(View.VISIBLE);              //设置提示为可见
                    hint2.setVisibility(View.VISIBLE);
                    time.start();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String tocheckcode = inputcode.getText().toString();
                if (!tocheckcode.equals(checkcode))//邮箱验证可能又不太行了，先这样试吧
                {
                    //   此时验证已经通过
                    //   address是邮箱
                    //   userID是用户名
                    //   pass是密码
                    //   都是字符串类型
                    //   后端就在这写把用户数据存到数据库里的代码就行了

                    //   说明：后端查询individual_information表中是否有该用户，
                    // if用户没有填写个人信息，在登录的时候转到填写个人信息的活动，else直接登录
                    /*****************///
                    User.username=userID;
                    HttpUtil.sendRegisterRequest(userID,pass,address,new HttpCallbackListener()
                    {
                        @Override
                        public void onFinish(String response) {}
                        public void onError(Exception e){
                            e.printStackTrace();
                    }
                    });
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this,GetinfoActivity.class);
                    startActivity(intent);

                    /*****************/
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();   //注册完成，销毁活动，退回到上一个活动
                } else
                {
                    Toast.makeText(RegisterActivity.this, "验证码有误", Toast.LENGTH_SHORT).show();
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
    public boolean iscugbEmail(String email){
        return email.endsWith("@cugb.edu.cn");
    }
    public boolean isPassword(String password)
    {
        String str = "^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,16}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    //获取用户名的字节数，用于判断是否符合规则
    public static int getWordCount(String s)
    {
        int length = 0;
        for (int i = 0; i < s.length(); i++)
        {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
                length++;
            else
                length += 2;
        }
        return length;
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
                mailInfo.setPassword("yiersan123");// 您的邮箱密码      //163邮箱的授权码（需要打开smtp服务，怎么开启服务请百度）
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
