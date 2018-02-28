package ru.xmn.common.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.inputmethod.InputMethodManager


fun View.pairSharedTransition(): android.support.v4.util.Pair<View, String> {
    return android.support.v4.util.Pair<View, String>(this, ViewCompat.getTransitionName(this))
}

fun View.isInBounds(x: Int, y: Int): Boolean {
    val outRect = Rect()
    val location = IntArray(2)
    this.getDrawingRect(outRect)
    this.getLocationOnScreen(location)
    outRect.offset(location[0], location[1])
    return outRect.contains(x, y)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun List<View>.visibleOnly(vararg view: View) {
    this.forEach { v ->
        when {
            view.any { it.id == v.id } -> v.visible()
            else -> v.invisible()
        }
    }
}

val Int.dpToPx: Int
    get() {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }

val Int.pxToDp: Int
    get() {
        return (this / Resources.getSystem().displayMetrics.density).toInt()
    }

fun View.changeWidth(w: Int) {
    val layoutParams = this.layoutParams
    layoutParams.width = w
    this.layoutParams = layoutParams
}

fun View.changeHeight(w: Int) {
    val layoutParams = this.layoutParams
    layoutParams.height = w
    this.layoutParams = layoutParams
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}

fun View.animateBackground(startColor: Int, endColor: Int, duration: Int = 300) {
    val color = arrayOf(ColorDrawable(startColor), ColorDrawable(endColor))
    val trans = TransitionDrawable(color)
    this.background = trans
    trans.startTransition(duration)
}

fun Context.drawableFromAttr(attrId: Int): Drawable? {
    val attrs = intArrayOf(attrId)
    val typedArray = obtainStyledAttributes(attrs)
    val drawableFromTheme = typedArray.getDrawable(0)
    typedArray.recycle()
    return drawableFromTheme
}

tailrec fun View.getActivity(): Activity? {
    if (context is Activity)
        return context as Activity
    if (parent == null || parent !is View)
        return null
    return (parent as View).getActivity()
}
