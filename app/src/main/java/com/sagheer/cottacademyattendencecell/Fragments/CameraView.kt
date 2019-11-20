package com.sagheer.cottacademyattendencecell.Fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.frame.Frame
import com.sagheer.cottacademyattendencecell.R
import com.toast.Toast
import kotlinx.android.synthetic.main.fragment_camera_view.*


class CameraView(
    private var subjectSelected: String,
    private var teacherSelected: String,
    private var timingSelected: String
) : Fragment() {


    private lateinit var cameraView: CameraView
    private var isDetectedQR = false // this variable is to show whether QR code is detected or not
    private var dbRootRef = FirebaseDatabase.getInstance()
        .reference // this variable refers to the root of the FirebaseDatabase

    private var rollNumber = ""
    private var studentName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_view, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainLinearCameraViewFragment.setOnClickListener { }

        // first initialize cameraView Variable
        cameraView = view.findViewById(R.id.cameraViewInFragment)

        cameraView.setLifecycleOwner(this)

        // when button is clicked disables buttonScan and makes isDetected false until QR code is scanned
        btnScan.setOnClickListener {
            btnScan.isEnabled = false
            btnScan.visibility = View.GONE
            isDetectedQR = false

            //scans each frame and process it to check whether QR is scanned or not
            cameraView.addFrameProcessor {
                processImage(getFirebaseVisionImage(it))
            }
        }

    }

    // this function takes FirebaseVisionImage from given frame
    private fun getFirebaseVisionImage(frame: Frame): FirebaseVisionImage {

        val data = frame.data
        val metadata = FirebaseVisionImageMetadata.Builder()
        metadata.setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
        metadata.setHeight(frame.size.height)
        metadata.setWidth(frame.size.width)
            .build()

        return FirebaseVisionImage.fromByteArray(data, metadata.build())

    }

    // this function detects QR code from the FirebaseVisionImage
    private fun processImage(firebaseVisionImage: FirebaseVisionImage) {
        val detector = FirebaseVision.getInstance().visionBarcodeDetector
        detector.detectInImage(firebaseVisionImage).addOnSuccessListener {
            processResult(it)
        }

    }

    // this function processResult given by the detector in processImage Funciton
    @SuppressLint("RestrictedApi")
    private fun processResult(it: List<FirebaseVisionBarcode>) {
        if (it.isNotEmpty() && !isDetectedQR) {
            for (item in it) {
                when (item.valueType) {
                    FirebaseVisionBarcode.TYPE_TEXT -> {
                        if (item.rawValue.toString().contains("Roll:")) {
                            rollNumber = item.rawValue.toString().substring(5, 10)
                            studentName = item.rawValue.toString().substringAfter("Name:")
                            takeAttendence(subjectSelected, rollNumber, studentName)
                            isDetectedQR = true
                            btnScan.isEnabled = true
                            btnScan.visibility = View.VISIBLE
                        } else {
                            isDetectedQR = true
                            btnScan.isEnabled = true
                            btnScan.visibility = View.VISIBLE
                            Toast().shortToast(
                                requireContext(),
                                "This QR is not from Cott Academy!!"
                            )
                        }
                    }
                }
            }
        }
    }

    // takes students current subjects present value and adds 1 to it and put that value to database
    private fun takeAttendence(
        subjectSelected: String,
        rollNumberDetected: String,
        studentName: String
    ) {

        dbRootRef.child("Students")
            .child(rollNumberDetected)
            .child(subjectSelected)
            .child("Present").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    val currentPresentValue: Int

                    if (p0.value != null) {
                        currentPresentValue = p0.value.toString().toInt()
                        dbRootRef.child("Students").child(rollNumberDetected).child(subjectSelected)
                            .child("Present").setValue((currentPresentValue + 1))
                        dbRootRef.child("Students").child(rollNumberDetected).child(subjectSelected)
                            .child("subTeacher").setValue(teacherSelected)
                        dbRootRef.child("Students").child(rollNumberDetected).child(subjectSelected)
                            .child("subTiming").setValue(timingSelected)

                    } else {
                        dbRootRef.child("Students").child(rollNumberDetected).child("stdName")
                            .setValue(studentName)
                        //if it is null it creates New User and Set Values
                        dbRootRef.child("Students").child(rollNumberDetected).child(subjectSelected)
                            .child("Present").setValue(1)
                        dbRootRef.child("Students").child(rollNumberDetected).child(subjectSelected)
                            .child("subTeacher").setValue(teacherSelected)
                        dbRootRef.child("Students").child(rollNumberDetected).child(subjectSelected)
                            .child("subTiming").setValue(timingSelected)
                    }
                }
            })

        Toast().shortToast(context!!, "$rollNumberDetected Success!!!")
    }


}
