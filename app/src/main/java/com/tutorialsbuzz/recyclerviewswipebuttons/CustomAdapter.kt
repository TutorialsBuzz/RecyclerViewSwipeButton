package com.tutorialsbuzz.recyclerviewswipebuttons

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tutorialsbuzz.recyclerviewswipebuttons.R
import kotlinx.android.synthetic.main.regular_row_item.view.*
import kotlinx.android.synthetic.main.swipe_row_item.view.*


class CustomAdapter(val modelList: MutableList<Model>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemsPendingRemoval: MutableList<Model>

    var PENDING_REMOVAL_TIMEOUT: Long = 3000
    var handler: Handler = Handler()
    var pendingRunnables: HashMap<Model, Runnable> = HashMap()

    init {
        itemsPendingRemoval = mutableListOf<Model>()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(modelList.get(position));
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.row_itemm, parent, false))
    }


    override fun getItemCount(): Int {
        return modelList.size;
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: Model): Unit {

            if (itemsPendingRemoval.contains(model)) {
                //show swipe layout
                itemView.swipeLayout.visibility = View.VISIBLE
                itemView.regularLayout.visibility = View.GONE

                itemView.undo.setOnClickListener({ view ->
                    undoOpt(model)
                })

            } else {
                //show regular layout
                itemView.swipeLayout.visibility = View.GONE
                itemView.regularLayout.visibility = View.VISIBLE
                itemView.txt.text = model.name
                itemView.sub_txt.text = model.version
                val id = context.resources.getIdentifier(
                    model.name.toLowerCase(),
                    "drawable",
                    context.packageName
                )
                itemView.img.setBackgroundResource(id)
            }

        }


        private fun undoOpt(model: Model) {
            val pendingRemovalRunnable = pendingRunnables[model]
            pendingRunnables.remove(model)
            if (pendingRemovalRunnable != null)
                handler.removeCallbacks(pendingRemovalRunnable)
            itemsPendingRemoval.remove(model)
            // this will rebind the row in "normal" state
            notifyItemChanged(modelList.indexOf(model))
        }

    }


    fun pendingRemoval(position: Int) {

        val data = modelList.get(position)
        if (!itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.add(data)
            // this will redraw row in "undo" state
            notifyItemChanged(position)
            // let's create, store and post a runnable to remove the data
            val pendingRemovalRunnable = Runnable {
                remove(modelList.indexOf(data))
            }

            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT)
            pendingRunnables[data] = pendingRemovalRunnable
        }
    }

    fun remove(position: Int) {
        val data = modelList.get(position)
        if (itemsPendingRemoval.contains(data)) {
            itemsPendingRemoval.remove(data)
        }
        if (modelList.contains(data)) {
            //dataList.remove(position)
            modelList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun isPendingRemoval(position: Int): Boolean {
        val data = modelList.get(position)
        return itemsPendingRemoval.contains(data)
    }


}