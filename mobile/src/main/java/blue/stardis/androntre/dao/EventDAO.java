package blue.stardis.androntre.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EventDAO extends DAOBase {

    public static final String EVENT_TABLE_NAME = "EVENT";
    public static final String EVENT_KEY = "id";
    public static final String EVENT_TITLE = "title";
    public static final String EVENT_TYPE = "type_event";
    public static final String EVENT_DESCRIPTION = "description";
    public static final String EVENT_DATE = "date_event";
    public static final String EVENT_NOTIFY = "notification";


    public EventDAO(Context pContext) {
        super(pContext);
    }
        /*String ADD_EVENT =
                "INSERT INTO " + EVENT_TABLE_NAME + " (" +
                        EVENT_TITLE + " TEXT, " +
                        EVENT_TYPE + " TEXT" +
                        EVENT_DESCRIPTION + " TEXT" +
                        EVENT_DATE + " DATE)" +
                        "VALUES (" +
                        title + ", " +
                        type + ", " +
                        description + ", " +
                        date + ");";

        db.execSQL(ADD_EVENT);*/


    public long addEvent(Event event) {


        ContentValues value = new ContentValues();
        value.put(EVENT_TITLE, event.getTitle());
        value.put(EVENT_TYPE, event.getType());
        value.put(EVENT_DESCRIPTION, event.getDescription());
        //System.out.println(event.getDate().toString());

        Log.d("Date event ", event.getDateEvent());
        value.put(EVENT_DATE, event.getDateEvent());
        value.put(EVENT_NOTIFY, event.isNotification());
        System.out.println(value.toString());
        return mDb.insert(EventDAO.EVENT_TABLE_NAME, null, value);

    }

    public Event getEvent(Long id) {
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + EVENT_TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            Long out_id = cursor.getLong(0);
            String title = cursor.getString(1);
            String type = cursor.getString(2);
            String description = cursor.getString(3);
            String date = cursor.getString(4);
            int notification = cursor.getInt(5);

            return new Event(out_id, title, type, description, date, notification != 0);
        }
        cursor.close();
        return null;
    }

    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {

        return new Date(uDate.getTime());

    }

    public ArrayList<Event> getAllEvent() {
        return cursorToEvents(selectEventAll());
    }


    public void deleteEvent(Event event) {
        mDb.delete(EVENT_TABLE_NAME, EVENT_KEY + " = ?", new String[]{String.valueOf(event.getId())});

    }

    public void updateEventTitle(Event event, String title) {
        ContentValues value = new ContentValues();
        value.put(EVENT_TITLE, title);
        mDb.update(EVENT_TABLE_NAME, value, EVENT_KEY + " = ?", new String[]{String.valueOf(event.getId())});
    }

    public void updateEventType(Event event, String type) {
        ContentValues value = new ContentValues();
        value.put(EVENT_TYPE, type);
        if ((type.contentEquals("Rendez-vous") ||
                type.contentEquals("Travail") ||
                type.contentEquals("Autres") ||
                type.contentEquals("Anniversaire"))) {

            Log.d("error", "Incorrect type");
            return;
        }
        mDb.update(EVENT_TABLE_NAME, value, EVENT_KEY + " = ?", new String[]{String.valueOf(event.getId())});
    }

    public void updateEventDescription(Event event, String description) {
        ContentValues value = new ContentValues();
        value.put(EVENT_DESCRIPTION, description);
        mDb.update(EVENT_TABLE_NAME, value, EVENT_KEY + " = ?", new String[]{String.valueOf(event.getId())});
    }

    public void updateEventDate(Event event, Date date) {
        ContentValues value = new ContentValues();
        value.put(EVENT_DATE, date.toString());
        mDb.update(EVENT_TABLE_NAME, value, EVENT_KEY + " = ?", new String[]{String.valueOf(event.getId())});
    }


    public Cursor selectEventAll() {

        Calendar newCalendar = Calendar.getInstance();
        String event_date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE).format(newCalendar.getTime());
        String query = "select * from " + EVENT_TABLE_NAME + " WHERE " + EVENT_DATE + " > ? ORDER BY " + EVENT_DATE;

        return mDb.rawQuery(query, new String[]{event_date});
    }

    public ArrayList<Event> getEventsByType(String type) {
        ArrayList<Event> list = getAllEvent();

        ArrayList<Event> copy = new ArrayList<>();
        for (Event event : list) {
            if (event.getType().equals(type)) {
                copy.add(event);
            }
        }

        return copy;
    }


    private ArrayList<Event> cursorToEvents(Cursor cursor) {
        ArrayList<Event> allEvent = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String title = cursor.getString(1);
            String type = cursor.getString(2);
            String description = cursor.getString(3);
            String date = cursor.getString(4);
            boolean notification = cursor.getInt(5) != 0;

            allEvent.add(new Event(id, title, type, description, date, notification));
        }
        cursor.close();
        return allEvent;
    }


}
