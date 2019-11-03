package android.example.courses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CourseDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val courseId = arguments?.get("courseId")
        /* Load the details of the course given by that ID */

        return inflater.inflate(R.layout.fragment_course_detail, container, false)
    }
}
