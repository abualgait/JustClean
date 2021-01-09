/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util

import android.content.ContentProviderOperation
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.provider.ContactsContract
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import com.justclean.app.shared.dialogs.FailedDialog
import com.justclean.app.shared.ext.hide
import com.justclean.app.shared.ext.show
import com.justclean.app.shared.ui.activity.BaseActivity
import java.net.URLEncoder
import java.util.*

open class WhatsAppHelper(
    val context: Context, val packageManager: PackageManager,
    val name: String,
    val fullNumber: String,
    val textToSend: String,
    val progressBar: ProgressBar,
    val textWait: TextView? = null
) {

    fun checkValidation(

    ) {
        try {
            if (whatsAppInstalledOrNot("com.whatsapp", packageManager)) {
                progressBar.hide()
                textWait.let { it?.hide() }
                if (!contactExists(fullNumber)) {
                    createContact(name, fullNumber)
                } else {
                    openWhatsApp(fullNumber, textToSend)
                }

            } else {
                progressBar.hide()
                textWait.let { it?.hide() }
                FailedDialog(
                    activity = context as BaseActivity<*, *>,
                    title = "context.getString(R.string.whatsapp_not_istalled)",
                    subtitle = "context.getString(R.string.open_google_play)"
                ) { isConfiremd ->
                    if (isConfiremd) {
                        val uri = Uri.parse("market://details?id=com.whatsapp")
                        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(goToMarket)
                    }

                }


            }
        } catch (e: java.lang.Exception) {
            progressBar.hide()
            textWait.let { it?.hide() }
            Log.w("UpdateContact", e.message + "")
            for (ste in e.stackTrace) {
                Log.w("UpdateContact", "\t" + ste.toString())
            }
        }
    }

    fun whatsAppInstalledOrNot(uri: String, packageManager: PackageManager): Boolean {
        val pm: PackageManager = packageManager
        val app_installed = try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }

    fun openWhatsApp(fullNumber: String, textToSend: String) {
        progressBar.show()
        textWait.let { it?.show() }
        Handler().postDelayed({
            progressBar.hide()
            textWait.let { it?.hide() }
            val url =
                "https://api.whatsapp.com/send?phone=${fullNumber}&text=" + URLEncoder.encode(
                    textToSend,
                    "UTF-8"
                )
            val i = Intent(Intent.ACTION_VIEW)
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            context.startActivity(i)
        }, 2000)
    }

    fun contactExists(
        number: String?
    ): Boolean {
        /// number is the phone number
        val lookupUri =
            Uri.withAppendedPath( /*ContactsContract.Data.CONTENT_URI,*/
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number)
            )
        val mPhoneNumberProjection = arrayOf(
            ContactsContract.PhoneLookup._ID,
            ContactsContract.PhoneLookup.NUMBER,
            ContactsContract.PhoneLookup.DISPLAY_NAME
        )
        val cur: Cursor = context.contentResolver
            .query(lookupUri, mPhoneNumberProjection, null, null, null)!!
        cur.use { cur ->
            if (cur.moveToFirst()) {
                return true
            }
        }
        return false
    }

    fun createContact(id: String?, number: String?) {
        val MobileNumber = number
        val ops =
            ArrayList<ContentProviderOperation>()
        ops.add(
            ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI
            )
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        // Names
        if (id != null) {
            ops.add(
                ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI
                )
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                    )
                    .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        id
                    ).build()
            )
        }

        // Mobile Number
        if (MobileNumber != null) {
            ops.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                    )
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                    .withValue(
                        ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                    )
                    .build()
            )
        }

        // Asking the Contact provider to create a new contact
        try {
            context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
            progressBar.hide()
            textWait.let { it?.hide() }
            openWhatsApp(fullNumber, textToSend)
        } catch (e: Exception) {
            e.reportAndPrint()
            FlashbarUtil.show(
                "Error getting contacts provider",
                activity = context as BaseActivity<*, *>
            )
        }
    }
}