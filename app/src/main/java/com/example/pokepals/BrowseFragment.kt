package com.example.pokepals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.io.InputStreamReader

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BrowseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BrowseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_browse, container, false)

        val textView : TextView = view.findViewById(R.id.textView)
        val bg : ConstraintLayout = view.findViewById(R.id.bg_browse)
        val mode : Switch = view.findViewById(R.id.mode_switch_browse)
        mode.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                mode.text = resources.getString(R.string.light_mode)
                mode.setTextColor(resources.getColor(R.color.white))
                textView.setBackgroundColor(resources.getColor(R.color.black))
                textView.setTextColor(resources.getColor(R.color.white))
                bg.setBackgroundColor("#7D3C7C".toColorInt())
            } else {
                mode.text = resources.getString(R.string.dark_mode)
                mode.setTextColor(resources.getColor(R.color.black))
                textView.setBackgroundColor(resources.getColor(R.color.white))
                textView.setTextColor("#8A000000".toColorInt())
                bg.setBackgroundColor("#eeb8ea".toColorInt())
            }
        }

        try {
            val pokemon_list : ArrayList<Pokemon> = ArrayList()
            val fileInputStream = requireContext().assets.open("pokemons.txt")
            val inputReader = InputStreamReader(fileInputStream)
            inputReader.forEachLine {
                val pokemon_data : List<String> = it.split("|")
                    pokemon_list.add(Pokemon(
                        pokemon_data[0],
                        pokemon_data[1],
                        pokemon_data[2])
                    )
            }

            val customAdapter = CustomAdapter(pokemon_list, requireContext())
            val recyclerView : RecyclerView = view.findViewById(R.id.recycler_view)
            recyclerView.adapter = customAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        } catch (e : IOException) { }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.info_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BrowseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BrowseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}