package zyc.squaresql;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class RvAdapter<T, VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {
  protected List<T> mList;

  public RvAdapter(List<T> data) {
    setData(data);
  }

  public List<T> getData() {
    return mList;
  }

  public void setData(List<T> data) {
    mList = data;
    notifyDataSetChanged();
  }

  public void sort(Comparator<T> comparator) {
    Collections.sort(mList, comparator);
    notifyDataSetChanged();
  }

  protected View inflateView(int itemViewLayoutId, ViewGroup parent) {
    return LayoutInflater.from(parent.getContext()).inflate(itemViewLayoutId, parent, false);
  }

  @Override public int getItemCount() {
    return mList.size();
  }

  public T getItemData(int position) {
    return mList.get(position);
  }
}
