package com.tutorialsbuzz.recyclerviewswipebuttons

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.tutorialsbuzz.recyclerviewswipebuttons.SwipeHelper.UnderlayButtonClickListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val modelList = readFromAsset();

        val adapter = CustomAdapter(modelList, this)
        recyclerView.addItemDecoration(SimpleDividerItemDecoration(this))
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter;

        object : SwipeHelper(this, recyclerView, false) {

            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?
            ) {
                // Archive Button
                underlayButtons?.add(SwipeHelper.UnderlayButton(
                    "Archive",
                    AppCompatResources.getDrawable(
                        this@MainActivity,
                        R.drawable.ic_archive_black_24dp
                    ),
                    Color.parseColor("#000000"), Color.parseColor("#ffffff"),
                    UnderlayButtonClickListener { pos: Int ->

                        Toast.makeText(
                            this@MainActivity,
                            "Delete clicked at " + pos,
                            Toast.LENGTH_SHORT
                        ).show()
                        adapter.modelList.removeAt(pos);
                        adapter.notifyItemRemoved(pos)

                    }

                ))

                // Flag Button
                underlayButtons?.add(SwipeHelper.UnderlayButton(
                    "Flag",
                    AppCompatResources.getDrawable(
                        this@MainActivity,
                        R.drawable.ic_flag_black_24dp
                    ),
                    Color.parseColor("#FF0000"), Color.parseColor("#ffffff"),
                    UnderlayButtonClickListener { pos: Int ->

                        Toast.makeText(
                            this@MainActivity,
                            "Flag Button Clicked at Position: " + pos,
                            Toast.LENGTH_SHORT
                        ).show()
                        adapter.notifyItemChanged(pos)
                    }

                ))

                // More Button
                underlayButtons?.add(SwipeHelper.UnderlayButton(
                    "More",
                    AppCompatResources.getDrawable(
                        this@MainActivity,
                        R.drawable.ic_more_horiz_black_24dp
                    ),
                    Color.parseColor("#00FF00"), Color.parseColor("#ffffff"),
                    UnderlayButtonClickListener { pos: Int ->

                        Toast.makeText(
                            this@MainActivity,
                            "More Button CLicked at Position: " + pos,
                            Toast.LENGTH_SHORT
                        ).show()
                        adapter.notifyItemChanged(pos)
                    }

                ))


            }
        }
    }

    private fun readFromAsset(): MutableList<Model> {
        val modeList = mutableListOf<Model>()
        val bufferReader = application.assets.open("android_version.json").bufferedReader()
        val json_string = bufferReader.use {
            it.readText()
        }
        val jsonArray = JSONArray(json_string);

        for (i in 0..jsonArray.length() - 1) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)

            val model = Model(jsonObject.getString("name"), jsonObject.getString("version"))
            modeList.add(model)
        }

        return modeList
    }


}
