package com.project.newsly.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.project.newsly.MainActivity
import com.project.newsly.databinding.ActivityLogInBinding
import com.project.newsly.extensions.Extensions.toast
import com.project.newsly.utils.FirebaseUtils.firebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    lateinit var logInEmail: String
    lateinit var logInPassword: String
    lateinit var logInInputArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        logInInputArray = arrayOf(binding.logInEmail, binding.logInPassword)

        binding.signInText.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }

        binding.logInBtn.setOnClickListener {
            logInUser()
        }
    }

    private fun notEmpty(): Boolean = logInEmail.isNotEmpty() && logInPassword.isNotEmpty()

    private fun logInUser() {
        logInEmail = binding.logInEmail.text.toString().trim()
        logInPassword = binding.logInPassword.text.toString().trim()

        if(notEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(logInEmail, logInPassword)
                .addOnCompleteListener { signIn ->
                    if(signIn.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        toast("Logged In Successfully!")
                        finish()
                    } else {
                        toast("Log In Failed!!")
                    }
                }
        } else {
            logInInputArray.forEach { input ->
                if(input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required!"
                }
            }
        }
    }
}