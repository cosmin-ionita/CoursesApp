package android.example.courses

import android.example.courses.dataClasses.Grade
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
import kotlinx.android.synthetic.main.fragment_grades_item.view.*

class GradesFragment : Fragment() {
    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    private var gradesAdapter: FirestoreRecyclerAdapter<Grade, GradesViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val query = db.collection("grades/${auth.currentUser?.email}/data")
        gradesAdapter = getAdapter(query)

        val view = inflater.inflate(R.layout.fragment_grades_list, container, false)
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = gradesAdapter
            }
        }
        return view
    }

    private fun getAdapter(query: CollectionReference): FirestoreRecyclerAdapter<Grade, GradesViewHolder> {
        val options = FirestoreRecyclerOptions.Builder<Grade>()
            .setQuery(query, Grade::class.java)
            .build()

        return object : FirestoreRecyclerAdapter<Grade, GradesViewHolder>(options) {
            override fun onBindViewHolder(holder: GradesViewHolder, position: Int, model: Grade) {
                holder.courseName.text = model.courseName
                holder.grade.text = model.grade
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradesViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_grades_item, parent, false)
                return GradesViewHolder(view)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        gradesAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        gradesAdapter?.stopListening()
    }

    inner class GradesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseName: TextView = view.gradesCourseNameTextView
        val grade: TextView = view.gradeTextView
    }
}
