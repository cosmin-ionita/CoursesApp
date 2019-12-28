package android.example.courses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
    }

    fun loginClick(view: View) {
        val email = findViewById<TextInputEditText>(R.id.emailEditText).text.toString()
        val pass = findViewById<TextInputEditText>(R.id.passEditText).text.toString()

        if (email == "" || pass == "") {
            invalidCredentials()
            return
        }

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, NavigationActivity::class.java)
                    startActivity(intent)

                } else {
                    invalidCredentials()
                }
            }

        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
    }

    private fun invalidCredentials() {
        Toast.makeText(
            baseContext, "Authentication failed! Please try again!",
            Toast.LENGTH_SHORT
        ).show()
    }
}
