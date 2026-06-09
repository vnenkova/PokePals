package com.example.pokepals

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.core.content.edit
import androidx.core.graphics.toColorInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TrainerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrainerFragment : Fragment() {

    private lateinit var userNameField: EditText

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
        val view = inflater.inflate(R.layout.fragment_trainer, container, false)

        val mode : Switch = view.findViewById(R.id.mode_switch_trainer)
        val bg : ConstraintLayout = view.findViewById(R.id.bg_trainer)
        val thinkCheck : CheckBox = view.findViewById(R.id.think_check)

        thinkCheck.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                thinkCheck.setText("Understood!")
                Toast.makeText(requireContext(), "Amazing!", Toast.LENGTH_SHORT).show()
            } else {
                thinkCheck.setText("I'll think about it!")
                Toast.makeText(requireContext(), "Happy to hear it!", Toast.LENGTH_SHORT).show()
            }
        }

        mode.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                mode.text = resources.getString(R.string.light_mode)
                mode.setTextColor(resources.getColor(R.color.white))
                userNameField.setTextColor(resources.getColor(R.color.white))
                bg.setBackgroundColor("#4F3C7D".toColorInt())
                thinkCheck.setTextColor(resources.getColor(R.color.white))
            } else {
                mode.text = resources.getString(R.string.dark_mode)
                mode.setTextColor(resources.getColor(R.color.black))
                userNameField.setTextColor(resources.getColor(R.color.black))
                bg.setBackgroundColor("#a49df2".toColorInt())
                thinkCheck.setTextColor(resources.getColor(R.color.black))
            }
        }

        val profilePicBtn : Button = view.findViewById(R.id.profile_pic_btn)

        profilePicBtn.setOnClickListener {
            if (userNameField.hasFocus()) {
                userNameField.clearFocus()
            }
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivity(intent)
        }

        val userNameBtn : Button = view.findViewById(R.id.username_btn)
        userNameField = view.findViewById(R.id.username_field)

        val sharedPref = requireContext().getSharedPreferences("username", Context.MODE_PRIVATE)
        val savedUserName = sharedPref.getString("username", "")
        userNameField.setText(savedUserName)

        val attentionText : TextView = view.findViewById(R.id.attentionText)
        userNameField.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                attentionText.visibility = View.VISIBLE
            } else {
                attentionText.visibility = View.GONE
            }
        }

        userNameBtn.setOnClickListener {
            userNameField.clearFocus()
            userNameBtnClick(it)
        }

        setHasOptionsMenu(true)

        return view
    }


    public fun userNameBtnClick(view: View) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Question")
            .setMessage("Save your new username?")
            .setPositiveButton("Yes") { dialog, id ->

                val userName = userNameField.text.toString()
                if (userName.length >= 6) {

                    val sharedPref = requireContext().getSharedPreferences("username", Context.MODE_PRIVATE)
                    with (sharedPref.edit()) {
                        putString("username", userName)
                        apply()
                    }

                    userNameField.setText(userName)
                    userNameField.clearFocus()
                    Toast.makeText(requireContext(), "Username changed successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    userNameField.clearFocus()
                    Toast.makeText(requireContext(), "You haven't entered a valid username!", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("No") { dialog, id ->
                Toast.makeText(requireContext(), "Operation cancelled!", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment TrainerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrainerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}