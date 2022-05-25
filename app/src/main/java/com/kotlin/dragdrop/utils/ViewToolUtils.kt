package com.kotlin.dragdrop.utils

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

@SuppressLint("ClickableViewAccessibility")
class ViewToolUtils(clContainerMain: ConstraintLayout, onKeyDownTouchEventListener: OnKeyDownTouchEventListener) {

    private var pressedX = 0
    private var pressedY = 0

    /**
     *  ConstraintLayout.LayoutParams : (layout) Must be parent layout of 'clContainerMain'
     *  else Replace 'ConstraintLayout' with parent's layout Name
     */

    val onObjectTouchListener = View.OnTouchListener { _, event ->
        val layoutParams = clContainerMain.layoutParams as ConstraintLayout.LayoutParams
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                // Where the user started the drag
                pressedX = event.rawX.toInt()
                pressedY = event.rawY.toInt()
                onKeyDownTouchEventListener.onKeyDown()
            }
            MotionEvent.ACTION_MOVE -> {
                // Where the user's finger is during the drag
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()

                // Calculate change in x and change in y
                val dx = x - pressedX
                val dy = y - pressedY

                // Update the margins
                layoutParams.leftMargin += dx
                layoutParams.topMargin += dy
                clContainerMain.layoutParams = layoutParams

                // Save where the user's finger was for the next ACTION_MOVE
                pressedX = x
                pressedY = y
            }
        }
        true
    }

    val onExpandTouchListener = View.OnTouchListener { _, event ->
        clContainerMain.setOnTouchListener(null)
        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                // resize
                val xe = event.x.toInt()
                val ye = event.y.toInt()

                clContainerMain.layoutParams.width += xe
                clContainerMain.layoutParams.height += ye
                clContainerMain.requestLayout()
            }
            MotionEvent.ACTION_UP -> {
                clContainerMain.setOnTouchListener(onObjectTouchListener)
            }
        }
        true
    }

    interface OnKeyDownTouchEventListener {
        fun onKeyDown()
    }
}