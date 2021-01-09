/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */



import com.justclean.app.shared.ui.frag.BaseFrag
import com.justclean.app.shared.util.FlashbarUtil
import com.justclean.app.shared.util.configs.ConstValue
import com.justclean.app.shared.vm.BaseViewModel
import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import org.json.JSONObject
import retrofit2.HttpException
import com.justclean.app.R
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    private val TAG = Utils::class.java.simpleName

    val notificationIcon: Int
        get() {
            val useWhiteIcon =
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP
            return if (useWhiteIcon) R.mipmap.ic_launcher else R.mipmap.ic_launcher
        }


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

    fun isNetworkAvailable(context: Context): Boolean {
        var value = false
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        if (info != null && info.isAvailable) {
            value = true
        }
        return value
    }

    fun requestFocus(view: View, mActivity: Activity) {
        if (view.requestFocus()) {
            mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    fun isEqual(str1: String, str2: String): Boolean {
        return str1.equals(str2)
    }

    fun isEmailValidDefault(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }

    fun isEditTxtValid(str: String): Boolean {
        return !TextUtils.isEmpty(str)
    }

    fun getConnectionStatus(context: Context): Boolean {
        val mConnectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfoMobile: NetworkInfo
        val mNetworkInfoWifi: NetworkInfo

        mNetworkInfoMobile = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        mNetworkInfoWifi = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        try {
            if (mNetworkInfoMobile.isConnected) {
                return true
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return mNetworkInfoWifi.isConnected
    }

    fun hideKeyboardFromView(context: Context, view: View) {
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboard(activity: Activity) {

        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (imm.isAcceptingText) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }


    fun getDeviceDisplay(mActivity: Activity): DisplayMetrics {

        val displayMetrics = DisplayMetrics()
        mActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        return displayMetrics
    }

    fun getUnderlineString(mString: String): SpannableString {

        val content = SpannableString(mString)
        content.setSpan(UnderlineSpan(), 0, mString.length, 0)

        return content
    }

    fun getUnderlineStringCustome(mString: String, mStart: Int, mEnd: Int): SpannableString {

        val content = SpannableString(mString)
        content.setSpan(UnderlineSpan(), mStart, mEnd, 0)

        return content
    }

    fun getFormattedDecimal(fromStr: String): String {
        val formatter = DecimalFormat("#,###,###,###")
        val toStr = formatter.format(fromStr.toDouble())
        return toStr
    }

    fun getCamelCaseString(status: String): String {
        val s = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase()
        return s
    }


    fun View.setMargin(
        leftMargin: Int? = null, topMargin: Int? = null,
        rightMargin: Int? = null, bottomMargin: Int? = null
    ) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(
            leftMargin ?: params.leftMargin,
            topMargin ?: params.topMargin,
            rightMargin ?: params.rightMargin,
            bottomMargin ?: params.bottomMargin
        )
        layoutParams = params
    }


    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double, unit: String): Double {
        if (lat1 == lat2 && lon1 == lon2) {
            return 0.0
        } else {
            val theta = lon1 - lon2
            var dist =
                Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(
                    Math.toRadians(
                        lat1
                    )
                ) * Math.cos(
                    Math.toRadians(lat2)
                ) * Math.cos(Math.toRadians(theta))
            dist = Math.acos(dist)
            dist = Math.toDegrees(dist)
            dist *= 60.0 * 1.1515
            if (unit === "K") {
                dist *= 1.609344
            } else if (unit === "N") {
                dist *= 0.8684
            }
            return dist
        }
    }


    fun isPackageExisted(targetPackage: String, packageManager: PackageManager): Boolean {
        val packages: List<ApplicationInfo> = packageManager.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == targetPackage)
                return true
        }
        return false
    }

    fun getDrawableLetters(firstStr: String, secondStr: String): String {
        val firstChar: Char = firstStr[0]
        val secondChar: Char = secondStr[0]
        return firstChar.toString().toUpperCase() + secondChar.toString().toUpperCase()

    }


    fun checkError(error: Throwable, activity: Activity) {
        val errorException = error as HttpException
        val errorBody = errorException.response()?.errorBody()?.string()
        if (errorBody != "") {
            val mainObject = JSONObject(errorBody)
            FlashbarUtil.show(mainObject.getString(ConstValue.ERROR), activity = activity)
        }
    }

    fun getTimeFormated(time: String): String? {

        val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("hh:mma", Locale.ENGLISH)

        val symbols = DateFormatSymbols(Locale.getDefault())
        symbols.amPmStrings = arrayOf("am", "pm")
        outputFormat.dateFormatSymbols = symbols

        return outputFormat.format(inputFormat.parse(time))
    }

    fun setTextHTML(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html.replace("\n", "<br />"), Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html.replace("\n", "<br />"))
        }
    }


    fun convertArabicDigitsToEnglish(arabicDigits: String): String? {
        val chars = CharArray(arabicDigits.length)
        // convert arabic digits to english
        for (i in arabicDigits.indices) {
            var ch = arabicDigits[i]
            if (ch.toInt() in 0x0660..0x0669) ch -= 0x0660 - '0'.toInt()
            else if (ch.toInt() in 0x06f0..0x06F9) ch -= 0x06f0 - '0'.toInt()
            chars[i] = ch
        }
        return String(chars)
    }

    /**
     * convert returned 445815...786 to 4458 15** **** *786
     */
    fun getFormatedCardNumber(oldCardNumber: String): String? {
        val replaceDot = oldCardNumber.replace(".", "")
        val astrixStr = "*".repeat(7)
        val associatedStr =
            replaceDot.substring(IntRange(0, 5)) + astrixStr + replaceDot.substring(IntRange(6, 8))
        return associatedStr.replace("....".toRegex(), "$0 ")

    }

    fun loadFragment(
        fragment: BaseFrag<BaseViewModel, ViewDataBinding>,
        container: Int,
        tag: String,
        supportFragmentManager: FragmentManager
    ) {

        val transaction = supportFragmentManager.beginTransaction()
        val existingFrag = supportFragmentManager
            .findFragmentByTag(tag)
        if (existingFrag == null) {
            transaction.add(container, fragment, tag)
            showViewInBackStack(transaction, tag, supportFragmentManager)
            //transaction.addToBackStack(tag)
            transaction.commit()
        } else {
            showViewInBackStack(transaction, tag, supportFragmentManager)
            //transaction.addToBackStack(tag)
            transaction.commit()
        }
    }

    private fun showViewInBackStack(
        transaction: FragmentTransaction,
        tag: String,
        supportFragmentManager: FragmentManager
    ) {
        val fragList = supportFragmentManager.fragments
        for (frag in fragList) {
            if (frag.tag.equals(tag)) {
                transaction.show(frag)
            } else {
                transaction.hide(frag)
            }
        }
    }




    fun getViewBitmap(view: View): Bitmap? {
        //Get the dimensions of the view so we can re-layout the view at its current size
        //and create a bitmap of the same size
        val width = view.width
        val height = view.height
        val measuredWidth = View.MeasureSpec.makeMeasureSpec(
            width,
            View.MeasureSpec.EXACTLY
        )
        val measuredHeight = View.MeasureSpec.makeMeasureSpec(
            height,
            View.MeasureSpec.EXACTLY
        )

        //Cause the view to re-layout
        view.measure(measuredWidth, measuredHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)

        //Create a bitmap backed Canvas to draw the view into
        val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)

        //Now that the view is laid out and we have a canvas, ask the view to draw itself into the canvas
        view.draw(c)
        return b
    }
}
