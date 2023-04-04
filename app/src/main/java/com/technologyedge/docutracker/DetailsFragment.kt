package com.technologyedge.docutracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.technologyedge.docutracker.Models.Document
import com.technologyedge.docutracker.Views.DocAdapter
import com.technologyedge.docutracker.databinding.FragmentDetailsBinding
import com.technologyedge.docutracker.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    //private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val db = Firebase.firestore
    private var adapter: DocAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*adapter = DocAdapter(context,getDocuments())
        binding.recyclerview.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerview.adapter = adapter*/
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        /*viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }*/
    }

    private fun getDocuments(): ArrayList<Document> {
        var documents:ArrayList<Document> = ArrayList()
        db.collection("documents")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    var doc =document.toObject(Document::class.java)
                    doc.id = document.id

                     documents.add(doc)
                    adapter?.notifyDataSetChanged()


                   // Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity,"Failed to load",Toast.LENGTH_LONG).show()
            }
        return documents
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}