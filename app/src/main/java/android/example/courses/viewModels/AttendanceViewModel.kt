package android.example.courses.viewModels

import android.example.courses.dataClasses.Attendance
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AttendanceViewModel : ViewModel() {
    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    fun getAttendanceLiveData(): LiveData<List<Attendance>> {
        val resultList = ArrayList<Attendance>()
        val resultLiveData = MutableLiveData<List<Attendance>>(resultList)

        db.collection("attendance")
            .document(auth.currentUser?.email!!).get().addOnSuccessListener {
                val data =  it.get("attendances") as (List<HashMap<String, String>>)

                resultList.clear()

                data.forEach {
                    resultList.add(Attendance(
                        it.get("courseName")!!,
                        it.get("totalCourses")!!,
                        it.get("unnatended")!!))
                }
                resultLiveData.value = resultList
            }

        return resultLiveData
    }
}
