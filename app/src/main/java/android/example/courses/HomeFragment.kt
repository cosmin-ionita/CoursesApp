package android.example.courses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chart = view.findViewById<BarChart>(R.id.chart)
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 4f))
        entries.add(BarEntry(1f, 4f))
        entries.add(BarEntry(2f, 3f))
        entries.add(BarEntry(3f, 2f))
        entries.add(BarEntry(4f, 1f))
        entries.add(BarEntry(5f, 2f))
        entries.add(BarEntry(6f, 4f))
        entries.add(BarEntry(7f, 4f))
        entries.add(BarEntry(8f, 2f))

        val labels = ArrayList<String>()
        labels.add("October")
        labels.add("November")
        labels.add("December")
        labels.add("January")
        labels.add("February")
        labels.add("March")
        labels.add("April")
        labels.add("May")
        labels.add("June")

        val dataSet = BarDataSet(entries, "Courses Attendance per month")

        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()

        val lineData = BarData(dataSet)

        chart.data = lineData
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.xAxis.isGranularityEnabled = true
        chart.xAxis.granularity = 1f

        chart.description.text = ""
        chart.animateY(1500)

        chart.invalidate()
    }
}
