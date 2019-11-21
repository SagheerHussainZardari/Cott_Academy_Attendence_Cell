package com.sagheer.cottacademyattendencecell

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheer.cottacademyattendencecell.DataModels.ViewByStudentModel
import kotlinx.android.synthetic.main.recyler_view_layout_for_viewdataby_student.view.*

class MyAdapter(var context: Context, var dataList: ArrayList<ViewByStudentModel>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

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
        holder.view.tv_subjectName_FragmentViewByStudent.text = dataList[position].subName
        holder.view.tv_Present_FragmentViewByStudent.text = dataList[position].subPresent
        holder.view.tv_Absent_FragmentViewByStudent.text = dataList[position].subAbsent
        holder.view.tv_TotalClasses_FragmentViewByStudent.text = dataList[position].subTotalClasses
        holder.view.tv_Percentage_FragmentViewByStudent.text = dataList[position].subPercentage
    }
}