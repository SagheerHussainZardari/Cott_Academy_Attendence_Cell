package com.sagheer.cottacademyattendencecell.Adapters


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheer.cottacademyattendencecell.DataModels.ViewByTeacherModel
import com.sagheer.cottacademyattendencecell.R
import kotlinx.android.synthetic.main.recyler_view_layout_for_viewdataby_teacher.view.*

class ViewByTeacherAdapter(var context: Context, var dataList: ArrayList<ViewByTeacherModel>) :
    RecyclerView.Adapter<ViewByTeacherAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recyler_view_layout_for_viewdataby_teacher,
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
            holder.view.tv_studentName_FragmentViewByTeacher.setBackgroundColor(Color.parseColor("#20A5F7"))
            holder.view.tv_Present_FragmentViewByTeacher.setBackgroundColor(Color.parseColor("#20A5F7"))
            holder.view.tv_Absent_FragmentViewByTeacher.setBackgroundColor(Color.parseColor("#20A5F7"))
            holder.view.tv_TotalClasses_FragmentViewByTeacher.setBackgroundColor(Color.parseColor("#20A5F7"))
            holder.view.tv_Percentage_FragmentViewByTeacher.setBackgroundColor(Color.parseColor("#20A5F7"))

            holder.view.tv_studentName_FragmentViewByTeacher.text = dataList[position].stdName
            holder.view.tv_Present_FragmentViewByTeacher.text = dataList[position].stdPresent
            holder.view.tv_Absent_FragmentViewByTeacher.text = dataList[position].stdAbsent
            holder.view.tv_TotalClasses_FragmentViewByTeacher.text =
                dataList[position].stdTotalClasses
            holder.view.tv_Percentage_FragmentViewByTeacher.text = dataList[position].stdPercentage

        } else {
            holder.view.tv_studentName_FragmentViewByTeacher.text = dataList[position].stdName
            holder.view.tv_Present_FragmentViewByTeacher.text = dataList[position].stdPresent
            holder.view.tv_Absent_FragmentViewByTeacher.text = dataList[position].stdAbsent
            holder.view.tv_TotalClasses_FragmentViewByTeacher.text =
                dataList[position].stdTotalClasses
            holder.view.tv_Percentage_FragmentViewByTeacher.text = dataList[position].stdPercentage
        }


    }
}