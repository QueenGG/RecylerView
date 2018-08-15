package com.bawei.recylerview.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.bawei.recylerview.R;
import com.bawei.recylerview.adapter.SubAdapter;
import com.bawei.recylerview.bean.BookBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView re_content;
    public static final String URL_STRING="https://www.zhaoapi.cn/product/searchProducts?keywords=笔记本&page=1";
    public static final int STOLVE_DATA=100;
    MainActivity Context;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case STOLVE_DATA:
                    String responString = (String) msg.obj;
                    Gson gson = new Gson();
                    BookBean bookBean = gson.fromJson(responString, BookBean.class);
                    List<BookBean.DataBean> data = bookBean.getData();
                    SubAdapter subAdapter = new SubAdapter(Context, (ArrayList<BookBean.DataBean>) data);

                    subAdapter.setonItemclickLinsten(new SubAdapter.onItemclickLinsten() {
                        @Override
                        public void onclick(int layoutPosition) {
                            Toast.makeText(Context, "第" + layoutPosition + "的条目被点击", Toast.LENGTH_SHORT).show();
                        }
                    });

                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

                    re_content.setAdapter(subAdapter);
                     re_content.setLayoutManager(manager);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();

        requestData();
    }

    private void requestData() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Request request = new Request.Builder().url(URL_STRING).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String errorString = e.getMessage().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, errorString, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responString = response.body().string();
                Message message = handler.obtainMessage();
                message.what=STOLVE_DATA;
                message.obj=responString;
                handler.sendMessage(message);
            }
        });
    }

    private void initview() {
        re_content = findViewById(R.id.re_content);
        Context=this;
    }
}
