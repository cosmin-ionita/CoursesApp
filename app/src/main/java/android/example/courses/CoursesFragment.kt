package android.example.courses

import android.content.Context
import android.example.courses.dataClasses.Course
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_courses_item.view.*
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference

class CoursesFragment : Fragment() {
    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    private var coursesAdapter: FirestoreRecyclerAdapter<Course, CourseViewHolder>? = null
    private var activityListener: OnCourseListFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val query = db.collection("courses/${auth.currentUser?.email}/details")

        coursesAdapter = getAdapter(query)

        val view =
            inflater.inflate(R.layout.fragment_courses_list, container, false) as RecyclerView

        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = coursesAdapter
            val dividerItemDecoration =
                DividerItemDecoration(view.context, LinearLayout.VERTICAL)
            view.addItemDecoration(dividerItemDecoration)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCourseListFragmentListener) {
            activityListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCourseListFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        activityListener = null
    }

    override fun onStart() {
        super.onStart()
        coursesAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        coursesAdapter?.stopListening()
    }

    private fun getAdapter(query: CollectionReference): FirestoreRecyclerAdapter<Course, CourseViewHolder> {
        val options = FirestoreRecyclerOptions.Builder<Course>()
            .setQuery(query, Course::class.java)
            .build()

        return object : FirestoreRecyclerAdapter<Course, CourseViewHolder>(options) {
            override fun onBindViewHolder(holder: CourseViewHolder, position: Int, model: Course) {
                holder.courseTextView.text = model.name

                with(holder.courseCard) {
                    tag = model
                    setOnClickListener {
                        val item = it.tag as Course
                        activityListener?.onCourseListFragmentInteraction(item)
                    }
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_courses_item, parent, false)
                return CourseViewHolder(view)
            }
        }
    }

    interface OnCourseListFragmentListener {
        fun onCourseListFragmentInteraction(item: Course?)
    }

    inner class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseCard: MaterialCardView = view.courseItemCard
        val courseTextView: TextView = view.coursePageTextView
    }
}
