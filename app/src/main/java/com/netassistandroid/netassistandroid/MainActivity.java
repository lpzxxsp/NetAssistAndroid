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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    private Button btn_udp;
    private Button btn_tcp;
    private TextView tv_bjip;

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

        tv_bjip = (TextView) findViewById(R.id.tv_bjip);
        tv_bjip.setText("本机IP：" + getIP());

    }

    /**
     * 获取本机的IP地址
     * @return
     */
    public static String getIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }


}
