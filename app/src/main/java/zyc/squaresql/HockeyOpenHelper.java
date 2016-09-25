package zyc.squaresql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class HockeyOpenHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "trial.db";
  private static final int DATABASE_VERSION = 1;

  private static HockeyOpenHelper instance;

  public static HockeyOpenHelper getInstance(Context context) {
    if (instance == null) {
      instance = new HockeyOpenHelper(context);
    }
    return instance;
  }

  public HockeyOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(Demo.CREATE_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
