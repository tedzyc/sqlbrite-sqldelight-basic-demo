package zyc.squaresql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DemoOpenHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "trial.db";
  private static final int DATABASE_VERSION = 1;

  private static DemoOpenHelper instance;

  public static DemoOpenHelper getInstance(Context context) {
    if (instance == null) {
      instance = new DemoOpenHelper(context);
    }
    return instance;
  }

  public DemoOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(Demo.CREATE_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
