package cn.edu.cugb.information_program.go_test;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class GetinfoActivity extends AppCompatActivity
{
    private Button setbirthday, submit;
    private String dateStr, mouth, day;
    private EditText name, phone, major, introduce;
    private TextView birthday;
    private RadioGroup sex;
    private RadioButton boy, girl;
    private String namevalue, phonevalue, majorvalue, birthdayvalue, sexvalue, introducevalue;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getinfo);
        setbirthday = (Button) findViewById(R.id.info_setbirthday);
        submit = (Button) findViewById(R.id.info_submit);
        name = (EditText) findViewById(R.id.info_name);
        phone = (EditText) findViewById(R.id.info_phone);
        major = (EditText) findViewById(R.id.info_major);
        introduce = (EditText) findViewById(R.id.info_introduce);
        birthday = (TextView) findViewById(R.id.info_birthday);
        sex = (RadioGroup) findViewById(R.id.info_sex);
        boy = (RadioButton) findViewById(R.id.boy);
        girl = (RadioButton) findViewById(R.id.gril);
        final Calendar calender = Calendar.getInstance();
        boy.setChecked(true);

        setbirthday.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dialog = new
                        DatePickerDialog(GetinfoActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int
                            dayOfMonth)
                    {

                        if (monthOfYear <= 9)
                        {
                            mouth = "0" + (monthOfYear + 1);
                        } else
                        {
                            mouth = String.valueOf(monthOfYear + 1);
                        }
                        if (dayOfMonth <= 9)
                        {
                            day = "0" + dayOfMonth;
                        } else
                        {
                            day = String.valueOf(dayOfMonth);
                        }
                        dateStr = String.valueOf(year) + "-" + mouth + "-" + day;
                        birthday.setText(dateStr);
                    }
                }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),
                        calender.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (name.length() == 0 || phone.length() == 0 || major.length() == 0 || introduce.length() == 0 || birthday.length() == 0)
                {
                    Toast.makeText(GetinfoActivity.this, "请输入完整有效的信息", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    namevalue=name.getText().toString();
                    majorvalue=major.getText().toString();
                    birthdayvalue=birthday.getText().toString();
                    introducevalue=introduce.getText().toString();
                    if (isMobileNO(phone.getText().toString()))
                    {
                        phonevalue = phone.getText().toString();
                        if (boy.isChecked())
                            sexvalue = "Boy";
                        else if (girl.isChecked())
                            sexvalue = "Girl";

                        /************************************/
                        HttpUtil.savePersonalInformation(namevalue,phonevalue,birthdayvalue,sexvalue,majorvalue,introducevalue,new HttpCallbackListener()
                        {
                            @Override
                            public void onFinish(String response) {}
                            public void onError(Exception e){
                                e.printStackTrace();
                            }
                        });
                        /*
                            namevalue是姓名
                            phonevalue是电话号码
                            birtydayvalue是生日
                            introducevalue是个人简介
                            sexvalue是性别
                            majorvalue是专业
                            因为就在我们学校里用，也就没让用户给学校信息
                            全是String类型，在下面的分界线以上存到数据库里就行
                         */
                        /****************    分界线   *******************/
                        Intent intent = new Intent();
                        intent.setClass(GetinfoActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(GetinfoActivity.this, "电话号码格式有误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String getTime(Date date)
    {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }//数据库那边应该还用不上

    public static boolean isMobileNO(String str) throws PatternSyntaxException
    {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    public void Sendpersonalmessage(final String namevalue,final String phonevalue,final String birthdayvalue,
                                    final String sexvalue,final String  majorvalue,final String introducevalue) {
        String []usernames;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://172.20.1.125/TEST/PersonalMessage_Save.php");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    String data ="username="+User.username+"&name="+namevalue+"&phone=" + phonevalue+ "&birthday="
                            + birthdayvalue+ "&sex="+sexvalue+ "&major="+majorvalue+"&introduce="+introducevalue;
                    os.write(data.getBytes());
                    InputStream in = connection.getInputStream();
                    // 下面对获取到的输入流进行读取
                    reader = new BufferedReader(
                            new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();//最好还能读取用户的comment，应该可以用php把username和comment连成一个字符串（中间用‘\n’隔开）
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}

