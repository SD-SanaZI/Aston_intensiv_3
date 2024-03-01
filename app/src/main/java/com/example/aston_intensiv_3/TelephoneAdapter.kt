package com.example.aston_intensiv_3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder



class TelephoneAdapter(private val data: List<PersonData>): RecyclerView.Adapter<TelephoneAdapter.TelephoneViewHolder>() {
    class TelephoneViewHolder(view: View): ViewHolder(view){
        val id : TextView
        val name : TextView
        val surname : TextView
        val number : TextView

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
        holder.name.text = data[position].name
        holder.surname.text = data[position].surname
        holder.number.text = data[position].telephoneNumber
    }
}