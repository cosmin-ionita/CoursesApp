package android.example.courses.viewModels

import android.example.courses.dataClasses.Grade
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GradesViewModel : ViewModel() {
    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    fun getGradesLiveData(): LiveData<List<Grade>> {
        val resultList = ArrayList<Grade>()
        val resultLiveData = MutableLiveData<List<Grade>>(resultList)

        db.collection("grades")
            .document(auth.currentUser?.email!!).get().addOnSuccessListener {
                val data =  it.get("grades") as (List<HashMap<String, String>>)

                resultList.clear()

                data.forEach {
                    resultList.add(Grade(
                        it.get("courseName")!!,
                        it.get("grade")!!))
                }
                resultLiveData.value = resultList
            }

        return resultLiveData
    }
}
