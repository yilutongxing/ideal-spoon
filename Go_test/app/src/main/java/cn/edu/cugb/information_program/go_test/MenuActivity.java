package cn.edu.cugb.information_program.go_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private String[] data={"个人资料","修改密码","通用设置","应用反馈","切换账号" };
    private List<submenu>submenuList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initsumenu();
        submenuAdapter adapter=new submenuAdapter(MenuActivity.this,R.layout.submenu_item,submenuList);
        ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>parent,View view,int position,long id){
                submenu submenu =submenuList.get(position);
                Toast.makeText(MenuActivity.this,submenu.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initsumenu(){
        for (int i=0;i<1;i++){
            submenu apple = new submenu("个人资料",R.drawable.camera_back_default);
            submenuList.add(apple);
            submenu banana = new submenu("修改密码",R.drawable.camera_back_default);
            submenuList.add(banana);
            submenu pear = new submenu("通用设置",R.drawable.camera_back_default);
            submenuList.add(pear);
            submenu cherry = new submenu("应用反馈",R.drawable.camera_back_default);
            submenuList.add(cherry);
            submenu mango = new submenu("切换账号",R.drawable.camera_back_default);
            submenuList.add(mango);
        }
    }
}
