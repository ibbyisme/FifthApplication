package com.example.fifthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {

    EditText text1;
    Button bt;
    TextView view;
    Handler handler;
    //OutputStream os;
    ClientThread clientThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (EditText) findViewById(R.id.edittext);
        bt = (Button) findViewById(R.id.button);
        view = (TextView) findViewById(R.id.textView);

        //Socket s;
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0x123) {
                        view.append("\n"+msg.obj.toString());
                    }
                }
            };

            //clientThread=new ClientThread(handler);
       /* try{
            s=new Socket("192.168.1.2",20000);
            new Thread(new ClientThread(s,handler)).toString();
            os=s.getOutputStream();
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
            clientThread = new ClientThread(handler);
            final Thread thread = new Thread(clientThread);
            thread.start();
            //sleep(10000);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Message msg = new Message();
                        msg.what = 0x345;
                        msg.obj = text1.getText().toString();
                        clientThread.toHandler.sendMessage(msg);
                        //os.write((text1.getText().toString()+"\r\n").getBytes("utf-8"));
                        text1.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
}
