package com.example.pok2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.pok2.databinding.ItemPokemonBinding

class adapterPokemon(var Pokemones:ArrayList<Pokemon>):RecyclerView.Adapter<adapterPokemon.ContentViews>() {

    lateinit var ctx:Context

    class ContentViews(views:ItemPokemonBinding):RecyclerView.ViewHolder(views.root) {

        val foto:ImageView
        val nombre:TextView
        val tipo:TextView
        val ataques:TextView

        init {
            foto=views.imgPokemon
            nombre=views.txtNombre
            tipo=views.txtTipo
            ataques=views.txtAtaques
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterPokemon.ContentViews {
        ctx=parent.context
        val views=ItemPokemonBinding.inflate(LayoutInflater.from(ctx),parent,false)
        return ContentViews(views)

    }

    override fun onBindViewHolder(holder: adapterPokemon.ContentViews, position: Int) {

        val item=Pokemones.get(position)

        holder.apply {

            nombre.text=item.nombres
            tipo.text=item.tipo
            ataques.text=item.ataques

        }

        Glide
            .with(ctx)
            .load(item.foto)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .placeholder(R.drawable.who)
            .into(holder.foto)

        holder.foto.setOnClickListener {
            ctx.startActivity(Intent(ctx,PokemonDetails::class.java).apply {
                putExtra("nombre",item.nombres)
                putExtra("foto",item.foto)
                putExtra("atack",item.ataques)
            })
        }
    }

    override fun getItemCount()=Pokemones.size

}