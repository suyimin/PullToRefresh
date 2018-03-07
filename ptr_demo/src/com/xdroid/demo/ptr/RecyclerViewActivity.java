package com.xdroid.demo.ptr;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xdroid.demo.R;
import com.xdroid.lib.ptr.PtrClassicFrameLayout;
import com.xdroid.lib.ptr.PtrDefaultHandler;
import com.xdroid.lib.ptr.PtrFrameLayout;
import com.xdroid.lib.ptr.loadmore.OnLoadMoreListener;
import com.xdroid.lib.ptr.recyclerview.RecyclerAdapterWithHF;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView with loadmore
 */
public class RecyclerViewActivity extends AppCompatActivity {
    PtrClassicFrameLayout ptrLayout;
    RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<>();
    private RecyclerAdapter adapter;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();

    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);

        ptrLayout = (PtrClassicFrameLayout) this.findViewById(R.id.test_recycler_view_frame);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.test_recycler_view);
        init();
    }

    private void init() {
        adapter = new RecyclerAdapter(this, mData);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
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
                            mData.add(new String("RecyclerView item  " + i));
                        }
                        mAdapter.notifyDataSetChanged();
                        ptrLayout.refreshComplete();
                        ptrLayout.setLoadMoreEnable(true);
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
                        Toast.makeText(RecyclerViewActivity.this, "Load more complete", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }

    public class RecyclerAdapter extends Adapter<ViewHolder> {
        private List<String> datas;
        private LayoutInflater inflater;

        public RecyclerAdapter(Context context, List<String> data) {
            super();
            inflater = LayoutInflater.from(context);
            datas = data;
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            ChildViewHolder holder = (ChildViewHolder) viewHolder;
            holder.itemTv.setText(datas.get(position));
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
            View view = inflater.inflate(R.layout.listitem_layout, null);
            return new ChildViewHolder(view);
        }

    }

    public class ChildViewHolder extends ViewHolder {
        public TextView itemTv;

        public ChildViewHolder(View view) {
            super(view);
            itemTv = (TextView) view;
        }

    }
}
