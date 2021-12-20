package com.example.capstoneproject


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;







class Chat : AppCompatActivity() {
    var layout: LinearLayout? = null
    var sendButton: ImageView? = null
    var messageArea: EditText? = null
    var scrollView: ScrollView? = null
    var reference1: Firebase? = null
    var reference2: Firebase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        layout = findViewById<View>(R.id.layout1) as LinearLayout
        sendButton = findViewById<View>(R.id.sendButton) as ImageView
        messageArea = findViewById<View>(R.id.messageArea) as EditText
        scrollView = findViewById<View>(R.id.scrollView) as ScrollView
        Firebase.setAndroidContext(this)
        reference1 =
            Firebase("https://mycapstoneprojecta-default-rtdb.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith)
        reference2 =
            Firebase("https://mycapstoneprojecta-default-rtdb.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username)
        sendButton!!.setOnClickListener {
            val messageText = messageArea!!.text.toString()
            if (messageText != "") {
                val map: MutableMap<String, String> =
                    HashMap()
                map["message"] = messageText
                map["user"] = UserDetails.username
                reference1!!.push().setValue(map)
                reference2!!.push().setValue(map)
            }
        }
        reference1!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String) {
                val map = dataSnapshot.value as Map<String, Any>
                val message = map["message"].toString()
                val userName = map["user"].toString()
                if (userName == UserDetails.username) {
                    addMessageBox("You:-\n$message", 1)
                } else {
                    addMessageBox(
                        """
                            ${UserDetails.chatWith}:-
                            $message
                            """.trimIndent(), 2
                    )
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {}
            override fun onCancelled(firebaseError: FirebaseError) {}
        })
    }

    fun addMessageBox(message: String?, type: Int) {
        val textView = TextView(this@Chat)
        textView.text = message
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(0, 0, 0, 10)
        textView.layoutParams = lp
        if (type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1)
        } else {
            textView.setBackgroundResource(R.drawable.rounded_corner2)
        }
        layout!!.addView(textView)
        scrollView!!.fullScroll(View.FOCUS_DOWN)
    }
}