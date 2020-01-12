package android.example.courses

import android.example.courses.dataClasses.Grade
import android.example.courses.viewModels.GradesViewModel
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
import kotlinx.android.synthetic.main.fragment_grades_item.view.*

class GradesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[GradesViewModel::class.java]
        val liveData = viewModel.getGradesLiveData()

        liveData.observe(this, Observer {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = GradesRecyclerViewAdapter(it)
        })

        val view = inflater.inflate(R.layout.fragment_grades_list, container, false) as RecyclerView

        with(view) {
            layoutManager = LinearLayoutManager(context)
            adapter = GradesRecyclerViewAdapter(emptyList())
        }

        recyclerView = view

        return view
    }

    inner class GradesRecyclerViewAdapter(private var data: List<Grade>) :
        RecyclerView.Adapter<GradesViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradesViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_grades_item, parent, false)
            return GradesViewHolder(view)
        }

        override fun getItemCount(): Int {
            return data.count()
        }

        override fun onBindViewHolder(holder: GradesViewHolder, position: Int) {
            holder.courseName.text = data.get(position).courseName
            holder.grade.text = data.get(position).grade
        }
    }

    inner class GradesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseName: TextView = view.gradesCourseNameTextView
        val grade: TextView = view.gradeTextView
    }
}
