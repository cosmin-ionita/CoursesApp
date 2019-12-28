package android.example.courses

import android.example.courses.dataClasses.Attendance
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_attendance_item.view.*

class AttendanceFragment : Fragment() {
    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    private var attendanceAdapter: FirestoreRecyclerAdapter<Attendance, AttendanceViewHolder>? =
        null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val query = db.collection("attendance/${auth.currentUser?.email}/data")

        attendanceAdapter = getAdapter(query)

        val view =
            inflater.inflate(R.layout.fragment_attendance_list, container, false) as RecyclerView
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = attendanceAdapter
        }
        return view
    }

    private fun getAdapter(query: CollectionReference): FirestoreRecyclerAdapter<Attendance, AttendanceViewHolder> {
        val options = FirestoreRecyclerOptions.Builder<Attendance>()
            .setQuery(query, Attendance::class.java)
            .build()

        return object : FirestoreRecyclerAdapter<Attendance, AttendanceViewHolder>(options) {
            override fun onBindViewHolder(
                holder: AttendanceViewHolder,
                position: Int,
                model: Attendance
            ) {
                holder.courseName.text = model.courseName
                holder.totalLectures.text = model.totalCourses
                holder.unnatendedLectures.text = model.unnatended
            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): AttendanceViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_attendance_item, parent, false)
                return AttendanceViewHolder(view)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        attendanceAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        attendanceAdapter?.stopListening()
    }

    inner class AttendanceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseName: TextView = view.attendanceCourseNameTextView
        val totalLectures: TextView = view.lecturesTotalTextView
        val unnatendedLectures: TextView = view.unattendedLecturesTextView
    }
}
