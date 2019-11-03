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
import androidx.recyclerview.widget.DividerItemDecoration

class CoursesFragment : Fragment() {
    private var activityListener: OnCourseListFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = CoursesRecyclerViewAdapter(activityListener)

                val dividerItemDecoration = DividerItemDecoration(view.context, LinearLayout.VERTICAL)
                view.addItemDecoration(dividerItemDecoration)
            }
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

    interface OnCourseListFragmentListener {
        fun onCourseListFragmentInteraction(item: Course?)
    }
}
