package com.tawkto.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.tawkto.R
import com.tawkto.data.local.entities.Users
import com.tawkto.ui.interfaces.OnUserClicked
import com.tawkto.ui.views.viewholders.UsersViewHolder
import java.util.*


class UsersAdapter(private val listener: OnUserClicked) : RecyclerView.Adapter<UsersViewHolder>(),
    Filterable {

    public var filteredList: MutableList<Users> = arrayListOf()

    var users: List<Users>? = null
        set(value) {
            field = value
            filteredList.clear()
            filteredList.addAll(value!!)
            notifyDataSetChanged()
        }

    override fun getItemCount() = filteredList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item, parent, false)
        return UsersViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user: Users? = filteredList?.get(position)!!
        if (user != null) {
            val viewHolder = holder as UsersViewHolder
            viewHolder.bindNowShowingData(user)
        } else {
            notifyItemRemoved(position)
        }
    }

    override fun getFilter(): Filter {
//        TODO("Not yet implemented")
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredList.clear()
                if (charSearch.isEmpty()) {
                    filteredList.addAll(users!!)
                } else {
                    for (row in users!!) {
                        if (row.login!!.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            filteredList.add(row)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }

        }

    }

}
