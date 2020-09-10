package com.tutorialsbuzz.recyclerviewswipebuttons

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tutorialsbuzz.recyclerviewswipebuttons.R

class SimpleDividerItemDecoration : RecyclerView.ItemDecoration {
    private var mDivider: Drawable? = null

    constructor(context: Context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider)
    }

    constructor(context: Context, drawable: Int) {
        mDivider = ContextCompat.getDrawable(context, drawable)
    }

    override fun onDrawOver(c: Canvas, recyclerView: RecyclerView, state: RecyclerView.State) {

        val left = recyclerView.paddingLeft
        val right = recyclerView.width

        val childCount = recyclerView.childCount
        for (i in 0 until childCount) {

            val child = recyclerView.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)

        }
    }
}
