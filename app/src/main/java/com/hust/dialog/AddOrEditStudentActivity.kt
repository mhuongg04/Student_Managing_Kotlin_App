package com.hust.dialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddOrEditStudentActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtMSSV: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtEmail : EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancle: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_or_edit_student)
        supportActionBar?.title = "Thêm mới"

        edtName = findViewById(R.id.edtName)
        edtMSSV = findViewById(R.id.edtMSSV)
        edtPhone = findViewById(R.id.edtPhone)
        edtEmail = findViewById(R.id.edtEmail)
        btnSave = findViewById(R.id.btnSave)
        btnCancle = findViewById(R.id.btnCancle)

        val student = intent.getSerializableExtra("student") as? Student
        student?.let{
            edtName.setText(it.name)
            edtMSSV.setText(it.mssv)
            edtPhone.setText(it.sdt)
            edtEmail.setText(it.email)
        }

        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val mssv = edtMSSV.text.toString()
            val sdt = edtPhone.text.toString()
            val email = edtEmail.text.toString()

            val newStudent = Student(name, mssv, sdt, email)
            val resultIntent = Intent()
            resultIntent.putExtra("student", newStudent)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        btnCancle.setOnClickListener{
            finish()
        }
    }
}