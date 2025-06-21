package com.example.authentication_firebase

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeScreen : AppCompatActivity() {

    private var points = 0
    private lateinit var totalPointsText: TextView

    // Deklarasi variabel untuk referensi Firebase Database
    // Kita akan inisialisasi di dalam onCreate setelah dapat USER_ID
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        totalPointsText = findViewById(R.id.totalPoints)

        // 1. Ambil UID yang dikirim dari MainActivity
        val userId = intent.getStringExtra("USER_ID")

        // 2. Validasi UID. Jika tidak ada, jangan lanjutkan.
        if (userId.isNullOrEmpty()) {
            Toast.makeText(this, "User ID not found. Cannot sync points.", Toast.LENGTH_LONG).show()
            Log.e("Firebase", "User ID is null or empty. Finishing activity.")
            finish() // Tutup HomeScreen jika tidak ada user ID
            return // Hentikan eksekusi onCreate lebih lanjut
        }

        // 3. Inisialisasi referensi database dengan UID yang dinamis
        val dbUrl = "https://authentication-firebase-dad68-default-rtdb.asia-southeast1.firebasedatabase.app"
        database = Firebase.database(dbUrl).reference.child("users").child(userId).child("points")
        // =======================================================

        // Memuat poin dari Firebase saat layar dibuka (kode ini tetap sama)
        loadPointsFromFirebase()

        val espressoCard = findViewById<CardView>(R.id.espressoCard)
        val blendedCard = findViewById<CardView>(R.id.blendedCard)
        val brewCoffeeCard = findViewById<CardView>(R.id.brewCoffeeCard)
        val othersCard = findViewById<CardView>(R.id.othersCard)

        espressoCard.setOnClickListener {
            addPoints(10)
        }

        blendedCard.setOnClickListener {
            addPoints(15)
        }

        brewCoffeeCard.setOnClickListener {
            addPoints(20)
        }

        othersCard.setOnClickListener {
            addPoints(5)
        }
    }

    // Fungsi addPoints dan loadPointsFromFirebase tidak perlu diubah sama sekali,
    // karena sudah menggunakan variabel 'database' yang sekarang mengarah ke path yang benar.
    private fun addPoints(amount: Int) {
        points += amount
        totalPointsText.text = "Total Points: $points"

        database.setValue(points)
            .addOnSuccessListener {
                Log.d("Firebase", "Points successfully written!")
            }
            .addOnFailureListener {
                Log.e("Firebase", "Error writing points", it)
            }
    }

    private fun loadPointsFromFirebase() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val savedPoints = snapshot.getValue(Int::class.java)
                    if (savedPoints != null) {
                        points = savedPoints
                        totalPointsText.text = "Total Points: $points"
                        Log.d("Firebase", "Points loaded: $points")
                    }
                } else {
                    Log.d("Firebase", "No points data found for this user.")
                    totalPointsText.text = "Total Points: 0"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value.", error.toException())
            }
        })
    }
}