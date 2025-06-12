package com.example.authentication_firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.authentication_firebase.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            if(binding.emailET.text.toString().isEmpty()){
                binding.emailLayout.error="Enter Email"
            }
            else if(binding.passET.text.toString().isEmpty()){
                binding.passLayout.error="Enter Password"

            }
            else if(binding.confirmPassET.text.toString().isEmpty()){
                binding.confirmPassLayout.error="Enter Confirm Password"
            }
            else if(binding.passET.text.toString()!=binding.confirmPassET.text.toString()){
                binding.confirmPassLayout.error="Password not matched"
            }
            else{
                val intent=Intent(this,OtpActivity::class.java)
                intent.putExtra("email",binding.emailET.text.toString())
                intent.putExtra("pass",binding.passET.text.toString())
                startActivity(intent)
            }
        }
    }
}