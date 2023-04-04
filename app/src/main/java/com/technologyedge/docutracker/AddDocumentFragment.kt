package com.technologyedge.docutracker

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.technologyedge.docutracker.Models.Document
import com.technologyedge.docutracker.databinding.FragmentAddDocumentBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddDocumentFragment : Fragment() {

    private var _binding: FragmentAddDocumentBinding? = null

    val db = Firebase.firestore
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddDocumentBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.app_bar_search)
        item.isVisible = false
        val ref = menu.findItem(R.id.refer)
        ref.isVisible = false
        val rl = menu.findItem(R.id.referedlist)
        rl.isVisible = false
        val r = menu.findItem(R.id._return)
        r.isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editDateReceived.setOnClickListener {

            val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker
                .Builder
                .datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText("Select date received")
            val datePicker = datePickerBuilder.build()
            datePicker.show( childFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener {
                val date = sdf.format(it)
                binding.editDateReceived.setText(date)
            }
        }
        binding.editDueDate.setOnClickListener {

            val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker
                .Builder
                .datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText("Select due date")
            val datePicker = datePickerBuilder.build()
            datePicker.show( childFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener {
                val date = sdf.format(it)
                binding.editDueDate.setText(date)
            }
        }

        binding.btnSave.setOnClickListener {
            //Submit to Firestore
            // Add a new document with a generated ID
            db.collection("documents")
                .add(getDocument())
                .addOnSuccessListener { documentReference ->
                    //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(activity,"Success",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                   // Log.w(TAG, "Error adding document", e)
                    Toast.makeText(activity,"Error, check internet connectivity",Toast.LENGTH_LONG).show()
               }


            //If there is a due date add Title and due date to google calendar
            //return to home fragrament
            findNavController().navigate(R.id.action_FirstFragment_to_detailsFragment)
        }
    }

    fun getDocument():Document
    {
        return Document(
            null,
            binding.editTitle.text.toString(),
            binding.editReference.text.toString(),
            binding.editDescription.text.toString(),
            binding.editInstruction.text.toString(),
            binding.editType.text.toString(),
            getDate(binding.editDateReceived.text.toString()),
            getDate(binding.editDueDate.text.toString()),
            null,
            null
        )
    }
    fun getDate(dateString:String): java.util.Date
    {
        //this line is not working
      var format = SimpleDateFormat("yyyy-MM-dd")

      return try {
          return format.parse(dateString)
      } catch (e: ParseException) {
          return Date();
      }



    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}