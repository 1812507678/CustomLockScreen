package com.example.mywebsocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mywebsocket.bean.JsonBase;
import com.example.mywebsocket.bean.OnlineUser;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private WebSocketClient mWebSocketClient;
    private String address = "ws://192.168.252.3:8080/WebTest/websocket";
    private boolean isStartRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            initSocketClient();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }



    }

    //初始化WebSocketClient
    /**
     *
     * @throws URISyntaxException
     */
    private void initSocketClient() throws URISyntaxException {
        if(mWebSocketClient == null) {
            mWebSocketClient = new WebSocketClient(new URI(address)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    //连接成功
                    Log.i(TAG,"opened connection");

                    String testIconUrl = "http://p1.music.126.net/iOB_WWmen6-mH7z0aba5AQ==/3444769932752065.jpg?param=180y180";
                    OnlineUser onlineUser = new OnlineUser(testIconUrl,"测试用户",1);
                    Gson gson = new Gson();
                    JsonBase jsonBase = new JsonBase();
                    jsonBase.setRet(0);
                    jsonBase.setErrDesc(onlineUser);

                    String msg = gson.toJson(jsonBase);

                    sendMsg(msg);

                }


                @Override
                public void onMessage(String s) {
                    //服务端消息
                    Log.i(TAG,"received:" + s);

                    Gson gson = new Gson();
                    JsonBase jsonBase = gson.fromJson(s, JsonBase.class);
                    Log.i(TAG,"jsonBase："+jsonBase.toString());
                    if (jsonBase.getRet()==1){
                        //开始实时数据传输

                        startRealTimeDataTrasmit();
                    }
                }


                @Override
                public void onClose(int i, String s, boolean remote) {
                    //连接断开，remote判定是客户端断开还是服务端断开
                    Log.i(TAG,"Connection closed by " + ( remote ? "remote peer" : "us" ) + ", info=" + s);
                    //
                    //closeConnect();
                }


                @Override
                public void onError(Exception e) {
                    Log.i(TAG,"error:" + e);
                }
            };
        }
    }

    private void startRealTimeDataTrasmit() {
        final JsonBase jsonBase = new JsonBase();
        final Gson gson = new Gson();
        jsonBase.setRet(1);
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (isStartRunning){
                    try {
                        String data = "data:"+new Date().toString();
                        jsonBase.setErrDesc(data);
                        sendMsg(gson.toJson(jsonBase));
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }


    //连接
    private void connect() {
        new Thread(){
            @Override
            public void run() {
                mWebSocketClient.connect();
            }
        }.start();
    }


    //断开连接
    private void closeConnect() {
        try {
            mWebSocketClient.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            mWebSocketClient = null;
        }
    }


    //发送消息
        /**
         *
         */
    private void sendMsg(String msg) {
        mWebSocketClient.send(msg);
    }

    public void senMsg(View view) {
        sendMsg("android client");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

    public void startRun(View view) {
        connect();
        isStartRunning = true;
    }

    public void stopRun(View view) {
        //closeConnect();
        isStartRunning = false;
    }




    /*//增长率
    private void sendOnLineMsgToServer() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();

        params.addBodyParameter("iconUrl","http://p1.music.126.net/iOB_WWmen6-mH7z0aba5AQ==/3444769932752065.jpg?param=180y180");
        params.addBodyParameter("username","测试用户");
        params.addBodyParameter("onlinestate","1");

        String url  = "http://192.168.252.3:8080/AmsuClothMonitor/deleteOnlineUserAction";
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String result = responseInfo.result;
                Log.i(TAG,"上传onSuccess==result:"+result);
                *//*Gson gson = new Gson();
                JsonBase jsonBase = gson.fromJson(result, JsonBase.class);
                Log.i(TAG,"jsonBase:"+jsonBase);
                if (jsonBase.getRet()==0){
                    Log.i(TAG,"在线成功");

                }*//*
            }

            @Override
            public void onFailure(HttpException e, String s) {


                Log.i(TAG,"上传onFailure==s:"+s);
            }
        });



    }*/
}
