package com.example.aston_intensiv_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.example.aston_intensiv_3.data.PersonData
import com.example.aston_intensiv_3.data.TelephoneData

class MainActivity : AppCompatActivity() {
    private var isOnDeleteMode: Boolean = false
    private val deleteButton = find<Button>(R.id.delete_button)
    private val cancelButton = find<Button>(R.id.cancel_button)
    private val addButton = find<Button>(R.id.add_button)
    private val deleteModeImage = find<ImageView>(R.id.delete_mod_image)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = MutableList(100){
            TelephoneData(
                id = it + 1,
                personData = PersonData("Name $it",
                    "Surname $it",
                    (80290000000+it).toString()),
                isSelected = false)
        }
        val adapter = TelephoneAdapter(data)
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
        updateButtonDisposing()
        deleteModeImage.value.setOnClickListener {
            isOnDeleteMode = true
            adapter.isOnDeleteMode = true
            updateButtonDisposing()
        }
        cancelButton.value.setOnClickListener {
            isOnDeleteMode = false
            adapter.isOnDeleteMode = true
            updateButtonDisposing()
        }
        deleteButton.value.setOnClickListener {
            adapter.data.removeAll { it.isSelected }
            adapter.notifyDataSetChanged()
            isOnDeleteMode = false
            adapter.isOnDeleteMode = true
            updateButtonDisposing()
        }
    }

    private fun updateButtonDisposing(){
        if (isOnDeleteMode){
            deleteButton.value.visibility = View.VISIBLE
            cancelButton.value.visibility = View.VISIBLE
            addButton.value.visibility = View.GONE
        }else{
            deleteButton.value.visibility = View.GONE
            cancelButton.value.visibility = View.GONE
            addButton.value.visibility = View.VISIBLE
        }
    }
}
private fun <VIEW: View> AppCompatActivity.find(@IdRes idRes: Int): Lazy<VIEW> {
    return lazy { findViewById(idRes) }
}