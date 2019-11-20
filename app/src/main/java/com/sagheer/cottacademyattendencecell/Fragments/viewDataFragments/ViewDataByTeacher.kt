package com.sagheer.cottacademyattendencecell.Fragments.viewDataFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sagheer.cottacademyattendencecell.R

/**
 * A simple [Fragment] subclass.
 */
class ViewDataByTeacher : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_data_by_teacher, container, false)
    }


}
