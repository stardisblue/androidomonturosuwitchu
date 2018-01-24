package blue.stardis.androntre

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import blue.stardis.androntre.dao.Event
import blue.stardis.androntre.dao.EventDAO

import kotlinx.android.synthetic.main.activity_display_event.*
import kotlinx.android.synthetic.main.content_display_event.*

class DisplayEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_event)
        setSupportActionBar(toolbar)
        val mb = EventDAO(applicationContext)
        mb.open()
        val id: Long = intent.extras["id"] as Long
        Log.d("eventId", id.toString())
        val event: Event? = mb.getEvent(id)

        eventTitle.text = event!!.title
        eventType.text = event.type
        eventDate.text = event.dateEvent
        eventContent.text = event.description
        if (event.isNotification)
            eventNotif.text = "Notification activée"
        else
            eventNotif.text = ""

    }

}
