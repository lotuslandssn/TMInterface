package com.tiamaes.theplatform.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tiamaes.theplatform.R;
import com.tiamaes.theplatform.utils.CollectRms;
import com.tiamaes.theplatform.utils.ServerURL;
import com.tiamaes.theplatform.utils.StringUtils;
import com.tiamaes.theplatform.utils.ToastUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends BaseActivity {
    private long clickTime;
    private String url;
    private static final int LOAD_WEB = 0;
    private static final int CHECK_URL = 1;
    private static final int TOAST = 2;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_WEB:
                    loadWeb();
                    break;
                case CHECK_URL:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (checkURL(url)) {
                                handler.sendEmptyMessage(LOAD_WEB);
                            } else {
                                handler.sendEmptyMessage(TOAST);
                            }
                        }
                    }).start();
                    break;
                case TOAST:
                    dialogMiss();
                    ToastUtils.showShortToast(mContext, "服务器无法连接,请检查配置");
                    break;
                default:
                    break;
            }
        }
    };
    private EditText ed_url;
    private Button btn_login;
    private CollectRms collectRms;
    private EditText ed_port;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        collectRms = new CollectRms(mContext);
        initView();
    }

    private void initView() {
        ed_url = (EditText) findViewById(R.id.login_url);
        ed_url.setText(collectRms.loadData("ip"));
        ed_port = (EditText) findViewById(R.id.login_port);
        ed_port.setText(collectRms.loadData("port"));
        btn_login = (Button) findViewById(R.id.login_btn);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPort()) {
                    dialog = ProgressDialog.show(mContext,"","正在跳转...");
                    dialog.setCancelable(false);
                    url = "http://" + ed_url.getText().toString().trim() + ":"+ed_port.getText().toString().trim()+"/webapp";
                    ToastUtils.showShortToast(mContext, url);
                    handler.sendEmptyMessage(CHECK_URL);
                }
            }
        });
    }

    private boolean checkPort() {
        if (StringUtils.isEmpty(ed_url.getText().toString().trim())) {
            ToastUtils.showShortToast(mContext, "请配置配ip");
            return false;
        }
        if (StringUtils.isEmpty(ed_port.getText().toString().trim())) {
            ToastUtils.showShortToast(mContext, "请配置端口号");
            return false;
        }
        return true;
    }

    /**
     * 跳转加载服务地址
     */
    private void loadWeb() {
        collectRms.saveData("ip",ed_url.getText().toString().trim());
        collectRms.saveData("port",ed_port.getText().toString().trim());
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogMiss();
    }

    private void dialogMiss() {
        if(dialog.isShowing()&&dialog !=null){
            dialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - clickTime) > 2000) {
                ToastUtils.showShortToast(mContext, "再次点击退出应用");
                clickTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param url 服务器地址
     * @return 检查地址连通性
     */
    public boolean checkURL(String url) {
        boolean value = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            int code = conn.getResponseCode();
            System.out.println(">>>>>>>>>>>>>>>> " + code + " <<<<<<<<<<<<<<<<<<");
            if (code != 200) {
                value = false;
            } else {
                value = true;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }
}