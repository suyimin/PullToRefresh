package com.xdroid.demo.ptr;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xdroid.demo.R;
import com.xdroid.lib.ptr.PtrClassicFrameLayout;
import com.xdroid.lib.ptr.PtrDefaultHandler;
import com.xdroid.lib.ptr.PtrFrameLayout;
import com.xdroid.lib.ptr.loadmore.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView with loadmore
 */
public class ListViewActivity extends AppCompatActivity {
    PtrClassicFrameLayout ptrLayout;
    ListView mListView;
    private List<String> mData = new ArrayList<>();
    private ListViewAdapter mAdapter;
    Handler handler = new Handler();

    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        ptrLayout = (PtrClassicFrameLayout) this.findViewById(R.id.test_list_view_frame);
        mListView = (ListView) this.findViewById(R.id.test_list_view);
        initData();
    }

    private void initData() {
        mAdapter = new ListViewAdapter(this, mData);
        mListView.setAdapter(mAdapter);

        ptrLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrLayout.autoRefresh(true);
            }
        }, 150);

        ptrLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        mData.clear();
                        for (int i = 0; i < 17; i++) {
                            mData.add(new String("ListView item " + i));
                        }
                        mAdapter.notifyDataSetChanged();
                        ptrLayout.refreshComplete();

                        if (!ptrLayout.isLoadMoreEnable()) {
                            ptrLayout.setLoadMoreEnable(true);
                        }
                    }
                }, 1500);
            }
        });


        ptrLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mData.add(new String("Load more " + page));
                        mAdapter.notifyDataSetChanged();
                        ptrLayout.loadMoreComplete(true);
                        page++;
                        Toast.makeText(ListViewActivity.this, "Load more complete", Toast.LENGTH_SHORT)
                                .show();

                        if (page == 2) {
                            //set load more disable
                            ptrLayout.setLoadMoreEnable(false);
                        }
                    }
                }, 1000);
            }
        });
    }

    public class ListViewAdapter extends BaseAdapter {
        private List<String> datas;
        private LayoutInflater inflater;

        public ListViewAdapter(Context context, List<String> data) {
            super();
            inflater = LayoutInflater.from(context);
            datas = data;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listitem_layout, parent, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(datas.get(position));
            return convertView;
        }

    }

}
