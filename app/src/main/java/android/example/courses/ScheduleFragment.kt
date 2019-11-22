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

class ScheduleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val container = inflater.inflate(R.layout.fragment_schedule, container, false)
        val globalLinearLayout = container.findViewById<LinearLayout>(R.id.globalScheduleFrameLayout)
        buildPage(globalLinearLayout)
        return container
    }

    private fun buildPage(container: LinearLayout) {
        val day = "Monday"
        val totalHours = "5.34 hours"

        val layout = LinearLayout(context)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 10.dpToPx, 0, 0)

        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = params

        layout.addView(getDayHeader(day, totalHours))

        layout.addView(getTableEntry("15:40", "DS Laboratory 1"))
        layout.addView(getTableEntry("16:40", "DS Laboratory 2"))
        layout.addView(getTableEntry("17:40", "DS Laboratory 3"))
        layout.addView(getTableEntry("18:40", "DS Laboratory 4"))

        layout.addView(getDayHeader("Tuesday", totalHours))

        layout.addView(getTableEntry("5:40", "DS Laboratory 1"))
        layout.addView(getTableEntry("6:40", "DS Laboratory 2"))
        layout.addView(getTableEntry("7:40", "DS Laboratory 3"))
        layout.addView(getTableEntry("8:40", "DS Laboratory 4"))

        container.addView(layout)
    }

    private fun getTableEntry(startHour : String, activityName: String) : LinearLayout {
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

    private fun getStartTimeTextView(startTime: String, context: Context) : TextView {
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

    private fun getActivityNameTextView(activityName: String, context: Context) : TextView {
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

    private fun getDayHeader(day: String, totalHours: String) : LinearLayout {
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
        innerLayout.addView(context?.let { getTotalHoursTextView(totalHours, it) })

        return innerLayout
    }

    private fun getTotalHoursTextView(totalHours: String, context: Context): TextView {
        val view = TextView(context)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        view.gravity = Gravity.END
        params.weight = 5f

        view.layoutParams = params
        view.text = totalHours

        return view
    }

    private fun getDayTextView(day: String, context: Context) : TextView {
        val view = TextView(context)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.weight = 5f
        view.gravity = Gravity.START

        view.layoutParams = params
        view.text = day

        return view
    }

    private val Int.dpToPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}
