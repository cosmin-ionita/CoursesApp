package android.example.courses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CourseDetailFragment : Fragment() {
    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    private var selectedCourse = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectedCourse = arguments?.get("courseName") as String
        return inflater.inflate(R.layout.fragment_course_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val loadingScreen = view.findViewById<FrameLayout>(R.id.progress_overlay)
//        loadingScreen.visibility = View.VISIBLE

        val courseNameView = view.findViewById<TextView>(R.id.courseDetailName)
        val frequencyView = view.findViewById<TextView>(R.id.courseDetailFrequency)
        val roomView = view.findViewById<TextView>(R.id.courseDetailRoom)
        val structureView = view.findViewById<TextView>(R.id.courseDetailStructure)
        val teacherNameView = view.findViewById<TextView>(R.id.courseDetailTeacherName)
        val teacherPhoneView = view.findViewById<TextView>(R.id.courseDetailTeacherPhone)
        val teacherWebsiteView = view.findViewById<TextView>(R.id.courseDetailTeacherWebsite)
        val requirementView1 = view.findViewById<TextView>(R.id.courseDetailRequirement1)
        val requirementView2 = view.findViewById<TextView>(R.id.courseDetailRequirement2)
        val requirementView3 = view.findViewById<TextView>(R.id.courseDetailRequirement3)

        db.collection("courses/${auth.currentUser?.email}/details")
            .document(selectedCourse)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    courseNameView.text = document.data?.get("name").toString()
                    frequencyView.text = document.data?.get("frequency").toString()
                    roomView.text = document.data?.get("room").toString()
                    teacherNameView.text = document.data?.get("teacherName").toString()
                    teacherPhoneView.text = document.data?.get("teacherPhone").toString()
                    teacherWebsiteView.text = document.data?.get("teacherWebsite").toString()
                    structureView.text = document.data?.get("structure").toString()
                    requirementView1.text = document.data?.get("requirement1").toString()
                    requirementView2.text = document.data?.get("requirement2").toString()
                    requirementView3.text = document.data?.get("requirement3").toString()
                }
            }
            .addOnCompleteListener {
//                loadingScreen.visibility = View.INVISIBLE
            }
    }
}
