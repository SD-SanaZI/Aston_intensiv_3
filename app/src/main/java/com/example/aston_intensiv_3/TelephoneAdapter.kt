package com.example.aston_intensiv_3

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.aston_intensiv_3.data.TelephoneData


class TelephoneAdapter(private val fragmentManager: FragmentManager) :
    ListAdapter<TelephoneData, TelephoneAdapter.TelephoneViewHolder>(TelephoneDiffUtil()) {
    var isOnDeleteMode: Boolean = false

    inner class TelephoneViewHolder(view: View) : ViewHolder(view) {
        private val id: TextView
        private val name: TextView
        private val surname: TextView
        private val number: TextView
        private var isSelected: Boolean = false

        init {
            id = view.findViewById(R.id.id)
            name = view.findViewById(R.id.personName)
            surname = view.findViewById(R.id.surname)
            number = view.findViewById(R.id.number)
        }

        fun bind(data: TelephoneData){
            id.text = data.id.toString()
            name.text = data.personData.name
            surname.text = data.personData.surname
            number.text = data.personData.telephoneNumber
            isSelected = data.isSelected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TelephoneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.telephone_item, parent, false)
        return TelephoneViewHolder(view)
    }

    override fun onBindViewHolder(holder: TelephoneViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            if (isOnDeleteMode) {
                val newData = currentList.toMutableList()
                newData[position] = data.copy(isSelected = !data.isSelected)
                submitList(newData)
                notifyItemChanged(position)
            } else {
                val dialog = DataChangeDialog.newInstance(data)
                dialog.show(fragmentManager,"")
            }
        }
        holder.itemView.background =
            if (data.isSelected) ColorDrawable(Color.YELLOW)
            else null
    }
}