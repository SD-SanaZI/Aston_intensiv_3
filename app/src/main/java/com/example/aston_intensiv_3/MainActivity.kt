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
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

const val TELEPHONE_DATA_LIST_KEY = "TELEPHONE_DATA_KEY"
const val IS_ON_DELETE_MOSE_KEY = "IS_ON_DELETE_MOSE_KEY"
class MainActivity : AppCompatActivity(), DataChangeDialog.DialogListener {
    private var isOnDeleteMode: Boolean = false
    private val deleteButton = find<Button>(R.id.delete_button)
    private val cancelButton = find<Button>(R.id.cancel_button)
    private val addButton = find<Button>(R.id.add_button)
    private val deleteModeImage = find<ImageView>(R.id.delete_mod_image)
    private lateinit var adapter: TelephoneAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        var data = getOnCreateData()

        if (savedInstanceState != null){
            val str = savedInstanceState.getString(TELEPHONE_DATA_LIST_KEY)
            data = Gson().fromJson(str, TelephoneDataList::class.java).list.toMutableList()
            isOnDeleteMode = savedInstanceState.getBoolean(IS_ON_DELETE_MOSE_KEY)
        }
        adapter = TelephoneAdapter(supportFragmentManager)
        adapter.submitList(data)
        recycler.adapter = adapter
        changeModeAndUpdate(isOnDeleteMode)
        deleteModeImage.value.setOnClickListener {
            changeModeAndUpdate(true)
        }
        cancelButton.value.setOnClickListener {
            val newData = adapter.currentList.toMutableList()
            unSelectData(newData)
            adapter.submitList(newData)
            changeModeAndUpdate(false)
        }
        deleteButton.value.setOnClickListener {
            val newData = adapter.currentList.toMutableList()
            newData.removeAll { it.isSelected }
            adapter.submitList(newData)
            changeModeAndUpdate(false)
        }
        addButton.value.setOnClickListener {
            val missedData = adapter.currentList.filterIndexed{index, telephoneData -> telephoneData.id != index + 1}
            val indexOfNew =
                if (missedData.isEmpty()) adapter.currentList.size + 1
                else missedData.first().id - 1
            val dialog = DataChangeDialog.newInstance(TelephoneData(indexOfNew))
            dialog.show(supportFragmentManager,"")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TELEPHONE_DATA_LIST_KEY, Gson().toJson(TelephoneDataList(adapter.currentList.toMutableList())))
        outState.putBoolean(IS_ON_DELETE_MOSE_KEY,isOnDeleteMode)
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

    private fun changeModeAndUpdate(value: Boolean){
        isOnDeleteMode = value
        adapter.isOnDeleteMode = value
        updateButtonDisposing()
    }

    private fun unSelectData(data: MutableList<TelephoneData>){
        for(index in 0..< data.size){
            data[index] =
                data[index].copy(isSelected = false)
        }
    }

    override fun onDialogResult(data: TelephoneData) {
        val newData = adapter.currentList.toMutableList()
        val index = newData.indexOfFirst { it.id == data.id}
        if(index > 0) newData[index] = data
        else newData.add(data)
        newData.sortBy { it.id }
        adapter.submitList(newData)
    }

    private fun getOnCreateData(): MutableList<TelephoneData>{
        return MutableList(100){
            TelephoneData(
                id = it + 1,
                personData = PersonData("Name $it",
                    "Surname $it",
                    (80290000000+it).toString()),
                isSelected = false)
        }
    }
}
private fun <VIEW: View> AppCompatActivity.find(@IdRes idRes: Int): Lazy<VIEW> {
    return lazy { findViewById(idRes) }
}

data class TelephoneDataList(
    @SerializedName("list")
    val list: List<TelephoneData>
) : Serializable