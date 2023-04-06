package com.technologyedge.docutracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.technologyedge.docutracker.Models.Document
import com.technologyedge.docutracker.Views.DocAdapter
import com.technologyedge.docutracker.databinding.FragmentDetailsBinding
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val args: DetailsFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val docRef = db.collection("documents").document(args.currentDocument.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val city = documentSnapshot.toObject<Document>()
            binding.txtDetTitle.text = city?.title.toString()
            binding.txtDtDescription.text = city?.description.toString()
            binding.txtDtRef.text = city?.reference.toString()
            binding.txtDtDueDate.text = getFormattedDate(city?.date_due)
            binding.txtDtDateReceived.text = getFormattedDate(city?.date_received)
            binding.txtDtInstruction.text = city?.instruction.toString()
        }
        binding.btnRefer.setOnClickListener{
            val action = DetailsFragmentDirections.actionDetailsFragmentToReferFragment(args.currentDocument)
            findNavController().navigate(action)
        }
    }

    private fun getFormattedDate(dateDue: Date?): String {

        var formatter =  SimpleDateFormat("dd MMMM yyyy")
        var strDate = formatter.format(dateDue)
        return  strDate;
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}