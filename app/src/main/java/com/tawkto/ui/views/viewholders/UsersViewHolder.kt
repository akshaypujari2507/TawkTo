package com.tawkto.ui.views.viewholders

import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso
import com.tawkto.R
import com.tawkto.data.local.entities.Users
import com.tawkto.ui.interfaces.OnUserClicked


class UsersViewHolder(
    itemView: View?,
    val listener: OnUserClicked
) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {

    var tv_user_name: TextView
    var tv_type: TextView
    var civ_profile: CircularImageView
    var iv_notes: ImageView
    private var mUser: Users? = null

    init {
        tv_user_name = itemView!!.findViewById(R.id.tv_user_name)
        tv_type = itemView!!.findViewById(R.id.tv_type)
        civ_profile = itemView!!.findViewById(R.id.civ_profile)
        iv_notes = itemView!!.findViewById(R.id.iv_notes)

        itemView.setOnClickListener(this)

    }

    fun bindNowShowingData(mUser: Users?) {
        if (mUser == null) {
            return
        } else {

            this.mUser = mUser

            tv_user_name.setText("Name: ${mUser.login}")
            tv_type.setText("Type: ${mUser.type}")
            Picasso.get()
                .load(mUser.avatar_url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(civ_profile);
            println("Akshay ${mUser.notes}")
            if (mUser.notes.toString().equals("null")) {
//            if (mUser.notes!!.isNotEmpty() || mUser.notes!!.isNotBlank()) {
                iv_notes.visibility = View.GONE
            }
//            tv_Item.setPaintFlags(tv_Item.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        }
    }

    override fun onClick(p0: View?) {
        val position: Int = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            listener.onItemClicked(mUser!!)
        }
    }

}