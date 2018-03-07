package com.xdroid.lib.ptr.loadmore;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 加载更多布局切换接口
 */
public interface ILoadMoreViewFactory {

    public ILoadMoreView madeLoadMoreView();

    /**
     * ListView底部加载更多的布局切换
     *
     * @author Chanven
     */
    public interface ILoadMoreView {

        /**
         * 初始化
         *
         * @param footViewHolder
         * @param onClickLoadMoreListener 加载更多的点击事件，需要点击调用加载更多的按钮都可以设置这个监听
         */
        public void init(FootViewAdder footViewHolder, OnClickListener onClickLoadMoreListener);

        /**
         * 显示普通布局
         */
        public void showNormal();

        /**
         * 显示已经加载完成，没有更多数据的布局
         */
        public void showNomore();

        /**
         * 显示正在加载中的布局
         */
        public void showLoading();

        /**
         * 显示加载失败的布局
         *
         * @param e
         */
        public void showFail(Exception e);

        public void setFooterVisibility(boolean isVisible);

    }

    public static interface FootViewAdder {

        public View addFootView(View view);

        public View addFootView(int layoutId);

    }

}
