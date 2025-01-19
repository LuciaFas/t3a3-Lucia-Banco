package com.example.banco_lufaga.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banco_lufaga.databinding.ActivityWelcomeBinding
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.banco_lufaga.R
import com.example.banco_lufaga.bd.CajeroApplication
import com.example.banco_lufaga.pojo.CajeroEntity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonEntrar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val animationView = findViewById<LottieAnimationView>(R.id.animation)
        animationView.setAnimation(R.raw.money_animation)
        animationView.playAnimation()
        animationView.repeatCount = LottieDrawable.INFINITE

        /*Thread{
            val cajerosEntityLists : List<CajeroEntity> = listOf(
                CajeroEntity(1, "Carrer del Clariano, 1, 46021 Valencia, Valencia, España",
                    39.47600769999999, -0.3524475000000393, ""),
                CajeroEntity(2, "Avinguda del Cardenal Benlloch, 65, 46021 València, Valencia, España",
                    39.4710366, -0.3547525000000178, ""),
                        CajeroEntity(3, "Av. del Port, 237, 46011 València, Valencia, España",
                    39.46161999999999, -0.3376299999999901, ""),
                CajeroEntity(4, "Carrer del Batxiller, 6, 46010 València, Valencia, España",
                    39.4826729, -0.3639118999999482, ""),
                CajeroEntity(5, "Av. del Regne de València, 2, 46005 València, Valencia, España",
                    39.4647669, -0.3732760000000326, "")
            )
            CajeroApplication.database.cajeroDao().insertAll(cajerosEntityLists)
        }.start()*/


        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}