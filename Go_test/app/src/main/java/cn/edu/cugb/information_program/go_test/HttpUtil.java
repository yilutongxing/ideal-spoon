package cn.edu.cugb.information_program.go_test;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/5/7.
 */

public class HttpUtil {
    public static String ip = "172.20.51.69";
    public static void sendRegisterRequest(final String username, final String password, final String address,final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://"+ip+"/TEST/Register.php");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    String data = "username=" + username + "&password=" + password + "&address="+address;
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
                    if (listener != null)
                        listener.onFinish(response.toString());
                } catch (Exception e) {
                    if (listener != null)
                        listener.onError(e);
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }
            }
        }).start();
    }

    public static void sendLoginRequest(final String username, final String password,final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://"+ip+"/TEST/LOGIN.php");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    String data = "username=" + username + "&password=" + password;
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
                    if (listener != null)
                        listener.onFinish(response.toString());
                } catch (Exception e) {
                    if (listener != null)
                        listener.onError(e);
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }
            }
        }).start();
    }
    public static void sendTravelInformation(final String origin,final String destination,final String number,
                                       final String date,final String comment,final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://"+ip+"/TEST/Travelinformation_test.php");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
              //      Log.d("aaa","1");
                    String data ="username="+User.username+"&origin=" + origin+ "&destination=" + destination+
                            "&number="+number+ "&date="+date+"&comment="+comment;
                    os.write(data.getBytes());
                    InputStream in = connection.getInputStream();
               //     Log.d("aaa","2");
                    // 下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();//最好还能读取用户的comment，应该可以用php把username和comment连成一个字符串（中间用‘\n’隔开）
                    String line;
              //      Log.d("aaa","3");
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
               //     Log.d("aaa","4");
            if(listener !=null)
                listener.onFinish(response.toString());
              //  Log.d("aaa","5");}
        }catch (Exception e){
            if(listener !=null)
                listener.onError(e);
        }finally {
            if(connection !=null)
                connection.disconnect();
                 }
            }
        }).start();
    }

    public static void getOthersInformation(final String username,final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://"+ip+"/TEST/getOthersInformation.php");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    String data ="username="+username;
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
                    if(listener !=null)
                        listener.onFinish(response.toString());
                }catch (Exception e){
                    if(listener !=null)
                        listener.onError(e);
                }finally {
                    if(connection !=null)
                        connection.disconnect();
                }
            }
        }).start();
    }
    public static void savePersonalInformation(final String namevalue,final String phonevalue,final String birthdayvalue, final String sexvalue,
                                               final String  majorvalue,final String introducevalue,final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://"+ip+"/TEST/PersonalMessage_Save.php");
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
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if(listener !=null)
                        listener.onFinish(response.toString());
                }catch (Exception e){
                    if(listener !=null)
                        listener.onError(e);
                }finally {
                    if(connection !=null)
                        connection.disconnect();
                }
            }
        }).start();
    }
}