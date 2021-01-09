/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.ext

import Utils
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.justclean.app.shared.util.FlashbarUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun isOreo() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

@Suppress("UNCHECKED_CAST")
fun <T> Context.systemService(name: String): T {
    return getSystemService(name) as T
}

fun getBitmapFromURL(strURL: String): Bitmap? {
    return try {
        val url = URL(strURL)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        BitmapFactory.decodeStream(input)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun getDrawableLetters(str: String): String {
    val firstChar: Char = str[0]
    return firstChar.toString().toUpperCase()

}

fun openUrl(context: Context, url: String?) {
    val i = Intent(Intent.ACTION_VIEW)
    if (url != null)
        i.data = Uri.parse(url)
    context.startActivity(i)
}


fun openDirections(
    activity: Context,
    latitude: Double?,
    longitude: Double?,
    packageManager: PackageManager
) {
    if (Utils.isPackageExisted("com.google.android.apps.maps", packageManager)) {
//            val gmmIntentUri = Uri.parse("geo:" + info.latitude + "," + info.longitude + "?q=" + info.latitude + ","
//                    + info.longitude + "(" + info.cityName + ")" + "")
        val directionIntentUri =
            Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude + "")
        val mapIntent = Intent(Intent.ACTION_VIEW, directionIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        activity.startActivity(mapIntent)
    } else {
        FlashbarUtil.show("no app to handle the action", activity = activity as Activity)
    }
}

fun openEmail(context: Context, email: String?) {
    val intent = Intent(
        Intent.ACTION_SENDTO,
        Uri.fromParts("mailto", email, null)
    )
    intent.putExtra(Intent.EXTRA_SUBJECT, "")
    intent.putExtra(Intent.EXTRA_TEXT, "")

    try {
        context.startActivity(Intent.createChooser(intent, "GoodsMart EG"))
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(
            context as Activity,
            "There are no email clients installed.",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Context.openAppInGooglePlay() {
    val uri = Uri.parse("market://details?id=" + getPackageName())
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    // To count with Play market backstack, After pressing back button,
    // to taken back to our application, we need to add following flags to intent.
    // To count with Play market backstack, After pressing back button,
    // to taken back to our application, we need to add following flags to intent.
    goToMarket.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())
            )
        )
    }
}


fun shareData(context: Context, subject: String, body: String?) {
    var shareBody = body + "\n\n"
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    shareBody += "Sent from INews App"
    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
}

fun Context.shareContent(imageView: ImageView, subject: String, body: String?, timeago: String) {
    val bitmap = getBitmapFromView(imageView)
    try {
        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val file = File(this.externalCacheDir, "image.png")
        val fOut = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
        fOut.flush()
        fOut.close()
        file.setReadable(true, false)

        var shareBody = body + "\n\n" + timeago + "\n\n"
        shareBody += "Sent from INews App"

        val intent = Intent(Intent.ACTION_SEND)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //Uri.fromFile(file)
        //val uri: Uri = FileProvider.getUriForFile(this, this.applicationContext.packageName.toString() + ".provider", file)
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        intent.type = "image/png"
        startActivity(Intent.createChooser(intent, "Share via"))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun getMarkerBitmapFromView(
    view: View
): Bitmap? {

    view.measure(
        View.MeasureSpec.UNSPECIFIED,
        View.MeasureSpec.UNSPECIFIED
    )
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    view.buildDrawingCache()
    val returnedBitmap = Bitmap.createBitmap(
        view.measuredWidth, view.measuredHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(returnedBitmap)
    canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
    val drawable = view.background
    drawable?.draw(canvas)
    view.draw(canvas)
    return returnedBitmap
}

fun getBitmapFromView(view: View): Bitmap {
    val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(returnedBitmap)
    val bgDrawable: Drawable? = view.background
    if (bgDrawable != null) {
        bgDrawable.draw(canvas)
    } else {
        canvas.drawColor(Color.WHITE)
    }
    view.draw(canvas)
    return returnedBitmap
}


fun openDial(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    context.startActivity(intent)
}

fun TabLayout.changeTabsFont(typeface: Typeface) {
    val vg = this.getChildAt(0) as ViewGroup
    val tabsCount = vg.childCount
    for (j in 0 until tabsCount) {
        val vgTab = vg.getChildAt(j) as ViewGroup
        val tabChildsCount = vgTab.childCount
        for (i in 0 until tabChildsCount) {
            val tabViewChild: View = vgTab.getChildAt(i)
            if (tabViewChild is TextView) {
                (tabViewChild as TextView).setTypeface(typeface, Typeface.NORMAL)
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun isOnline(context: Context?): Boolean {
    var result = false
    if (context != null) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null) {
            result = networkInfo.isConnected
        }
    }
    return result
}

fun isEmailValidDefault(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isEditTxtValid(str: String): Boolean {
    return !TextUtils.isEmpty(str)
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun hideKeyboard(context: Activity) {
    val view = context.currentFocus
    if (view != null) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Context.initRTL() {

    val languageToLoad = "ar"
    val locale = Locale(languageToLoad)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    this.resources.updateConfiguration(config, this.resources.displayMetrics)


}

fun ViewPager2.setViewPagerTransform(pageMarginPx: Int, offsetPx: Int) {

    clipToPadding = false
    clipChildren = false
    offscreenPageLimit = 3

    setPageTransformer { page, position ->

        val offset = position * -(2 * offsetPx + pageMarginPx)
        if (this.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            //if you want to make current page zoom
            /*when {
                position < -1 -> {
                    page.translationX = -offset
                }
                position <= 1 -> {
                    val scaleFactor =
                        Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                    page.translationX = offset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                }
                else -> {
                    page.alpha = 0f
                    page.translationX = offset
                }
            }*/
            if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.translationX = -offset
            } else {
                page.translationX = offset
            }
        } else {
            page.translationY = offset
        }
    }

}

fun Double.formatePrice(isWallet: Boolean): String? {
    return if (this % 1 != 0.0) {  // fractional value
        if (!isWallet) {
            String.format(
                Locale.getDefault(),
                "%.2f",
                Math.round(this / 0.05) * .05
            )
        } else {
            val df = DecimalFormat("#,###,###")
            df.format(this)
            NumberFormat.getCurrencyInstance(Locale.getDefault())
                .format(this)
        }
    } else {
        NumberFormat.getInstance(Locale.getDefault()).format(this)
    }
}

fun NavController.popBackStackAllInstances(destination: Int, inclusive: Boolean): Boolean {
    var popped: Boolean
    while (true) {
        popped = popBackStack(destination, inclusive)
        if (!popped) {
            break
        }
    }
    return popped
}

fun String.isValidUrl(): Boolean {
    return try {
        URL(this)
        true
    } catch (e: Exception) {
        false
    }
}

fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(s.toString())
        }

        override fun afterTextChanged(editable: Editable?) {

        }
    })
}