package zyc.squaresql;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.sqlbrite.BriteDatabase;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

  @Bind(R.id.rv_full) RecyclerView mRvFull;
  @Bind(R.id.rv_filtered) RecyclerView mRvFiltered;

  @Inject ItemAdapter mFullAdapter;
  @Inject ItemAdapter mFilteredAdapter;
  @Inject BriteDatabase mDatabase;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);
    DaggerMainActivityComponent.builder()
        .mainActivityModule(new MainActivityModule(this))
        .build()
        .inject(this);

    mRvFull.setAdapter(mFullAdapter);
    mRvFiltered.setAdapter(mFilteredAdapter);
    bindDivider(mRvFull);
    bindDivider(mRvFiltered);

    Observable.create((Observable.OnSubscribe<Boolean>) subscriber -> {
      final BriteDatabase.Transaction transaction = mDatabase.newTransaction();
      try {
        mDatabase.delete(Demo.TABLE_NAME, null);// clear all rows
        insert("A", 1);
        insert("B", 2);
        insert("C", 1);
        insert("D", 2);
        insert("E", 1);
        transaction.markSuccessful();
      } finally {
        transaction.end();
      }
      if (!subscriber.isUnsubscribed()) {
        subscriber.onNext(true);
        subscriber.onCompleted();
      }
    })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(result -> {
          if (result) {
            queryThenShow(mFullAdapter, Demo.SELECT_ALL);
          }
        });
  }

  @OnClick(R.id.btn_filter) public void onClick() {
    queryThenShow(mFilteredAdapter, Demo.SELECT_BY_VALUE, new String[] { "1" });
  }

  private void bindDivider(RecyclerView recyclerView) {
    recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
  }

  private void queryThenShow(Action1<List<Demo>> handler, String clause, String... args) {
    mDatabase.createQuery(Demo.TABLE_NAME, clause, args)
        .mapToList(cursor -> Demo.MAPPER.map(cursor))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(handler);
  }

  private void insert(String name, int value) {
    mDatabase.insert(Demo.TABLE_NAME,
        Demo.FACTORY.marshal().name(name).value(value).asContentValues());
  }

  ////////////////////////////////////////////////////////////////////////////////////////////

  public static class ItemAdapter extends RvAdapter<Demo, ItemAdapter.ViewHolder>
      implements Action1<List<Demo>> {
    public ItemAdapter(List<Demo> data) {
      super(data);
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ViewHolder(inflateView(R.layout.rv_item, parent));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
      holder.bind(mList.get(position));
    }

    @Override public void call(List<Demo> demo) {
      setData(demo);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
      @Bind(R.id.tv_name) TextView mTvName;
      @Bind(R.id.tv_value) TextView mTvValue;

      public ViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
      }

      public void bind(Demo demo) {
        mTvName.setText(demo.name());
        mTvValue.setText(String.valueOf(demo.value()));
      }
    }
  }
}
