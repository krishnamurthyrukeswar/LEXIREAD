package com.lexiread.app.ui.reader

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ScrollView

/**
 * ScrollView with pinch-to-zoom support for the TXT reader.
 * Adjusts the child text size on pinch gesture.
 */
class ZoomableScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ScrollView(context, attrs, defStyle) {

    var onFontSizeChanged: ((Float) -> Unit)? = null

    private var currentFontSize = 16f
    private val minFontSize = 10f
    private val maxFontSize = 40f

    private val scaleDetector = ScaleGestureDetector(
        context,
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val scaleFactor = detector.scaleFactor
                val newSize = (currentFontSize * scaleFactor).coerceIn(minFontSize, maxFontSize)
                if (newSize != currentFontSize) {
                    currentFontSize = newSize
                    onFontSizeChanged?.invoke(currentFontSize)
                }
                return true
            }
        }
    )

    fun setCurrentFontSize(size: Float) {
        currentFontSize = size
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { scaleDetector.onTouchEvent(it) }
        return if (scaleDetector.isInProgress) true else super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { scaleDetector.onTouchEvent(it) }
        return if (scaleDetector.isInProgress) true else super.onTouchEvent(ev)
    }
}
