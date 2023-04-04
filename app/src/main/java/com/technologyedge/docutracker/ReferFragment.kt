package com.technologyedge.docutracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.technologyedge.docutracker.Models.Document
import com.technologyedge.docutracker.databinding.FragmentReferBinding
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ReferFragment : Fragment() {

    private var _binding: FragmentReferBinding? = null
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReferBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editDate.setOnClickListener {

            val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker
                .Builder
                .datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText("Select date refered")
            val datePicker = datePickerBuilder.build()
            datePicker.show( childFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener {
                val date = sdf.format(it)
                binding.editDate.setText(date)
            }
        }
        binding.editDateDue.setOnClickListener {

            val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker
                .Builder
                .datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText("Select return date")
            val datePicker = datePickerBuilder.build()
            datePicker.show( childFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener {
                val date = sdf.format(it)
                binding.editDateDue.setText(date)
            }
        }

       /* binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}