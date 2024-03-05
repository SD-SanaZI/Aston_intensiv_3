package com.example.aston_intensiv_3

import androidx.recyclerview.widget.DiffUtil
import com.example.aston_intensiv_3.data.TelephoneData

class TelephoneDiffUtil: DiffUtil.ItemCallback<TelephoneData>() {
    override fun areItemsTheSame(oldItem: TelephoneData, newItem: TelephoneData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TelephoneData, newItem: TelephoneData): Boolean {
        return oldItem == newItem
    }
}