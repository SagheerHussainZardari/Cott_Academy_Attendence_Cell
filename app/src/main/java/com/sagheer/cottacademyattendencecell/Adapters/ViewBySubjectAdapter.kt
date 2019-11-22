package com.sagheer.cottacademyattendencecell.Adapters


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sagheer.cottacademyattendencecell.DataModels.ViewBySubjectModel
import com.sagheer.cottacademyattendencecell.R
import kotlinx.android.synthetic.main.recyler_view_layout_for_viewdataby_subject.view.*

class ViewBySubjectAdapter(var context: Context, var dataList: ArrayList<ViewBySubjectModel>) :
    RecyclerView.Adapter<ViewBySubjectAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recyler_view_layout_for_viewdataby_subject,
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
            holder.view.tv_studentName_FragmentViewBySubject.setBackgroundColor(Color.parseColor("#20A5F7"))
            holder.view.tv_Present_FragmentViewBySubject.setBackgroundColor(Color.parseColor("#20A5F7"))
            holder.view.tv_Absent_FragmentViewBySubject.setBackgroundColor(Color.parseColor("#20A5F7"))
            holder.view.tv_TotalClasses_FragmentViewBySubject.setBackgroundColor(Color.parseColor("#20A5F7"))
            holder.view.tv_Percentage_FragmentViewBySubject.setBackgroundColor(Color.parseColor("#20A5F7"))

            holder.view.tv_studentName_FragmentViewBySubject.text = dataList[position].stdName
            holder.view.tv_Present_FragmentViewBySubject.text = dataList[position].stdPresent
            holder.view.tv_Absent_FragmentViewBySubject.text = dataList[position].stdAbsent
            holder.view.tv_TotalClasses_FragmentViewBySubject.text =
                dataList[position].stdTotalClasses
            holder.view.tv_Percentage_FragmentViewBySubject.text = dataList[position].stdPercentage

        } else {
            holder.view.tv_studentName_FragmentViewBySubject.text = dataList[position].stdName
            holder.view.tv_Present_FragmentViewBySubject.text = dataList[position].stdPresent
            holder.view.tv_Absent_FragmentViewBySubject.text = dataList[position].stdAbsent
            holder.view.tv_TotalClasses_FragmentViewBySubject.text =
                dataList[position].stdTotalClasses
            holder.view.tv_Percentage_FragmentViewBySubject.text = dataList[position].stdPercentage
        }


    }
}