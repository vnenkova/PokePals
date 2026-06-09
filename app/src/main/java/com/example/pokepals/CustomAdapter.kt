package com.example.pokepals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val dataSet : ArrayList<Pokemon>, private val context : Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val sharedPref = context.getSharedPreferences("pokemon_prefs", Context.MODE_PRIVATE)
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonPhoto_image : ImageView
        val pokemonName_txt : TextView
        val pokemonType_txt : TextView
        val have_check : CheckBox

        init {
            pokemonPhoto_image = view.findViewById(R.id.photo)
            pokemonName_txt = view.findViewById(R.id.name_txt)
            pokemonType_txt = view.findViewById(R.id.type_txt)
            have_check = view.findViewById(R.id.have_check)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.pokemon_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var currentItem = dataSet[position]
        val imageResId = viewHolder.itemView.context.resources.getIdentifier(currentItem.pokemon_photo_id, "drawable", viewHolder.itemView.context.packageName)
        viewHolder.pokemonPhoto_image.setImageResource(imageResId)
        viewHolder.pokemonName_txt.text = currentItem.pokemon_name
        viewHolder.pokemonType_txt.text = currentItem.pokemon_type

        viewHolder.have_check.setOnCheckedChangeListener(null)

        val check_state = sharedPref.getBoolean(currentItem.pokemon_name, false)
        viewHolder.have_check.isChecked = check_state

        viewHolder.have_check.setOnCheckedChangeListener { compoundButton, isChecked ->
            sharedPref.edit().putBoolean(currentItem.pokemon_name, isChecked).apply()

            if (isChecked) {
                Toast.makeText(context, "Yey! You've obtained another pokemon!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Pokemon removed from your collection!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = dataSet.size

}