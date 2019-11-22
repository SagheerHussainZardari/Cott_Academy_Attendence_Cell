package com.sagheer.cottacademyattendencecell.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheer.cottacademyattendencecell.DataModels.ViewByStudentModel
import com.sagheer.cottacademyattendencecell.R

class ViewByTeacherAdapter(var context: Context, var dataList: ArrayList<ViewByStudentModel>) :
    RecyclerView.Adapter<ViewByTeacherAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recyler_view_layout_for_viewdataby_student,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }
}