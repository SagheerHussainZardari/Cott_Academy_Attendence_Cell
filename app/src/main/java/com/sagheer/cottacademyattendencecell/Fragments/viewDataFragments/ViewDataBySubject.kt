package com.sagheer.cottacademyattendencecell.Fragments.viewDataFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    var dataForSubject = ArrayList<ViewBySubjectModel>()
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
        dataForSubject.add(ViewBySubjectModel("stdName", "P", "A", "T", "%"))

        setListnerForAutoTextComplete()
    }

    private fun setListnerForAutoTextComplete() {
        autoTextCompleteViewDataBySubject.setOnItemClickListener { parent, view, position, id ->
            setRecyclerViewForCurrentSubject(autoTextCompleteViewDataBySubject.text.toString())
        }
    }

    private fun setRecyclerViewForCurrentSubject(subject: String) {
        Toast().longToast(requireContext(), subject)
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
