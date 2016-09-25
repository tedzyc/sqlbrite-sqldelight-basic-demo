package zyc.squaresql;

import android.content.Context;
import com.github.davidmoten.guavamini.Lists;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module public class MainActivityModule {
  private MainActivity mMainActivity;

  public MainActivityModule(MainActivity mainActivity) {
    mMainActivity = mainActivity;
  }

  @Provides Context provideContext() {
    return mMainActivity;
  }

  @Provides MainActivity.ItemAdapter provideItemAdapter() {
    return new MainActivity.ItemAdapter(Lists.newArrayList());
  }

  @Provides BriteDatabase provideDb(Context context) {
    return SqlBrite.create()
        .wrapDatabaseHelper(HockeyOpenHelper.getInstance(context), Schedulers.io());
  }
}