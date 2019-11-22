package com.sagheer.cottacademyattendencecell.Fragments.viewDataFragments


import android.os.Bundle
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
import com.sagheer.cottacademyattendencecell.Adapters.ViewByStudentAdapter
import com.sagheer.cottacademyattendencecell.DataModels.ViewByStudentModel
import com.sagheer.cottacademyattendencecell.R
import kotlinx.android.synthetic.main.fragment_view_data_by_student.*

/**
 * A simple [Fragment] subclass.
 */
class ViewDataByStudent : Fragment() {
    var studentsRollNumbersList = ArrayList<String>()
    var mDBRoot = FirebaseDatabase.getInstance().reference
    var selectedStudent: String = ""
    var dataForStudent = ArrayList<ViewByStudentModel>()
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
        return inflater.inflate(R.layout.fragment_view_data_by_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        autoTextCompleteViewDataByStudent.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_item,
                studentsRollNumbersList
            )
        )

        setListnerForAutoTextComplete()
    }

    private fun setListnerForAutoTextComplete() {
        autoTextCompleteViewDataByStudent.setOnItemClickListener { parent, view, position, id ->
            setRecyclerViewForCurrentStudent(autoTextCompleteViewDataByStudent.text.toString())
        }
    }

    private fun setRecyclerViewForCurrentStudent(selectedStudent: String) {
        dataForStudent.clear()
        dataForStudent.add(ViewByStudentModel("subName", "P", "A", "T", "%"))

        mDBRoot.child("Students").child(selectedStudent)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    for (student in p0.children) {
                        if (student.key.toString() == "stdName") {
                            tv_studentName_viewbystudent.text = student.value.toString()
                            stdName = student.value.toString()
                        } else {
                            subName = student.key.toString()
                            present = student.child("Present").value.toString()
                            getTotalClassesForSubject(subName, present)
                        }
                    }
                }
            })
    }

    private fun getTotalClassesForSubject(subName: String, present: String) {


        mDBRoot.child("Subjects").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {


                for (subject in p0.children) {
                    if (subject.child("subName").value == subName) {
                        totalClasses = subject.child("subTotalClasses").value.toString()
                        absent = ((totalClasses.toInt() - present.toInt()).toString())
                        percentage = (present.toInt() * 100 / totalClasses.toInt()).toString()

                        dataForStudent.add(
                            ViewByStudentModel(
                                subName,
                                present,
                                absent,
                                totalClasses,
                                percentage
                            )
                        )
                    }
                }
                setRecyclerViewData(dataForStudent)
            }
        })
    }

    private fun setRecyclerViewData(dataForStudent: ArrayList<ViewByStudentModel>) {
        recyclerView_ViewByStudent.setHasFixedSize(true)
        recyclerView_ViewByStudent.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_ViewByStudent.adapter =
            ViewByStudentAdapter(
                requireContext(),
                dataForStudent
            )
    }

    override fun onStart() {
        super.onStart()
        setDataForAutoText()
    }

    private fun setDataForAutoText() {
        mDBRoot.child("Students").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                for (student in p0.children)
                    studentsRollNumbersList.add(student.key.toString())
            }
        })
    }

}
