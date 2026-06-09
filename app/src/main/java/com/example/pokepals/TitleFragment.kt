package com.example.pokepals

import android.R.attr.rating
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.core.net.toUri



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_title, container, false)

        val browseButton : Button = view.findViewById(R.id.browseBtn)
        val trainerButton : Button = view.findViewById(R.id.trainerBtn)
        val aboutButton : Button = view.findViewById(R.id.aboutBtn)

        browseButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_titleFragment_to_browseFragment)
        }

        trainerButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_titleFragment_to_trainerFragment)
        }

        aboutButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_titleFragment_to_aboutFragment)
        }

        val mode : Switch = view.findViewById(R.id.mode_switch_title)
        val rating : RatingBar = view.findViewById(R.id.ratingBar)
        val yellow_bg : TextView = view.findViewById(R.id.yellow)
        val pink_bg : TextView = view.findViewById(R.id.pink)
        val purple_bg : TextView = view.findViewById(R.id.purple)
        val blue_bg : TextView = view.findViewById(R.id.blue)
        val welcomeText : TextView = view.findViewById(R.id.welcome_txt)
        val rateText : TextView = view.findViewById(R.id.rate_text)

        mode.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                mode.text = resources.getString(R.string.light_mode)
                mode.setTextColor(resources.getColor(R.color.white))
                yellow_bg.setBackgroundColor("#7C7D3C".toColorInt())
                pink_bg.setBackgroundColor("#7D3C7C".toColorInt())
                purple_bg.setBackgroundColor("#4F3C7D".toColorInt())
                blue_bg.setBackgroundColor("#3C4E7D".toColorInt())
                welcomeText.setTextColor("#fff1ab".toColorInt())
                rateText.setTextColor(resources.getColor(R.color.white))
            } else {
                mode.text = resources.getString(R.string.dark_mode)
                mode.setTextColor(resources.getColor(R.color.black))
                yellow_bg.setBackgroundColor("#fff1ab".toColorInt())
                pink_bg.setBackgroundColor("#eeb8ea".toColorInt())
                purple_bg.setBackgroundColor("#a49df2".toColorInt())
                blue_bg.setBackgroundColor("#c5d7ff".toColorInt())
                welcomeText.setTextColor("#8a750f".toColorInt())
                rateText.setTextColor("#8A000000".toColorInt())
            }
        }

        rating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
           if (fromUser) {
               rateApp(ratingBar)
           }
        }

        setHasOptionsMenu(true)

        return view
    }

    public fun rateApp(ratingBar: RatingBar) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Question")
            .setMessage("Rate us in Play Store?")
            .setPositiveButton("Sure!") { dialog, id ->
                val intent = Intent(Intent.ACTION_VIEW, "market://search?q=apps".toUri())
                startActivity(intent)
            }
            .setNegativeButton("Maybe later") { dialog, id ->
                Toast.makeText(requireContext(), "OK!", Toast.LENGTH_SHORT).show()
                ratingBar.rating = 0f
            }
        val dialog = builder.create()
        dialog.show()
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
         * @return A new instance of fragment TitleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TitleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}