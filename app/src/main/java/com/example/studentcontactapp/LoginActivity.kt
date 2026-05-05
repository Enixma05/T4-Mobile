package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentcontactapp.utils.PrefManager

class LoginActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefManager = PrefManager(this)

        if (prefManager.isLoggedIn() && prefManager.isRememberMe()) {
            navigateToMain()
            return
        }

        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbRememberMe = findViewById<CheckBox>(R.id.cbRememberMe)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        if (prefManager.isRememberMe()) {
            etUsername.setText(prefManager.getUsername())
            cbRememberMe.isChecked = true
        }

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (username == "admin" && password == "123456") {
                prefManager.saveLoginSession(
                    username = username,
                    rememberMe = cbRememberMe.isChecked
                )
                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                navigateToMain()
            } else {
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}