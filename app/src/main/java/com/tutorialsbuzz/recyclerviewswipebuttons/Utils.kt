package com.tutorialsbuzz.recyclerviewswipebuttons

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class Utils {


    companion object {


        public fun readFromAsset(context: Context): MutableList<Model> {
            val modeList = mutableListOf<Model>()
            val bufferReader = context.assets.open("android_version.json").bufferedReader()
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

}