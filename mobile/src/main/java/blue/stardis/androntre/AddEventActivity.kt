package blue.stardis.androntre

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.NotificationCompat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import blue.stardis.androntre.dao.Event
import blue.stardis.androntre.dao.EventDAO
import kotlinx.android.synthetic.main.activity_add_event.*
import kotlinx.android.synthetic.main.content_add_event.*
import java.text.SimpleDateFormat
import java.util.*


class AddEventActivity : AppCompatActivity() {


    var listOfItems = arrayOf("RDV", "Anniversaire", "Evenement")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        setSupportActionBar(toolbar)

        eventType!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // use position to know the selected item
                //Snackbar.make(view, "You selected :" + listOfItems[position], Snackbar.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfItems)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        eventType!!.adapter = aa

        val myCalendar = Calendar.getInstance()

        eventDate.setOnClickListener {
            DatePickerDialog(this,
                    { _, year, monthOfYear, dayOfMonth ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val sdf = SimpleDateFormat.getDateInstance()
                        eventDate.setText(sdf.format(myCalendar.time))
                    },
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        eventTime.setOnClickListener {
            TimePickerDialog(this,
                    { _, hour, minute ->
                        myCalendar.set(Calendar.HOUR, hour)
                        myCalendar.set(Calendar.MINUTE, minute)
                        eventTime.setText(String.format("%02d:%02d", hour, minute))
                    },
                    myCalendar.get(Calendar.HOUR),
                    myCalendar.get(Calendar.MINUTE),
                    true).show()
        }

        fab.setOnClickListener { view ->
            val title: String = eventTitle.text.toString()
            val type: String = eventType.selectedItem.toString()
            val date: String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE).format(myCalendar.time)
            val description: String = content.text.toString()
            val notification = suwitchu.isChecked

            Log.d("FABClick", "titre : $title type: $type date: $date description: $description notification : $notification")


            if (title == "" || type == "") {
                Snackbar.make(view, "Champs non remplis", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val event = Event(title, type, description, date, notification)
            val mb = EventDAO(applicationContext)
            mb.open()
            val id: Int = mb.addEvent(event).toInt()

            if (notification) {
                Log.d("DELAY", myCalendar.timeInMillis.toString())
                scheduleNotification(myCalendar.timeInMillis, id, title, description)
            }

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun scheduleNotification(delay: Long, notificationId: Int, title: String, content: String) {//delay is after how much time(in millis) from current time you want to schedule the notification
        /*val notification = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .build()

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(0, notification)
*/
        // DOES NOT WORK ???
        val notificationIntent = Intent(applicationContext, MyNotificationPublisher::class.java)

        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_CONTENT, content)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_TITLE, title)

        Log.d("TEST", notificationIntent.extras.toString())

        val pendingIntent = PendingIntent.getBroadcast(applicationContext, notificationId, notificationIntent, 0)
        val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, delay, pendingIntent)
    }
}
