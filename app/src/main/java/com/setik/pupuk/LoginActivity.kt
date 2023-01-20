package com.setik.pupuk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var context: Context
    private lateinit var pref: preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
         context = this
            pref = preferences(context)

        val textVi = findViewById<TextView>(R.id.kios) as TextView
        textVi.setOnClickListener {
            intent = Intent(context, LoginKios::class.java)
            startActivity(intent)
            finish()
        }

        btn_login.setOnClickListener {
            val email: String = et_email.text.toString()
            val password: String = et_password.text.toString()
            if (email.isEmpty()) {
                et_email.error = "Data tidak boleh kosong"
                et_email.requestFocus()
            } else if (password.isEmpty()) {
                et_password.error = "Data tidak boleh kosong"
                et_password.requestFocus()
            } else{
                val query:Query = database.child("users").orderByChild("email").equalTo(email)
                query.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            for (item in snapshot.children){
                                val user = item.getValue<userData>()
                                if (user != null){
                                    if (user.password.equals(password)){
                                        pref.prefStatus = true
                                        intent = Intent(context, MainActivity::class.java)
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