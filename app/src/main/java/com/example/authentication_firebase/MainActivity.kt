package com.example.authentication_firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.authentication_firebase.databinding.ActivityMainBinding
import com.example.authentication_firebase.ui.theme.AuthenticationfirebaseTheme
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupTv.setOnClickListener {
            var intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            if (binding.emailET.text.toString().isEmpty()) {
                binding.emailLayout.error = "Enter Email"
            }
            else if (binding.passET.text.toString().isEmpty()) {
                binding.passLayout.error = "Enter Password"
            }
            else {
                auth.signInWithEmailAndPassword(binding.emailET.text.toString(), binding.passET.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        var intent = Intent(this@MainActivity, HomeScreen::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}