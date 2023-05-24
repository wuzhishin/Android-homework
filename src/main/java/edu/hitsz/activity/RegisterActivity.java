package edu.hitsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import edu.hitsz.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etPwd;
    private EditText etPwd1;
    private Button register;
    private String LOGIN_URL = "http://10.0.2.2:8080/LoginInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("注册用户");

        etName = findViewById(R.id.et_re_name);
        etPwd = findViewById(R.id.et_re_pwd);
        etPwd1 = findViewById(R.id.et_ensure_re_pwd);
        register = findViewById(R.id.registering);

        register.setOnClickListener(this);

    }

    public void onClick(View v) {
        final View lv = v;
        final Map<String, String> paramsmap = new HashMap<>();

        //判断两次密码输入是否相同
        String password0 = etPwd.getText().toString();
        String password1 = etPwd1.getText().toString();
        if(!password0.equals(password1)){
            threadRunToToast("两次输入密码不相同");
        }
        else{
            paramsmap.put("username", etName.getText().toString());
            paramsmap.put("password", etPwd.getText().toString());

            new Thread() {
                @Override
                public void run() {
                    String loginresult = "";
                    try {
                        loginresult = LoginByPost(LOGIN_URL, paramsmap);
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                        threadRunToToast("登录时程序发生异常");
                    }

                    ///返回消息
                    Message msg = new Message();
                    msg.what = 0x11;
                    msg.obj = loginresult;
                    handler.sendMessage(msg);
                }

                ;
                Handler handler = new Handler(getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 0x11) {
                            if ("success".equals(msg.obj.toString())) {
                                threadRunToToast("注册成功");
                                //跳转回登录页面
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                Bundle bundle = new Bundle();
                                startActivity(intent);

                            } else if ("failed".equals(msg.obj.toString())) {
                                threadRunToToast("该用户名已被注册");
                            }
                        }
                    }

                };
            }.start();
        }

    }

    /**
     * HttpURLConnection Post式请求
     *   路径：http://192.168.101.10:9090/Login_Server/login
     *   参数：
     *     name=admin
     *     pwd=123456
     */
    private String LoginByPost(String urlStr, Map<String,String> map) {

        StringBuilder result = new StringBuilder();  //StringBuilder用于单线程多字符串拼接，返回参数
        String paramsString = getStringFromEntry(map);  //获取拼接参数：name=admin&pwd=123456

        // 以下是 HttpURLConnection Post 访问 代码
        try{
            // 第一步 包装网络地址
            URL url = new URL(urlStr);
            // 第二步 创建连接对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 第三步 设置请求方式 POST
            conn.setRequestMethod("POST");
            // 第四步 设置读取和连接超时时长
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            // 第五步 允许对外输出
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(true);
            // 第六步 得到输出流 并把实体输出写出去
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            writer.write(paramsString);
            writer.flush();
            writer.close();
            outputStream.close();
            // 第七步 判断请求码是否成功 注意：只有在执行conn.getResponseCode() 的时候才开始向服务器发送请求
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                // 第八步 获取服务器响应的流
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String temp;
                while((temp = reader.readLine()) != null) {
                    result.append(temp);
                }
            }else{
                return "failed";
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            threadRunToToast("登录失败，请检查网络！");
        } catch (IOException e) {
            e.printStackTrace();
            threadRunToToast("IO发生异常");
        }
        return result.toString();
    }

    /**
     * 将map转换成key1=value1&key2=value2的形式
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getStringFromEntry(Map<String, String> map) {

        StringBuilder sb = new StringBuilder(); //StringBuilder用于单线程多字符串拼接
        boolean isFirst = true;
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (isFirst)
                    isFirst = false;
                else
                    sb.append("&");
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 在子线程中提示，属于UI操作
     */
    private void threadRunToToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
