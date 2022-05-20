package com.example.pok2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pok2.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var vistas:ActivityMainBinding
    var flagPresentacion=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vistas= ActivityMainBinding.inflate(layoutInflater)

        setContentView(vistas.root)

        val lista=vistas.lista
        val pokemones:ArrayList<Pokemon> = ArrayList()


        if(savedInstanceState != null){
            flagPresentacion=savedInstanceState.getBoolean("struct")
            Log.i("result",flagPresentacion.toString())
        }

        if(flagPresentacion){
            lista.layoutManager=GridLayoutManager(this,2)
        }else{
            lista.layoutManager=LinearLayoutManager(this)
        }

        var adapter:adapterPokemon

        val request=Volley.newRequestQueue(this)
        val url="https://pokeapi.co/api/v2/pokemon/?offset=150&limit=150"

        /*INFO POKEMON*/
        var nombre=""
        var especie=""
        var ataques=""
        var foto=""


        val r= JsonObjectRequest(url,
                Response.Listener {

                    val arrayPokemon=JSONArray(it.get("results").toString())

                    var objPokemon:JSONObject

                    for(index in 0 until 20)
                    {
                        objPokemon=JSONObject(arrayPokemon.get(index).toString())

                        /***********************************************************/
                        val requestPokemon=Volley.newRequestQueue(this)


                        val rPok= JsonObjectRequest(objPokemon.get("url").toString(),
                            Response.Listener {

                                val tipo=JSONArray(it.get("types").toString())
                                /*OBTENIENDO EL NOMBRE*/
                                nombre=it.get("name").toString();

                                val typeElement1=JSONObject(tipo.get(0).toString())
                                val objetoType=JSONObject(typeElement1.get("type").toString())

                                /*OBTENIENDO EL TIPO*/
                                especie=objetoType.get("name").toString()


                                /*OBTENIENDO LOS ATAQUES*/
                                val movimientos=JSONArray(it.get("moves").toString())

                                for(item in 0 until movimientos.length()){
                                    val objAtaques=JSONObject(movimientos.get(item).toString())
                                    val nameAtack=JSONObject(objAtaques.get("move").toString())
                                    ataques += " " +nameAtack.get("name").toString()
                                }

                                /*OBTENIENDO LA URL DE LA FOTO*/
                                foto=JSONObject(it.get("sprites").toString()).get("front_default").toString()

                                pokemones.add(Pokemon(nombre,especie,ataques,foto))
                                adapter= adapterPokemon(pokemones)
                                lista.adapter=adapter
                                //Log.i("result","$nombre [$especie]: $foto ")

                            },
                            Response.ErrorListener {
                                Log.i("result",it.toString())
                            }
                        )

                        requestPokemon.add(rPok)

                        /***********************************************************/

                    }


                },
            Response.ErrorListener {  }
            )

        request.add(r)

        vistas.btnCambiar.setOnClickListener {
            if(flagPresentacion){
                lista.layoutManager=LinearLayoutManager(this)
                flagPresentacion=false
            }else{
                lista.layoutManager=GridLayoutManager(this,2)
                flagPresentacion=true
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("struct",flagPresentacion)

    }
}