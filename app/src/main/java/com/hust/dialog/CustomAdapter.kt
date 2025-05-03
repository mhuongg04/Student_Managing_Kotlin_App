package com.hust.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu

class CustomAdapter(
    private val context: Context,
    private val studentList: ArrayList<Student>
) : BaseAdapter() {

    override fun getCount(): Int = studentList.size

    override fun getItem(position: Int): Any = studentList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.sv_layout, parent, false)

        val student = studentList[position]

        val textName = view.findViewById<TextView>(R.id.textName)
        val textMSSV = view.findViewById<TextView>(R.id.textMSSV)
        val buttonMenu = view.findViewById<ImageView>(R.id.buttonMenu)

        textName.text = student.name
        textMSSV.text = student.mssv

        buttonMenu.setOnClickListener {
            val popup = PopupMenu(context, buttonMenu)
            popup.menuInflater.inflate(R.menu.student_menu, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_edit -> {
                        if (context is MainActivity) {
                            context.editStudent(position)
                        }
                        true
                    }
                    R.id.menu_delete -> {
                        if (context is MainActivity) {
                            context.confirmDelete(position)
                        }
                        true
                    }
                    R.id.menu_call -> {
                        val callIntent = Intent(Intent.ACTION_DIAL)
                        callIntent.data = Uri.parse("tel:${student.sdt}")
                        context.startActivity(callIntent)
                        true
                    }
                    R.id.menu_email -> {
                        val emailIntent = Intent(Intent.ACTION_SENDTO)
                        emailIntent.data = Uri.parse("mailto:${student.email}")
                        context.startActivity(emailIntent)
                        true
                    }
                    else -> false
                }
            }

            popup.show()
        }

        return view
    }
}