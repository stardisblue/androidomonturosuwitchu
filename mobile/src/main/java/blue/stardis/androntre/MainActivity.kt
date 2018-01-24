package blue.stardis.androntre

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import blue.stardis.androntre.dao.Event
import blue.stardis.androntre.dao.EventDAO

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.collections.ArrayList
import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.os.SystemClock
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Context
import android.media.RingtoneManager
import android.graphics.drawable.BitmapDrawable
import android.support.v4.app.NotificationCompat


class MainActivity : AppCompatActivity() {
    private val mListOfItems = arrayOf("Tous", "RDV", "Anniversaire", "Evenement")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // DAO
        val mb = EventDAO(applicationContext)
        mb.open()
        val events: ArrayList<Event> = mb.allEvent


        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, mListOfItems)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner_nav!!.adapter = aa

        /*
         *List management
         */
        // use a linear layout manager
        val mLayoutManager = LinearLayoutManager(this)
        list_events.layoutManager = mLayoutManager

        // specify an adapter (see also next example)
        val mAdapter = MyAdapter(events)
        list_events.adapter = mAdapter

        fab.setOnClickListener { _ ->
            startActivity(Intent(applicationContext, AddEventActivity::class.java))
        }


        /*
         * navigation choice menu
         */
        spinner_nav!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // use position to know the selected item
                // Snackbar.make(view, "You selected :" + mListOfItems[position], Snackbar.LENGTH_SHORT).show()
                mAdapter.events.clear()
                if (position == 0) {
                    mAdapter.events.clear()
                    mAdapter.events.addAll(mb.allEvent)
                } else {
                    mAdapter.events.addAll(mb.getEventsByType(mListOfItems[position]))
                }
                Log.d("arraylist", mAdapter.events.toString())
                mAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

    }

    class MyAdapter(eventsSet: ArrayList<Event>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {


        val events = eventsSet

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v), OnClickListener {

            var id: Long = 0
            var title: TextView = v.findViewById(R.id.title)
            var description: TextView = v.findViewById(R.id.content)
            var date: TextView = v.findViewById(R.id.date)
            var type: TextView = v.findViewById(R.id.type)
            var notif: TextView = v.findViewById(R.id.notif)

            init {
                v.setOnClickListener(this)
            }

            override fun onClick(view: View) {
                //Snackbar.make(view!!, "Clicked on $id", Snackbar.LENGTH_SHORT).show()
                val intent = Intent(view.context, DisplayEventActivity::class.java).putExtra("id", id)
                view.context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            // create a new view
            val v: View = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.event_item, parent, false)
            // set the view's size, margins, paddings and layout parameters
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            val event: Event = events[position]
            holder.id = event.id
            holder.title.text = event.title
            holder.description.text = event.description
            holder.date.text = event.dateEvent
            holder.type.text = event.type
            if (event.isNotification)
                holder.notif.text = "Notification activée"
            else
                holder.notif.text = ""
        }

        override fun getItemCount(): Int {
            return events.size
        }
    }
}
