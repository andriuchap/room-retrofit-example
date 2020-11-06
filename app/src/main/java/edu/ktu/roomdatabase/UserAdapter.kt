package edu.ktu.roomdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(data:MutableList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>()
{
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        var nameText = v.findViewById<TextView>(R.id.name_text)
        var surnameText = v.findViewById<TextView>(R.id.surname_text)
    }

    var data = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var user = data[position]

        holder.nameText.text = user.firstName
        holder.surnameText.text = user.lastName
    }


}