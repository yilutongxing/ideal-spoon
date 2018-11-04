package cn.edu.cugb.information_program.go_test;

/**
 * Created by Administrator on 2018/5/7.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);


}
