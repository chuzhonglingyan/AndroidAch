package com.yuntian.basecomponent.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chulingyan
 * @time 2018/12/16 11:31
 * @describe
 */
public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    private OnItemClickListener<T> onItemClickListener;

    private Context context;

    private List<T> data = new ArrayList<>();

    public BaseRvAdapter() {
    }


    public void addData(List<T> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.data.addAll(list);
        notifyItemRangeInserted(this.data.size() - list.size(), list.size());
    }

    public void addItem(T item) {
        if (item == null) {
            return;
        }
        this.data.add(item);
        notifyItemInserted(this.data.size() - 1);
    }


    public void updateData(int startPos, List<T> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        if (startPos < data.size() && startPos >= 0) {
            for (int i = 0; i < list.size(); i++) {
                data.set(startPos + i, list.get(i));
            }
        }
        notifyItemRangeChanged(startPos, list.size());
    }


    public void updateItem(int pos, T item) {
        if (item == null || pos < 0) {
            return;
        }
        if (pos < data.size()) {
            this.data.set(pos, item);
            notifyItemChanged(pos);
        }
    }

    public void removeData(int startPos, int endPos) {
        if (startPos > endPos || startPos < 0) {
            return;
        }
        if (endPos + 1 < data.size()) {
            data.subList(startPos, endPos + 1).clear();
            notifyItemRangeRemoved(startPos, endPos - startPos + 1);
        }
    }


    public void removeList(List<T> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        data.removeAll(list);
        notifyDataSetChanged();
    }


    public void removeItem(int pos) {
        if (pos < 0) {
            return;
        }
        if (pos < data.size()) {
            this.data.remove(pos);
            notifyItemRemoved(pos);
        }
    }


    public void setData(List<T> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.data.clear();
        this.data.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        if (data.size() > 0) {
            return data.get(position);
        }
        return null;
    }


    @NonNull
    @Override
    public BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        BaseViewHolder<T> baseViewHolder = BaseViewHolder.createViewHolder(parent, viewType);
        createItemClickListener(baseViewHolder);
        return baseViewHolder;
    }

    private void createItemClickListener(BaseViewHolder<T> baseViewHolder) {
        if (baseViewHolder != null && onItemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(v -> {
                int position = baseViewHolder.getAdapterPosition();
                if (position < 0) { //视图没及时刷新，获得位置信息可能为-1
                    return;
                }
                if (getItem(position) == null) {
                    return;
                }
                onItemClickListener.onItemClick(baseViewHolder.itemView, getItem(position), position);
            });
        }
    }


    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutId(position);
    }

    protected abstract int getLayoutId(int position);


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        if (getItem(position) != null) {
            holder.bindData(getItem(position), position);
        } else {
            holder.clear();
        }
    }


}