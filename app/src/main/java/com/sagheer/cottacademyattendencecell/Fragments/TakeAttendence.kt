package com.sagheer.cottacademyattendencecell.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sagheer.cottacademyattendencecell.MainActivity
import com.sagheer.cottacademyattendencecell.R
import kotlinx.android.synthetic.main.fragment_take_attendence.*

class TakeAttendence : Fragment() {

    // lists to store spinners data taken from database
    private var listSubject = ArrayList<String>()
    private var listTeacher = ArrayList<String>()
    private var listTiming = ArrayList<String>()
    private var listTeacherID = ArrayList<String>()
    private var dbRootRef = FirebaseDatabase.getInstance().reference
    private var subjectSelected: String = ""
    private var teacherSelected: String = ""
    private var timingSelected: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_take_attendence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_TakeAttendence.setOnClickListener {
            (activity as MainActivity).openCameraViewFragment(
                CameraView(
                    subjectSelected,
                    teacherSelected,
                    timingSelected
                )
            )
        }
    }


    override fun onStart() {
        super.onStart()
        AfterLoggedInProgressbar.visibility = View.VISIBLE
        AfterLoggedInView.visibility = View.VISIBLE
        setDataForTeacherSpinner()
        spinnerSubject.isEnabled = false
        spinnerTiming.isEnabled = false
        btn_TakeAttendence.isEnabled = false
    }

    private fun setDataForTeacherSpinner() {
        dbRootRef.child("Teachers").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                spinnerTeacherListner()

                listSubject.clear()
                listTiming.clear()
                listSubject.add(" Select Subject")
                listTiming.add(" Select Timing")
                spinnerSubject.adapter =
                    ArrayAdapter<String>(context!!, R.layout.spinner_item, listSubject.sorted())
                spinnerTiming.adapter =
                    ArrayAdapter<String>(context!!, R.layout.spinner_item, listTiming.sorted())

                listTeacher.clear()
                listTeacher.add(" Select Teacher")
                listTeacherID.add("0 index")

                for (teacher in p0.children) {
                    listTeacher.add("" + teacher.value)
                    listTeacherID.add(teacher.key.toString())
                }

                spinnerTeacher.adapter =
                    ArrayAdapter<String>(context!!, R.layout.spinner_item, listTeacher.sorted())


                AfterLoggedInProgressbar.visibility = View.GONE
                AfterLoggedInView.visibility = View.GONE
            }
        })
    }


    fun spinnerTeacherListner() {
        spinnerTeacher.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (listTeacher.sorted()[position] == (" Select Teacher")) {
                    teacherSelected = listTeacher.sorted()[position]
                    spinnerSubject.isEnabled = false
                    btn_TakeAttendence.isEnabled = false
                } else {
                    teacherSelected = listTeacher.sorted()[position]
                    setDataForSubjectSpinner(teacherSelected)
                    spinnerSubject.isEnabled = true
                    btn_TakeAttendence.isEnabled = true
                }
            }
        }
    }

    private fun setDataForSubjectSpinner(teacherSelected: String) {
        dbRootRef.child("Subjects").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                listSubject.clear()
                listSubject.add(" Select Subject")
                for (subject in p0.children) {
                    if (subject.child("subTeacher").value == listTeacherID[listTeacher.indexOf(
                            teacherSelected
                        )]
                    )
                        listSubject.add(subject.child("subName").value.toString())

                }

                spinnerSubject.adapter =
                    ArrayAdapter<String>(context!!, R.layout.spinner_item, listSubject.sorted())
                spinnerSubjectListner()
            }
        })
    }

    fun spinnerSubjectListner() {
        spinnerSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (listSubject.sorted()[position] == (" Select Subject")) {
                    subjectSelected = listSubject.sorted()[position]
                    btn_TakeAttendence.isEnabled = false
                    spinnerTiming.isEnabled = false
                } else {
                    subjectSelected = listSubject.sorted()[position]
                    spinnerTiming.isEnabled = true
                    btn_TakeAttendence.isEnabled = true
                    setDataForTimingSpinner()
                }
            }
        }
    }

    private fun setDataForTimingSpinner() {
        dbRootRef.child("Timings").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                listTiming.clear()
                listTiming.add(" Select Timing")
                for (time in p0.children) {
                    listTiming.add(time.value.toString())
                }

                spinnerTiming.adapter =
                    ArrayAdapter<String>(context!!, R.layout.spinner_item, listTiming.sorted())
                spinnerTimingListner()
            }
        })
    }

    private fun spinnerTimingListner() {
        spinnerTiming.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (listTiming.sorted()[position] == (" Select Timing")) {
                    timingSelected = listTiming.sorted()[position]
                    btn_TakeAttendence.isEnabled = false
                } else {
                    timingSelected = listTiming.sorted()[position]
                    btn_TakeAttendence.isEnabled = true

                }
            }
        }
    }

}