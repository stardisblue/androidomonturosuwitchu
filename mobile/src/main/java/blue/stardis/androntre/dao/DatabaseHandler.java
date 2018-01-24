package blue.stardis.androntre.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.util.Calendar;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String EVENT_KEY = "id";
    public static final String EVENT_TITLE = "title";
    public static final String EVENT_TYPE = "type_event";
    public static final String EVENT_DESCRIPTION = "description";
    public static final String EVENT_DATE = "date_event";
    public static final String EVENT_NOTIFY = "notification";

    public static final String EVENT_TABLE_NAME = "EVENT";

    public static final String EVENT_TABLE_CREATE =
            "CREATE TABLE " + EVENT_TABLE_NAME + " (" +
                    EVENT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EVENT_TITLE + " TEXT, " +
                    EVENT_TYPE + " TEXT," +
                    EVENT_DESCRIPTION + " TEXT," +
                    EVENT_DATE + " DATETIME, " +
                    EVENT_NOTIFY + " BOOLEAN );";

    public static final String EVENT_TABLE_DROP = "DROP TABLE IF EXISTS " + EVENT_TABLE_NAME + ";";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Run ", "onCreate: ");
        db.execSQL(EVENT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EVENT_TABLE_DROP);
        onCreate(db);
    }
}
