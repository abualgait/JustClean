/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBindView(`object`: Any, position: Int)


}