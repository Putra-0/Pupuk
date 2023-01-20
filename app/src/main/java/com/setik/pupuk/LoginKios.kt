package com.setik.pupuk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.setik.pupuk.ui_admin.data.Kios
import kotlinx.android.synthetic.main.activity_login.*

class LoginKios : AppCompatActivity() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var context: Context
    private lateinit var pref: preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_kios)
        context = this
        pref = preferences(context)

        val adm = findViewById<TextView>(R.id.admin) as TextView
        adm.setOnClickListener {
            intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_login.setOnClickListener {
            val email: String = et_email.text.toString()
            val password: String = et_password.text.toString()
            if (email.isEmpty()){
                et_email.error = "Data tidak boleh kosong"
                et_email.requestFocus()
            }else if (password.isEmpty()){
                et_password.error = "Data tidak boleh kosong"
                et_password.requestFocus()
            }else{
                val query: Query = database.child("kios").orderByChild("email").equalTo(email)
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            for (item in snapshot.children){
                                val kios = item.getValue<Kios>()
                                if (kios != null){
                                    if (kios.password.equals(password)){
                                        pref.prefStatus = true
                                        intent = Intent(context, MainKiosActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }else{
                                        Toast.makeText(context,
                                            "Email atau password salah",
                                            Toast.LENGTH_LONG)
                                            .show()
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(context,
                                "Email belum terdaftar",
                                Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context,
                            "Database error",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
        }
    }
}