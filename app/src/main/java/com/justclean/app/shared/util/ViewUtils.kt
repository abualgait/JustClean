/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util

import Utils
import android.text.TextUtils
import android.view.View
import com.google.android.material.textfield.TextInputLayout
import com.justclean.app.shared.ext.onTextChanged

object ViewUtils {
    fun resetTextInputErrorsOnTextChanged(vararg textInputLayouts: TextInputLayout) {
        for (inputLayout in textInputLayouts) {
            val editText = inputLayout.editText
            editText?.onTextChanged {
                if (TextUtils.isEmpty(editText.text.toString()))
                    inputLayout.error = " "
                else
                    if (inputLayout.error != null) inputLayout.error = null

            }
        }
    }


    fun resetTextInputErrorsOnTextChangedEmail(vararg textInputLayouts: TextInputLayout) {
        for (inputLayout in textInputLayouts) {
            val editText = inputLayout.editText
            editText?.onTextChanged {
                if (!Utils.isEmailValidDefault(editText.text.toString())) {
                    inputLayout.error = " "

                } else {
                    if (inputLayout.error != null) inputLayout.error = null
                }
            }
        }
    }
}

