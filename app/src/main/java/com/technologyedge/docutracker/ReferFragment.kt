package com.technologyedge.docutracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.technologyedge.docutracker.Models.Refer
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
    private val args: ReferFragmentArgs by navArgs()
    val db = Firebase.firestore
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

      binding.btnSaveRef.setOnClickListener{
          //insert into firebase
         
              db.collection("Documents").document(args.docId)
                  .set(getRef())
                  .addOnSuccessListener { documentReference ->
                      //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                      Toast.makeText(activity,"Success", Toast.LENGTH_LONG).show()
                  }
                  .addOnFailureListener { e ->
                      // Log.w(TAG, "Error adding document", e)
                      Toast.makeText(activity,"Error, check internet connectivity", Toast.LENGTH_LONG).show()
                  }
          //set calender reminder
         /* if(getDocument().refer != null)
          {
              val intent =  Intent(Intent.ACTION_INSERT)
              intent.setData(CalendarContract.Events.CONTENT_URI);
              intent.putExtra(CalendarContract.Events.TITLE, getDocument().title);
              intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,getDocument().date_due );
              intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, getDocument().date_due?.time);

          }*/
          //redirect to homefragement
          findNavController().navigate(R.id.action_referFragment_to_FirstFragment)
      }
    }

    private fun getRef(): Refer {
        return Refer(
            binding.editPersonName.text.toString(),
            getDate(binding.editDate.text.toString()),
            getDate(binding.editDateDue.text.toString()),

        )
    }
    fun getDate(dateString:String): java.util.Date
    {
        //this line is not working
        val locale = Locale("en_ZW","Zimbabwe")
        val format = SimpleDateFormat("dd/MM/yyyy",locale)
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.parse(dateString);
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}