package com.spread.recyclerviewstudy.preload

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder

sealed interface Occasion

data object ScrollOccasion : Occasion

data class DetachOccasion(val itemView: View) : Occasion

data object LayoutCompletedOccasion : Occasion