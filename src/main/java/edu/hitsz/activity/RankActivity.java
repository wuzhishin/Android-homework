package edu.hitsz.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.hitsz.record.MyDBHelper;
import edu.hitsz.record.Ranking;
import edu.hitsz.R;

public class RankActivity extends AppCompatActivity {
    private ListView listv;
    private MyDBHelper myHelper;
    private int Selection = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        listv = (ListView) findViewById(R.id.ListView01);  //初始化ListView
        findViewById(R.id.back).setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
        );

    }

    @Override
    protected void onStart() {
        super.onStart();

        myHelper = MyDBHelper.getInstance(this, 2);
        myHelper.openReadLink();
        if ((readSQLite() == null)|(readSQLite().size()<=0)) {
            showToast("数据库为空。");
            finish();
        } else {
            MyAdapter BAdapter = new MyAdapter(this,readSQLite());//得到一个MyAdapter对象
            listv.setAdapter(BAdapter);  //为ListView绑定Adapter

            // 为ListView的每一个item添加点击事件的监听器
            listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    setTitle("点击了ListView的第 "+(arg2+1)+" 条目" );   //在标题栏中输出点击条目信息
                    Selection = arg2;
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        myHelper.closeLink();
    }

    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }


    /**  读取数据库数据的方法 **/
    protected LinkedList<Ranking> readSQLite() {
        if (myHelper == null) {
            showToast("数据库连接为空。");
            return null;
        }
        LinkedList<Ranking> userArray = myHelper.query("1=1");
        return  userArray;
    }




    // 定义ViewHolder类，存放控件
    private final class ViewHolder {
        private TextView rank;
        private TextView name;
        private TextView score;
        private TextView time;
    }

    // 定义 BaseAdapter适配器的子类
    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;   //声明一个LayoutInfalter对象用来导入布局


        private List<Ranking> list;

        // 构造函数
        public MyAdapter(Context context, List<Ranking> list) {
            this.list = list;
            this.mInflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return list.size();//返回数组的长度
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 动态生成每个下拉项对应的View。每个下拉项View由LinearLayout中包含一个ImageView，
         * 两个内嵌的LinearLayout构成，其中一个包含两个TextView，另一个中包含一个按钮
         **/
        @SuppressLint("SetTextI18n")
        @Override
        public View getView( int position, View convertView, ViewGroup parent) {
            Ranking rank = (Ranking) this.getItem(position);

            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item,null);
                holder = new ViewHolder();
                // 得到各个控件的对象
                holder.rank = (TextView)convertView.findViewById(R.id.rank);
                holder.name = (TextView)convertView.findViewById(R.id.name);
                holder.score = (TextView)convertView.findViewById(R.id.score);
                holder.time = (TextView)convertView.findViewById(R.id.time);

                convertView.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
            }
            holder.rank.setText(Integer.toString(rank.getRank()));
            holder.rank.setTextSize(13);
            holder.name.setText(rank.getUsername());
            holder.name.setTextSize(13);
            holder.score.setText(Integer.toString(rank.getScore()));
            holder.score.setTextSize(13);
            holder.time.setText(rank.getRecordTime());
            holder.time.setTextSize(13);

            findViewById(R.id.del).setOnClickListener(
                    (v)-> {
                        if(Selection == -1){
                            showToast("请先选择删除的数据");
                        }
                        else{
                            String[] whereArgs = {list.get(Selection).getUsername()};
                            myHelper.closeLink();
                            myHelper.openWriteLink();
                            myHelper.delete("name=?", whereArgs);
                            /** 重新读取数据库  **/
                            myHelper.closeLink();
                            myHelper.openReadLink();
                            list = readSQLite();        //重新获取数据
                            notifyDataSetChanged();    //刷新ListView
                        }
                    }
            );

            return convertView;
        }
    }
}
