package android.example.courses

import android.example.courses.dataClasses.Attendance
import android.example.courses.viewModels.AttendanceViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_attendance_item.view.*

class AttendanceFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[AttendanceViewModel::class.java]
        val liveData = viewModel.getAttendanceLiveData()

        liveData.observe(this, Observer {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = AttendanceRecyclerViewAdapter(it)
        })

        val view =
            inflater.inflate(R.layout.fragment_attendance_list, container, false) as RecyclerView
        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = AttendanceRecyclerViewAdapter(emptyList())
        }

        recyclerView = view

        return view
    }

    inner class AttendanceRecyclerViewAdapter(private var data: List<Attendance>) :
        RecyclerView.Adapter<AttendanceViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_attendance_item, parent, false)
            return AttendanceViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data.count()
        }

        override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
            holder.courseName.text = data.get(position).courseName
            holder.totalLectures.text = data.get(position).totalCourses
            holder.unnatendedLectures.text = data.get(position).unnatended
        }
    }

    inner class AttendanceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseName: TextView = view.attendanceCourseNameTextView
        val totalLectures: TextView = view.lecturesTotalTextView
        val unnatendedLectures: TextView = view.unattendedLecturesTextView
    }
}
