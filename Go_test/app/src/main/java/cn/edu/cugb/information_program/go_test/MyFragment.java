package cn.edu.cugb.information_program.go_test;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment {
    private int choice;

    public MyFragment(int choice) {
        this.choice = choice;
    }


    private String[] data = {"个人资料", "修改密码", "通用设置", "应用反馈", "切换账号"};
    private List<submenu> submenuList = new ArrayList<>();
    private void initsumenu() {
        for (int i = 0; i < 1; i++) {
            submenu apple = new submenu("个人资料", R.drawable.camera_back_default);
            submenuList.add(apple);
            submenu banana = new submenu("修改密码", R.drawable.camera_back_default);
            submenuList.add(banana);
            submenu pear = new submenu("通用设置", R.drawable.camera_back_default);
            submenuList.add(pear);
            submenu cherry = new submenu("应用反馈", R.drawable.camera_back_default);
            submenuList.add(cherry);
            submenu mango = new submenu("切换账号", R.drawable.camera_back_default);
            submenuList.add(mango);
        }
    }

    private List<matching> matchingList = new ArrayList<matching>();
    public EditText origin;
    public EditText destination;
    public EditText month;
    public EditText day;
    public EditText number;
    public EditText comment;
    public static String []usernames;
    private void initusers() {
        matching []a=new matching[usernames.length];
        for(int j=0;j<usernames.length;j++) {
            a[j] = new matching(usernames[j]);
            matchingList.add(a[j]);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /********************************************************************************************/


        /********************************************************************************************/
        /********************************************************************************************/
        switch (choice) {
            case 1:
                View view = inflater.inflate(R.layout.mainview1, container, false);
                /********************************************************/

                /********************************************************/
                return view;
            case 2:
                View view2 = inflater.inflate(R.layout.mainview2, container, false);
                MatchingAdapter adapter2 = new MatchingAdapter(getActivity(), R.layout.user_item, matchingList);
                ListView listView2 = (ListView) view2.findViewById(R.id.list_view);
                listView2.setAdapter(adapter2);
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        matching user = matchingList.get(position);
                        Intent intent_introduce = new Intent(getActivity(), introduction.class);
                        intent_introduce.putExtra("username", user.getName());
                        startActivity(intent_introduce);
                    }
                });


                /*
                origin = (EditText) view2.findViewById(R.id.editText2);
                destination = (EditText) view2.findViewById(R.id.editText3);
                number = (EditText) view2.findViewById(R.id.editText4);
                month = (EditText) view2.findViewById(R.id.editText5);
                day = (EditText) view2.findViewById(R.id.editText6);
                comment = (EditText) view2.findViewById(R.id.editText7);
                Button button_matching = (Button) view2.findViewById(R.id.button_matching);

                button_matching.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "正在匹配，请稍等...", Toast.LENGTH_SHORT).show();
                        Intent intent_matching = new Intent(getActivity(), MatchingActivity.class);
                        String originstr= origin.getText().toString();
                        String destinationstr= destination.getText().toString();
                        String numberstr= number.getText().toString();
                        String monthstr= month.getText().toString();
                        String daystr= day.getText().toString();
                        String commentstr=comment.getText().toString();
                        intent_matching.putExtra("origin",originstr);
                        intent_matching.putExtra("destination",destinationstr);
                        intent_matching.putExtra("number",numberstr);
                        intent_matching.putExtra("month",monthstr);
                        intent_matching.putExtra("day",daystr);
                        intent_matching.putExtra("comment",commentstr);
                        startActivity(intent_matching);
                    }
                });*/
                /********************************************************/
                return view2;
            case 3:
                View view3 = inflater.inflate(R.layout.mainview3, container, false);
                /********************************************************/
                origin = (EditText) view3.findViewById(R.id.editText2);
                destination = (EditText) view3.findViewById(R.id.editText3);
                number = (EditText) view3.findViewById(R.id.editText4);
                month = (EditText) view3.findViewById(R.id.editText5);
                day = (EditText) view3.findViewById(R.id.editText6);
                comment = (EditText) view3.findViewById(R.id.editText7);
                Button button_matching = (Button) view3.findViewById(R.id.button_matching);

                button_matching.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "正在保存，请稍等...", Toast.LENGTH_SHORT).show();
                        String originstr = origin.getText().toString();
                        String destinationstr = destination.getText().toString();
                        String numberstr = number.getText().toString();
                        String monthstr = month.getText().toString();
                        String daystr = day.getText().toString();
                        String commentstr = comment.getText().toString();
                        String date = "2018-" + monthstr + "-" + daystr;

                        HttpUtil.sendTravelInformation(originstr, destinationstr, numberstr, date, commentstr, new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                if (response != "") {
                                    usernames = response.split(" ");
                                    initusers();
                                }
                            }
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }
                        });
                        try {
                            Thread.currentThread().sleep(100);//阻断0.1秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity(), "保存成功！您可以在匹配行程中查看您的所有匹配", Toast.LENGTH_SHORT).show();

                    }
                });
                /********************************************************/
                return view3;
            case 4:
                View view4 = inflater.inflate(R.layout.mainview4, container, false);
                /********************************************************/
                initsumenu();
                submenuAdapter adapter4 = new submenuAdapter(getActivity(), R.layout.submenu_item, submenuList);
                ListView listView4 = (ListView) view4.findViewById(R.id.list_view);
                listView4.setAdapter(adapter4);
                listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        submenu submenu = submenuList.get(position);
                        Toast.makeText(getActivity(), submenu.getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
                /********************************************************/
                return view4;
        }
        View view5 = inflater.inflate(R.layout.mainview_error, container, false);
        return view5;
    }
    /************************************************************************************************/
}

