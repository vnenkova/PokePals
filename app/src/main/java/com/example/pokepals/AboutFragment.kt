package com.example.pokepals

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {
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
        val view =  inflater.inflate(R.layout.fragment_about, container, false)

        val mystery_link : Button = view.findViewById(R.id.mysteryLink)
        mystery_link.setOnClickListener {
            val url = "https://ue-varna.bg"
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        }

        val mode : Switch = view.findViewById(R.id.mode_switch)
        val bg : ConstraintLayout = view.findViewById(R.id.bg)
        val text : TextView = view.findViewById(R.id.about_text)
        val title : TextView = view.findViewById(R.id.app_title)
        val checkedLink : CheckBox = view.findViewById(R.id.link_checked)

        checkedLink.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                checkedLink.setText("I checked it!")
                Toast.makeText(requireContext(), "Great!", Toast.LENGTH_SHORT).show()
            } else {
                checkedLink.setText("I won't!")
                Toast.makeText(requireContext(), "Why not??", Toast.LENGTH_SHORT).show()
            }
        }

        mode.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                mode.text = resources.getString(R.string.light_mode)
                mode.setTextColor(resources.getColor(R.color.white))
                text.setTextColor(resources.getColor(R.color.white))
                title.setTextColor(resources.getColor(R.color.white))
                bg.setBackgroundColor("#3C4E7D".toColorInt())
                checkedLink.setTextColor(resources.getColor(R.color.white))
            } else {
                mode.text = resources.getString(R.string.dark_mode)
                mode.setTextColor("#8A000000".toColorInt())
                text.setTextColor("#8A000000".toColorInt())
                title.setTextColor("#8A000000".toColorInt())
                bg.setBackgroundColor("#c5d7ff".toColorInt())
                checkedLink.setTextColor(resources.getColor(R.color.black))
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AboutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}