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
import com.sagheer.cottacademyattendencecell.Adapters.ViewBySubjectAdapter
import com.sagheer.cottacademyattendencecell.DataModels.ViewBySubjectModel
import com.sagheer.cottacademyattendencecell.R
import com.toast.Toast
import kotlinx.android.synthetic.main.fragment_view_data_by_subject.*

/**
 * A simple [Fragment] subclass.
 */
class ViewDataBySubject : Fragment() {
    var mDBRoot = FirebaseDatabase.getInstance().reference
    var subjectsList = ArrayList<String>()
    var dataForSubjects = ArrayList<ViewBySubjectModel>()
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
        return inflater.inflate(R.layout.fragment_view_data_by_subject, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        autoTextCompleteViewDataBySubject.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_item,
                subjectsList
            )
        )
        dataForSubjects.add(ViewBySubjectModel("stdName", "P", "A", "T", "%"))

        setListnerForAutoTextComplete()
    }

    private fun setListnerForAutoTextComplete() {
        autoTextCompleteViewDataBySubject.setOnItemClickListener { parent, view, position, id ->
            tv_subjectName_viewbysubject.text = autoTextCompleteViewDataBySubject.text.toString()
            setRecyclerViewForCurrentSubject(autoTextCompleteViewDataBySubject.text.toString())
        }
    }

    private fun setRecyclerViewForCurrentSubject(subject: String) {

        mDBRoot.child("Students").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                for (student in p0.children) {
                    for (sub in student.children) {
                        if (sub.key == subject) {
                            stdName = student.child("stdName").value.toString()
                            present = sub.child("Present").value.toString()
                            Log.d("a", "data: $subject = $stdName,$present\n")
                            getOtherValuesForThatStudent(subject, stdName, present)
                        }
                    }
                }

            }
        })

    }

    private fun getOtherValuesForThatStudent(subName: String, stdName: String, present: String) {
        dataForSubjects.clear()
        dataForSubjects.add(ViewBySubjectModel("StdName", "P", "A", "T", "%"))

        mDBRoot.child("Subjects").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {


                for (subject in p0.children) {
                    if (subject.child("subName").value == subName) {
                        totalClasses = subject.child("subTotalClasses").value.toString()
                        absent = ((totalClasses.toInt() - present.toInt()).toString())
                        percentage = (present.toInt() * 100 / totalClasses.toInt()).toString()

                        Toast().shortToast(requireContext(), "in if")
                        dataForSubjects.add(
                            ViewBySubjectModel(
                                stdName,
                                present,
                                absent,
                                totalClasses,
                                percentage
                            )
                        )
                    }
                }
                Log.d("a", "data: ${dataForSubjects}\n")
                setRecyclerViewDataBySubjects(dataForSubjects)
            }
        })

    }

    private fun setRecyclerViewDataBySubjects(dataForSubjects: ArrayList<ViewBySubjectModel>) {
        recyclerView_ViewBySubject.setHasFixedSize(true)
        recyclerView_ViewBySubject.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_ViewBySubject.adapter =
            ViewBySubjectAdapter(
                requireContext(),
                dataForSubjects
            )
    }


    override fun onStart() {
        super.onStart()
        setDataForAutoText()
    }

    private fun setDataForAutoText() {
        mDBRoot.child("Subjects").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                for (subject in p0.children)
                    subjectsList.add(subject.child("subName").value.toString())
            }
        })
    }


}
