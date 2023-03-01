package com.yung.android.common.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yung.android.common.R;
import com.yung.android.common.entity.PageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/02/28
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class PageItemsAdapter extends RecyclerView.Adapter<PageItemsAdapter.ViewHolder> {

    private List<PageItem> mDatas = new ArrayList<>();
    private Context mContext;

    public PageItemsAdapter(Context context, List<PageItem> datas) {
        this.mDatas.addAll(datas);
        this.mContext = context;
    }

    @NonNull
    @Override
    public PageItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(com.yung.android.common.R.layout.list_item_example, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull PageItemsAdapter.ViewHolder holder, int position) {

        PageItem pageItem = mDatas.get(position);

        holder.textView.setText(pageItem.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(pageItem.getPath())
                        .withString("list_json", pageItem.getListJson())
                        .navigation();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name);
        }
    }
}
