/**
 * 程序名称：网络调试助手Java版Android端
 * 作       者：周博文
 * 完成日期：2016年11月07日
 * 版       本：V1.0
 */

package com.netassistandroid.netassistandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_udp;
    private Button btn_tcp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_tcp = (Button) findViewById(R.id.btn_tcp);
        btn_udp = (Button) findViewById(R.id.btn_udp);

        btn_udp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UDPActivity.class);
                startActivity(intent);
            }
        });

        btn_tcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TCPActivity.class);
                startActivity(intent);
            }
        });


    }
}
