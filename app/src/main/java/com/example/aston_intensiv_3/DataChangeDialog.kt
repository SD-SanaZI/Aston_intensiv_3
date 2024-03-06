package com.example.aston_intensiv_3

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.aston_intensiv_3.data.PersonData
import com.example.aston_intensiv_3.data.TelephoneData
import java.lang.Exception

const val TELEPHONE_DATA_KEY = "TELEPHONE_DATA_KEY"

class DataChangeDialog: DialogFragment() {
    private var dialogListener: DialogListener? = null
    interface DialogListener{
        fun onDialogResult(data: TelephoneData)
    }

    companion object{
        fun newInstance(data: TelephoneData): DataChangeDialog{
            val dialog = DataChangeDialog()
            val bundle = Bundle()
            bundle.putSerializable(TELEPHONE_DATA_KEY,data)
            //bundle.putString(TELEPHONE_DATA_KEY,Gson().toJson(data))
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val view = layoutInflater.inflate(R.layout.dialog, null)
            val bundle = arguments as Bundle
            val data: TelephoneData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(TELEPHONE_DATA_KEY,TelephoneData::class.java)
                    ?: throw Exception("Null Dialog data")
            } else {
                bundle.getSerializable(TELEPHONE_DATA_KEY) as TelephoneData
            }
            //Gson().fromJson(arguments?.getString(TELEPHONE_DATA_KEY),TelephoneData::class.java)
            val name = view.findViewById<EditText>(R.id.nameEdit)
            val surname = view.findViewById<EditText>(R.id.surnameEdit)
            val telephoneNumber = view.findViewById<EditText>(R.id.telephoneNumberEdit)
            view.findViewById<TextView>(R.id.idText).text = data.id.toString()
            name.setText(data.personData.name)
            surname.setText(data.personData.surname)
            telephoneNumber.setText(data.personData.telephoneNumber)
            val builder = AlertDialog.Builder(it)
            builder
                .setView(view)
                .setPositiveButton("Make changes"){_, _ ->
                    dialogListener?.onDialogResult(
                        TelephoneData(data.id,
                        PersonData(
                            name.text.toString(),
                            surname.text.toString(),
                            telephoneNumber.text.toString()
                        )
                    ))
                }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogListener = context as DialogListener
    }

    override fun onDetach() {
        super.onDetach()
        dialogListener = null
    }
}