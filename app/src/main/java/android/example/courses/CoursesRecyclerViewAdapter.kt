package android.example.courses

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.example.courses.CoursesFragment.OnCourseListFragmentListener
import android.example.courses.dataClasses.Course
import android.widget.Button

import kotlinx.android.synthetic.main.fragment_item.view.*

class CoursesRecyclerViewAdapter(private val mListenerCourse: OnCourseListFragmentListener?)
    : RecyclerView.Adapter<CoursesRecyclerViewAdapter.ViewHolder>() {

    private val courses: MutableList<Course>
    private val mOnClickListener: View.OnClickListener

    init {
        courses = ArrayList()
        courses.add(Course(1, "System Design"))
        courses.add(Course(2, "Operating Systems Security"))
        courses.add(Course(3, "Data Structures"))
        courses.add(Course(4, "Algorithm Design"))

        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Course
            mListenerCourse?.onCourseListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = courses[position]
        holder.courseButton.text = item.name

        with(holder.courseButton) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = courses.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseButton: Button = view.list_item_button
    }
}
