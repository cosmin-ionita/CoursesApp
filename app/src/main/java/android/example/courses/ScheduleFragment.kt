package android.example.courses

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ScheduleFragment : Fragment() {
    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    private var DAYS = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val container = inflater.inflate(R.layout.fragment_schedule, container, false)
        val globalLinearLayout =
            container.findViewById<LinearLayout>(R.id.globalScheduleFrameLayout)
        buildSchedule(globalLinearLayout)
        return container
    }

    private fun buildSchedule(container: LinearLayout) {
        val layout = LinearLayout(context)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(0, 10.dpToPx, 0, 0)

        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = params

        var email = auth.currentUser?.email!!

        db.collection("schedule").document(email)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    DAYS.forEach {
                        val data = document.data?.get(it)

                        if (data != null) {
                            updateLayout(layout, data as ArrayList<String>, it)
                        }
                    }
                }
            }
            .addOnFailureListener {
                println(it)
            }
        container.addView(layout)
    }

    private fun updateLayout(
        layout: LinearLayout,
        daySchedule: ArrayList<String>,
        dayName: String
    ) {
        layout.addView(getDayHeader(dayName))

        daySchedule.forEach {
            layout.addView(getTableEntry(it.split(',')[1], it.split(',')[0]))
        }
    }

    private fun getTableEntry(startHour: String, activityName: String): LinearLayout {
        val context = activity?.applicationContext

        val layout = LinearLayout(context)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(10.dpToPx, 10.dpToPx, 0, 0)

        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = params

        layout.setBackgroundColor(Color.parseColor("#8B08BE45"))
        layout.setPadding(0, 10.dpToPx, 0, 10.dpToPx)

        layout.addView(context?.let { getStartTimeTextView(startHour, it) })
        layout.addView(context?.let { getActivityNameTextView(activityName, it) })

        return layout
    }

    private fun getStartTimeTextView(startTime: String, context: Context): TextView {
        val view = TextView(context)

        view.setBackgroundColor(Color.parseColor("#EBAEB8FE"))

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(10.dpToPx, 0, 0, 0)

        view.layoutParams = params
        view.text = startTime

        return view
    }

    private fun getActivityNameTextView(activityName: String, context: Context): TextView {
        val view = TextView(context)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        params.setMargins(10.dpToPx, 5.dpToPx, 0, 0)

        view.layoutParams = params
        view.text = activityName

        return view
    }

    private fun getDayHeader(day: String): LinearLayout {
        val context = activity?.applicationContext

        val innerLayout = LinearLayout(context)

        val innerParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        innerParams.setMargins(10.dpToPx, day.length + 20, 10.dpToPx, 0)
        innerParams.layoutDirection = LinearLayout.HORIZONTAL
        innerLayout.setPadding(20.dpToPx, 0, 20.dpToPx, 0)

        innerLayout.setBackgroundColor(Color.parseColor("#EBAEB8AE"))
        innerLayout.weightSum = 10f
        innerLayout.layoutParams = innerParams

        innerLayout.addView(context?.let { getDayTextView(day, it) })

        return innerLayout
    }

    private fun getDayTextView(day: String, context: Context): TextView {
        val view = TextView(context)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.weight = 5f
        view.gravity = Gravity.START

        view.layoutParams = params
        view.text = day
        view.setTextColor(Color.parseColor("#ffffff"))

        return view
    }

    private val Int.dpToPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}
