package jp.kuluna.eventgridview

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.kuluna.eventgridview.databinding.ItemTitleBinding

/**
 * Created by Qi Xingchen on 2018-7-16.
 */
open class TitleAdapter : RecyclerView.Adapter<ViewHolder>() {

    val titles = mutableListOf<String>()

    fun widthChange() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTitleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_title, null, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindVH(titles.getOrElse(position) { "" })
    }

    fun replace(newTitle: MutableList<String>) {
        titles.clear()
        titles.addAll(newTitle)
        notifyDataSetChanged()
    }

}

open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var mBinding: ItemTitleBinding? = null

    init {
        mBinding = DataBindingUtil.findBinding(itemView)
    }

    fun bindVH(title: String) {
        mBinding?.title = title
        mBinding?.height = DraggableEventGridListView.TitleHeight
        mBinding?.width = DraggableEventGridListView.GroupWidth + 2
    }
}

