package com.example.aston_intensiv_3

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.aston_intensiv_3.data.TelephoneData


class TelephoneAdapter(val data: MutableList<TelephoneData>) :
    RecyclerView.Adapter<TelephoneAdapter.TelephoneViewHolder>() {
    var isOnDeleteMode: Boolean = false

    inner class TelephoneViewHolder(view: View) : ViewHolder(view) {
        val id: TextView
        val name: TextView
        val surname: TextView
        val number: TextView
        var isSelected: Boolean = false

        init {
            id = view.findViewById(R.id.id)
            name = view.findViewById(R.id.personName)
            surname = view.findViewById(R.id.surname)
            number = view.findViewById(R.id.number)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TelephoneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.telephone_item, parent, false)
        return TelephoneViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TelephoneViewHolder, position: Int) {
        holder.id.text = data[position].id.toString()
        holder.name.text = data[position].personData.name
        holder.surname.text = data[position].personData.surname
        holder.number.text = data[position].personData.telephoneNumber
        holder.isSelected = data[position].isSelected
        holder.itemView.setOnClickListener {
            if (isOnDeleteMode) {
                Log.d("DeleteModeClickListener", "done")
                data[position] =
                    data[position].copy(isSelected = !holder.isSelected)
                this.notifyItemChanged(position)
            } else {

            }
        }
        holder.itemView.background =
            if (data[position].isSelected) ColorDrawable(Color.YELLOW)
            else null
    }
}