package com.sagheer.cottacademyattendencecell.Fragments.viewDataFragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sagheer.cottacademyattendencecell.Adapters.ViewByTeacherAdapter2
import com.sagheer.cottacademyattendencecell.DataModels.ViewByTeacherModel
import com.sagheer.cottacademyattendencecell.R
import kotlinx.android.synthetic.main.fragment_view_data_by_teacher.*

/**
 * A simple [Fragment] subclass.
 */
class ViewDataByTeacher : Fragment() {
    var mDBRoot = FirebaseDatabase.getInstance().reference
    var teachersList = ArrayList<String>()
    var allStudentsOfThatTeacher = ArrayList<String>()
    var dataForTeachers = ArrayList<ViewByTeacherModel>()
    var present = ""
    var absent = ""
    var totalClasses = ""
    var percentage = ""
    var subName = ""
    var stdName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_data_by_teacher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        autoTextCompleteViewDataByTeacher.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_item,
                teachersList
            )
        )
        dataForTeachers.add(ViewByTeacherModel("StdName", "P", "A", "T", "%"))

        setListnerForAutoTextComplete()
    }

    private fun setListnerForAutoTextComplete() {
        autoTextCompleteViewDataByTeacher.setOnItemClickListener { parent, view, position, id ->
            tv_TeacherName_viewbyTeacher.text = autoTextCompleteViewDataByTeacher.text.toString()
            setRecyclerViewForCurrentTeacher(autoTextCompleteViewDataByTeacher.text.toString())
        }
    }

    private fun setRecyclerViewForCurrentTeacher(teacher: String) {
        allStudentsOfThatTeacher.clear()
        allStudentsOfThatTeacher.add("Student Name")
        mDBRoot.child("Students").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                for (student in p0.children) {
                    stdName = student.child("stdName").value.toString()
                    for (subject in student.children) {
                        if (subject.child("subTeacher").value == teacher) {
                            if (!allStudentsOfThatTeacher.contains(stdName))
                                allStudentsOfThatTeacher.add(stdName)
                        }
                    }
                }
                Log.d("a", "data: $allStudentsOfThatTeacher")
                setRecyclerViewForCurrentTeacherStudents()
            }
        })

    }

    private fun setRecyclerViewForCurrentTeacherStudents() {

        recyclerView_ViewByTeacher.setHasFixedSize(true)
        recyclerView_ViewByTeacher.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_ViewByTeacher.adapter =
            ViewByTeacherAdapter2(
                requireContext(),
                allStudentsOfThatTeacher
            )
    }

    override fun onStart() {
        super.onStart()
        setDataForAutoText()
    }

    private fun setDataForAutoText() {
        mDBRoot.child("Teachers").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                for (teacher in p0.children)
                    teachersList.add(teacher.value.toString())
            }
        })
    }

}
