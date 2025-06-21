package com.example.authentication_firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.authentication_firebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Tombol untuk pindah ke halaman SignUp
        binding.signupTv.setOnClickListener {
            val intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Tombol login
        binding.btnLogin.setOnClickListener {
            val email = binding.emailET.text.toString().trim()
            val password = binding.passET.text.toString().trim()

            // ... (kode validasi Anda sudah benar, tidak perlu diubah)
            if (email.isEmpty()) {
                binding.emailLayout.error = "Enter Email"
                return@setOnClickListener
            } else {
                binding.emailLayout.error = null
            }

            if (password.isEmpty()) {
                binding.passLayout.error = "Enter Password"
                return@setOnClickListener
            } else {
                binding.passLayout.error = null
            }

            // Proses login
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                        // 1. Dapatkan UID pengguna yang sedang login
                        val userId = auth.currentUser?.uid

                        // 2. Buat Intent dan sisipkan UID sebagai "extra"
                        val intent = Intent(this@MainActivity, HomeScreen::class.java)
                        intent.putExtra("USER_ID", userId)

                        startActivity(intent)
                        finish() // menutup MainActivity
                        // =======================================================

                    } else {
                        Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
