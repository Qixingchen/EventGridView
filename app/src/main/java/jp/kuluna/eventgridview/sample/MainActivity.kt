package jp.kuluna.eventgridview.sample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import jp.kuluna.eventgridview.Event
import jp.kuluna.eventgridview.EventGridAdapter
import jp.kuluna.eventgridview.sample.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: EventGridAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adapter = EventGridAdapter(this)
        binding.eventGridView.adapter = adapter
        binding.eventGridView.hourHeight = 60
        binding.eventGridView.titleHeight = 40
        binding.eventGridView.groupWidth = 40
        binding.eventGridView.post {
            binding.eventGridView.eventGridView.scrollToHour(6)
        }

        showEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.reloadButton -> {
                showEvents()
            }
        }
        return true
    }

    private fun showEvents() {

        binding.eventGridView.groupWidth = Random().nextInt(40) + 40

        Toast.makeText(this, binding.eventGridView.groupWidth.toString(), Toast.LENGTH_LONG).show()

        val period = 3
        val startedAt = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 11)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.time
        val endedAt = Calendar.getInstance().apply {
            time = startedAt
            add(Calendar.HOUR_OF_DAY, period)
        }.time
        val gridColor = ContextCompat.getColor(this, android.R.color.holo_blue_light)
        val blackColor = ContextCompat.getColor(this, android.R.color.black)

        val events: List<Event> = ArrayList<Event>().apply {
            add(Event(
                    0,
                    "1",
                    startedAt,
                    endedAt,
                    "ドラッグ不可",
                    gridColor,
                    blackColor,
                    null,
                    null,
                    false))

            add(Event(
                    1,
                    "2",
                    startedAt,
                    endedAt,
                    "ドラッグ可",
                    gridColor,
                    blackColor,
                    null,
                    null,
                    true))
        }

        binding.eventGridView.eventGridView.replace(events, Date())
    }
}
