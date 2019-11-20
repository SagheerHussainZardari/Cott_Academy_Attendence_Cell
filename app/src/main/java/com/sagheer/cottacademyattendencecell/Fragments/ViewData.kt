package com.sagheer.cottacademyattendencecell.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sagheer.cottacademyattendencecell.Fragments.viewDataFragments.ViewDataByStudent
import com.sagheer.cottacademyattendencecell.Fragments.viewDataFragments.ViewDataBySubject
import com.sagheer.cottacademyattendencecell.Fragments.viewDataFragments.ViewDataByTeacher
import com.sagheer.cottacademyattendencecell.MainActivity
import com.sagheer.cottacademyattendencecell.R
import kotlinx.android.synthetic.main.fragment_view_data.*

/**
 * A simple [Fragment] subclass.
 */
class ViewData : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_data, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_ViewByTeacher.setOnClickListener {
            (activity as MainActivity).openFragment(ViewDataByTeacher())
        }

        btn_ViewBySubject.setOnClickListener {
            (activity as MainActivity).openFragment(ViewDataBySubject())
        }

        btn_ViewByStudent.setOnClickListener {
            (activity as MainActivity).openFragment(ViewDataByStudent())
        }


    }


}
