/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.dialogs

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

import com.justclean.app.R
import com.justclean.app.shared.ext.setupDialogStuff

class SuccessDialog(
    val title: String,
    val subtitle: String,
    val activity: AppCompatActivity,
    val callback: () -> Unit
) {
    init {

        val view = activity.layoutInflater.inflate(R.layout.dialog_success, null)
        val dismissCardView = view.findViewById<CardView>(R.id.dismissCardView)
        val titleText = view.findViewById<TextView>(R.id.titleText)
        val subtitleText = view.findViewById<TextView>(R.id.subtitleText)
        titleText.text = title
        subtitleText.text = subtitle
        AlertDialog.Builder(activity)
            .create().apply {
                activity.setupDialogStuff(view = view, dialog = this) {
                    dismissCardView.setOnClickListener {
                        dismiss()
                        callback()
                    }

                }
               /* setOnDismissListener {
                    dismiss()
                    callback()
                }*/
            }
    }


}