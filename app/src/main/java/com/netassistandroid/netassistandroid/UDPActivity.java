/**
 * 程序名称：网络调试助手Java版Android端
 * 作       者：周博文
 * 完成日期：2016年11月07日
 * 版       本：V1.0
 */
package com.netassistandroid.netassistandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UDPActivity extends AppCompatActivity {

    public static EditText ed_udp_bdzjdz;
    public static EditText ed_udp_bdzjdk;
    public static EditText ed_udp_neirong;//要发送的内容的编辑框
    public static EditText ed_udp_neirong_receive;//要接收的内容的编辑框

    private Button btn_udp_start;
    private Button btn_udp_close;
    private Button btn_udp_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udp);

        ed_udp_bdzjdz = (EditText) findViewById(R.id.ed_udp_bdzjdz);
        ed_udp_bdzjdk = (EditText) findViewById(R.id.ed_udp_bdzjdk);
        ed_udp_neirong = (EditText) findViewById(R.id.ed_udp_neirong);
        ed_udp_neirong_receive = (EditText) findViewById(R.id.ed_udp_neirong_receive);

        btn_udp_send = (Button) findViewById(R.id.btn_udp_send);
        btn_udp_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNumeric(ed_udp_bdzjdk.getText().toString())){
                    try {
                        UDPsend(ed_udp_bdzjdz.getText().toString(),toNumber(ed_udp_bdzjdk.getText().toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("输入的UDP端口不是数字");
                    //这里可以添加一个Toast提示
                    Toast.makeText(UDPActivity.this, "输入的UDP端口不是数字", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_udp_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPort(ed_udp_bdzjdk.getText().toString())){
                    //开启UDP服务，接收UDP消息的线程
                    UDPReceiveThread.flag = true;//恢复标志位的原本属性，让下一次UDP接收线程可以正常开启
                    UDPReceiveThread udpReceiveThread = new UDPReceiveThread(Integer.parseInt(ed_udp_bdzjdk.getText().toString()));
                    udpReceiveThread.start();//启动接收线程
                    btn_udp_start.setEnabled(false);

                }else {
                    //弹出toast提示
                    Toast.makeText(UDPActivity.this, "请填写正确的端口号1-65535", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_udp_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭UDP服务
                btn_udp_start.setEnabled(true);//设置点击后恢复开启按钮为可点
                UDPReceiveThread.flag = false;//改变标志位，推出循环接收的线程

                /**UDP接收线程阻塞的问题还没有解决*//*********************************************************************************************/

            }
        });


    }

    /**
     * 发送UDP信息的方法
     * @param ip
     * @param port
     * @throws IOException
     */
    public static void UDPsend(String ip,int port) throws IOException {
        System.out.println("UDPsend()方法启动。");

        DatagramSocket ds  = new DatagramSocket();
        byte[] buf = ed_udp_neirong.getText().toString().getBytes(); //获取输入框中的内容
        DatagramPacket dp = new DatagramPacket(buf,buf.length, InetAddress.getByName(ip /*"127.0.0.1"*/),port /*10000*/);//10000为定义的端口
        System.out.println("数据包开始发送。。。");
        ds.send(dp);
        ds.close();
    }

    /**
     * 将字符串转换为数字的方法
     * @param string
     * @return
     */
    public static int toNumber(String string){
        int num = 0;
        num = Integer.parseInt(string);

        return num;
    }

    /**
     * 判断是否为数字的方法，用的是正则表达式
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    /**
     * 判断是否为端口号的方法
     * @param port
     * @return
     */
    public static boolean isPort(String port) {
        boolean flag ;
        if(isNumeric(port) && 0 < Integer.parseInt(port) && 65536 > Integer.parseInt(port)){
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }























}
