package com.technologyedge.docutracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.technologyedge.docutracker.Models.Document
import com.technologyedge.docutracker.Models.Refer
import com.technologyedge.docutracker.databinding.FragmentDetailsBinding
import com.technologyedge.docutracker.util.Constants
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

        var doc:Document? = null
        var ref: Refer? = null
        val currentDocument = args.currentDocument
        db.collection(Constants.DOCUMENTS).document(currentDocument).collection(Constants.REFER).document()
            .get().addOnSuccessListener { documentSnapshot ->
                ref = documentSnapshot.toObject<Refer>()
               // assignDoc(ref)
            }
        val docRef = db.collection(Constants.DOCUMENTS).document(currentDocument)
        docRef.get().addOnSuccessListener { documentSnapshot ->
             doc = documentSnapshot.toObject<Document>()
            doc?.refer = ref
            assignDoc(doc)
        }
        binding.btnRefer.isVisible = doc?.refer != null
        binding.btnRefer.setOnClickListener{
            val action = DetailsFragmentDirections.actionDetailsFragmentToReferFragment(
                currentDocument
            )
            findNavController().navigate(action)
        }

    }

    private fun assignDoc(doc: Document?) {
        binding.txtDetTitle.text = doc?.title.toString()
        binding.txtDtDescription.text = doc?.description.toString()
        binding.txtDtRef.text = doc?.reference.toString()
        binding.txtDtDueDate.text = getFormattedDate(doc?.date_due)
        binding.txtDtDateReceived.text = getFormattedDate(doc?.date_received)
        binding.txtDtInstruction.text = doc?.instruction.toString()
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