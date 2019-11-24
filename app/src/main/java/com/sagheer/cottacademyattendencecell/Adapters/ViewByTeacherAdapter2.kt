package com.sagheer.cottacademyattendencecell.Adapters


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheer.cottacademyattendencecell.R
import kotlinx.android.synthetic.main.recyler_view_layout_for_viewdataby_teacher2.view.*

class ViewByTeacherAdapter2(var context: Context, var dataList: ArrayList<String>) :
    RecyclerView.Adapter<ViewByTeacherAdapter2.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recyler_view_layout_for_viewdataby_teacher2,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == 0) {
            holder.view.tv_studentName_viewByTeacher2?.text = dataList[position]
            holder.view.tv_studentName_viewByTeacher2?.setBackgroundColor(Color.parseColor("#20A5F7"))
        } else {
            holder.view.tv_studentName_viewByTeacher2?.text = dataList[position]
        }


    }
}