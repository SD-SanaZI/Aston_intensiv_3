package com.example.aston_intensiv_3

import androidx.recyclerview.widget.DiffUtil

class TelephoneDiffUtil: DiffUtil.ItemCallback<PersonData>() {
    override fun areItemsTheSame(oldItem: PersonData, newItem: PersonData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonData, newItem: PersonData): Boolean {
        return oldItem == newItem
    }
}