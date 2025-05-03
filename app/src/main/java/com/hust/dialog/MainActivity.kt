package com.hust.dialog

import com.hust.dialog.AddOrEditStudentActivity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ADD = 1
        const val REQUEST_EDIT = 2
    }

    private lateinit var studentList: ArrayList<Student>
    private lateinit var adapter: CustomAdapter
    private var editingPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Thêm sinh viên"

        studentList = ArrayList()
        adapter = CustomAdapter(this, studentList)

        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemLongClickListener { _, view, position, _ ->
            showPopupMenu(view, position)
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showPopupMenu(view: View, position: Int) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.student_menu, popup.menu)

        val student = studentList[position]

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_edit -> {
                    editingPosition = position
                    val intent = Intent(this, AddOrEditStudentActivity::class.java)
                    intent.putExtra("student", student)
                    startActivityForResult(intent, REQUEST_EDIT)
                    true
                }
                R.id.menu_delete -> {
                    confirmDelete(position)
                    true
                }
                R.id.menu_call -> {
                    val callIntent = Intent(Intent.ACTION_DIAL)
                    callIntent.data = Uri.parse("tel:${student.sdt}")
                    startActivity(callIntent)
                    true
                }
                R.id.menu_email -> {
                    val emailIntent = Intent(Intent.ACTION_SENDTO)
                    emailIntent.data = Uri.parse("mailto:${student.email}")
                    startActivity(emailIntent)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    fun editStudent(position: Int) {
        editingPosition = position
        val intent = Intent(this, AddOrEditStudentActivity::class.java)
        intent.putExtra("student", studentList[position])
        startActivityForResult(intent, REQUEST_EDIT)
    }

    fun confirmDelete(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên này?")
            .setPositiveButton("Xóa") { _, _ ->
                studentList.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            val intent = Intent(this, AddOrEditStudentActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val student = data.getSerializableExtra("student") as? Student
            if (student != null) {
                when (requestCode) {
                    REQUEST_ADD -> studentList.add(student)
                    REQUEST_EDIT -> if (editingPosition >= 0) {
                        studentList[editingPosition] = student
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }
    }
}
