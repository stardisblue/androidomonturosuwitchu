package blue.stardis.androntre.dao;

import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class Event {
    private long id;
    private String title;
    private String type_event;
    private String description;
    private String date_event; // format YYYY_MM_DD
    private boolean notification; // format YYYY_MM_DD


    public long getId() {
        return id;
    }

    public Event(String title, String type, String description, String date_event, boolean notification) {
        /*
        if ((type.contentEquals("RDV") ||
                type.contentEquals("Travail") ||
                type.contentEquals("Anniversaire"))){

            Log.d("error", "Incorrect type");
            return;
        }

        if (date.before(Calendar.getInstance().getTime())){
            Log.d("error", "Incorrect date before now");
            return;
        }*/

        //this.id = id;
        this.title = title;
        this.type_event = type;
        this.description = description;
        this.date_event = date_event;
        this.notification = notification;


    }

    public Event(long id, String title, String type, String description, String date_event, boolean notification) {
        /*
        if ((type.contentEquals("RDV") ||
                type.contentEquals("Travail") ||
                type.contentEquals("Anniversaire"))){

            Log.d("error", "Incorrect type");
            return;
        }

        if (date.before(Calendar.getInstance().getTime())){
            Log.d("error", "Incorrect date before now");
            return;
        }*/

        this.id = id;
        this.title = title;
        this.type_event = type;
        this.description = description;
        this.date_event = date_event;
        this.notification = notification;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type_event;
    }

    public void setType(String type) {
        this.type_event = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateEvent() {
        return date_event;
    }

    public void setDateEvent(String date) {
        this.date_event = date;
    }


    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }
}
