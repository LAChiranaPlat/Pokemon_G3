package com.example.pok2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.pok2.databinding.ActivityPokemonDetailsBinding

class PokemonDetails : AppCompatActivity() {

    lateinit var layouts:ActivityPokemonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layouts= ActivityPokemonDetailsBinding.inflate(layoutInflater)

        setContentView(layouts.root)

        layouts.textView.text=intent.getStringExtra("nombre")
        layouts.textView2.text=intent.getStringExtra("atack")

        Glide
            .with(this)
            .load(intent.getStringExtra("foto"))
            .into(layouts.imageView)
    }
}