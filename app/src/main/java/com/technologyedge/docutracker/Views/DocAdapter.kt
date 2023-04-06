package com.technologyedge.docutracker.Views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.technologyedge.docutracker.DetailsFragmentDirections
import com.technologyedge.docutracker.HomeFragmentDirections
import com.technologyedge.docutracker.Models.Document
import com.technologyedge.docutracker.R
import com.technologyedge.docutracker.databinding.DocumentListBinding
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class DocAdapter(val context: Context?, private val documents: ArrayList<Document>) :
    RecyclerView.Adapter<DocAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(private var binding: DocumentListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(document: Document) {
            binding.txtTitle.text = document.title
            binding.txtDueDate.text= getFormattedDate(document.date_due)
            binding.txtRefer.text = document.refer?.refer_to


        }

        private fun getFormattedDate(dateDue: Date?): String {

            var formatter =  SimpleDateFormat("dd MMMM yyyy")
            var strDate = formatter.format(dateDue)
            return  strDate;
        }


    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            DocumentListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            var doc = documents[position]
           // val action = DetailsFragmentDirections.(doc.id)

           val action = HomeFragmentDirections.actionFirstFragmentToDetailsFragment(doc.id.toString())
            viewHolder.itemView.findNavController().navigate(action)
        }

        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        var doc = documents[position]
        viewHolder.bind(doc)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = documents.size

}
