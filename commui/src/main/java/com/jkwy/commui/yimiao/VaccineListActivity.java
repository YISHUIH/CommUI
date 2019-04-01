package com.jkwy.commui.yimiao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import com.jkwy.commui.R;
import com.jkwy.commui.base.BasicActivity;
import com.jkwy.commui.yimiao.entry.VaccineList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangzejin on 2016/9/22.
 * 功能说明：疫苗时间列表
 */

public class VaccineListActivity extends BasicActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, VaccineListActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }
    private ListView mListView;
    private MyAdapter mAdapter;

    private List<VaccineList> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_list);
        initView();
        initData();

    }

    /**
     * 从assets中读取json文件获取内容
     */
    private void initData() {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("vaccine.json"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                Result += line;

            }

            mList = JSON.parseArray(Result, VaccineList.class);
            mAdapter.addAll(mList);
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("VaccineListActivity", "File not found");
        }
    }

    private void initView() {
        super.initViewTitle();
        super.tv_title_name.setText("疫苗时间表");
        super.img_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView = (ListView) findViewById(R.id.listView1);
        mListView.setAdapter(mAdapter = new MyAdapter());
    }

    class MyAdapter extends BaseAdapter {
        //标题
        public static final int VIEW_TYPE_TITLE = 0;
        //疫苗
        public static final int VIEW_TYPE_DATA = 1;

        public List<VaccineList.Vaccine> data = new ArrayList<>();

        public void addAll(List<VaccineList> list) {
            for (VaccineList vaccineList : list) {
                VaccineList.Vaccine vaccine = new VaccineList.Vaccine();
                vaccine.setTitle(vaccineList.getTime());
                data.add(vaccine);
                data.addAll(vaccineList.getList());
            }


        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if (TextUtils.isEmpty(data.get(position).getTitle())) {
                return VIEW_TYPE_DATA;
            } else {
                return VIEW_TYPE_TITLE;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder = null;
            if (convertView == null) {
                switch (getItemViewType(position)) {
                    case VIEW_TYPE_DATA:
                        holder = new Holder(parent.getContext(), R.layout.view_item_vaccine2, VIEW_TYPE_DATA);
                        break;
                    case VIEW_TYPE_TITLE:
                        holder = new Holder(parent.getContext(), R.layout.view_item_vaccine, VIEW_TYPE_TITLE);
                        break;
                    default:
                }
                convertView = holder.root;
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            final VaccineList.Vaccine vaccine = data.get(position);
            switch (getItemViewType(position)) {
                case VIEW_TYPE_DATA:
                    holder.name.setText(vaccine.getName());
                    holder.count.setText(vaccine.getCount());
                    holder.effect.setText(vaccine.getEffect());
                    holder.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            VaccineDetActivity.start(VaccineListActivity.this, vaccine.getTitle(), vaccine);
                        }
                    });
                    break;
                case VIEW_TYPE_TITLE:
                    holder.time.setText(vaccine.getTitle());
                    break;
                default:
            }
            return convertView;
        }

        class Holder {
            private View root;
            private TextView time;

            private TextView name, count, effect;
            private ImageView haveTo;

            public Holder(Context ctx, int r, int viewType) {
                root = View.inflate(ctx, r, null);
                switch (viewType) {
                    case VIEW_TYPE_DATA:
                        name = (TextView) root.findViewById(R.id.vaccine_name_tv);
                        count = (TextView) root.findViewById(R.id.vaccine_count_tv);
                        effect = (TextView) root.findViewById(R.id.vaccine_effect_tv);
                        haveTo = (ImageView) root.findViewById(R.id.vaccine_have_to_iv);

                        break;
                    case VIEW_TYPE_TITLE:
                        time = (TextView) root.findViewById(R.id.vaccine_time_tv);
                        break;
                    default:
                }


            }
        }

    }


}
