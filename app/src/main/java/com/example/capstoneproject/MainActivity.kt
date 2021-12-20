package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile_page.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)


        val emailId = intent.getStringExtra("email_id")

        tv_email_id.text = "Email ID :: $emailId"
        // END


        btn_logout.setOnClickListener {
            // Logout from app.
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, UserAuthenticatin::class.java))
            finish()


            // END
        }
        btn_Profile.setOnClickListener {
            // Profile login from app.
            FirebaseAuth.getInstance().currentUser

            startActivity(Intent(this@MainActivity, ProfilePage::class.java))
            finish()


            // END
        }
        btn_Home.setOnClickListener {
            // Message board from app.
            FirebaseAuth.getInstance().currentUser

            startActivity(Intent(this@MainActivity, Users::class.java))
            finish()


            // END
        }
    }
}
