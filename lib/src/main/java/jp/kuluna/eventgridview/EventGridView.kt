package jp.kuluna.eventgridview

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import jp.kuluna.eventgridview.databinding.ViewEventGridBinding
import java.util.*


class EventGridView : FrameLayout {
    private val binding: ViewEventGridBinding

    var adapter: EventGridAdapter?
        get() = binding.eventGridRecyclerView.adapter as? EventGridAdapter
        set(value) {
            binding.eventGridRecyclerView.adapter = value
            value?.onScaleRefreshListener = {
                binding.overTime = it
            }
        }

    var titleAdapter = TitleAdapter()

    var hourHeight: Int
        get() {
            return binding.hourHeight ?: 40
        }
        set(value) {
            binding.hourHeight = value
        }

    var titleHeight: Int
        get() {
            return binding.titleHeight ?: 80
        }
        set(value) {
            binding.titleHeight = value
        }

    /**
     * 今のイベントを全てクリアして引数で渡すイベントに差し替えます。
     * @param events イベントリスト
     */
    fun replace(events: List<Event>, day: Date) {
        adapter?.replace(events, day)
        val groups = events.groupBy { it.groupId }.toList()
        val titles = mutableListOf<String>()
        for (group in groups) {
            titles.add(group.second.first().groupTitle)
        }
        titleAdapter.replace(titles)
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_event_grid, this, true)

        binding.groupTitle.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupTitle.adapter = titleAdapter

        binding.eventGridRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.eventGridRecyclerView.setOnTouchListener { _, event ->
            return@setOnTouchListener when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    adapter?.hideAllAdjustButton()
                    false
                }
                else -> false
            }
        }

        val scrollListeners = arrayOfNulls<RecyclerView.OnScrollListener>(2)
        scrollListeners[0] = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.eventGridRecyclerView.removeOnScrollListener(scrollListeners[1])
                binding.eventGridRecyclerView.scrollBy(dx, dy)
                binding.eventGridRecyclerView.addOnScrollListener(scrollListeners[1])
            }
        }
        scrollListeners[1] = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.groupTitle.removeOnScrollListener(scrollListeners[0])
                binding.groupTitle.scrollBy(dx, dy)
                binding.groupTitle.addOnScrollListener(scrollListeners[0])
            }
        }
        binding.groupTitle.addOnScrollListener(scrollListeners[0])
        binding.eventGridRecyclerView.addOnScrollListener(scrollListeners[1])
    }
}
