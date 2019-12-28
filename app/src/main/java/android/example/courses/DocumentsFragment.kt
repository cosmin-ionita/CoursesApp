package android.example.courses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DocumentsFragment : Fragment() {
    private var docs: Array<String> =
        arrayOf("BAC Diploma", "Engineer Diploma", "Engineer Diploma Copy")

    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.documents_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        val adapter = ArrayAdapter(
            view.context,
            R.layout.documents_dropdown_item,
            docs
        )

        val autocompleteView = fragmentView.findViewById<AutoCompleteTextView>(R.id.filled_exposed_dropdown)
        autocompleteView.setAdapter(adapter)

        val submitButton = fragmentView.findViewById<MaterialButton>(R.id.submit_doc_button)

        submitButton.setOnClickListener {
            val selectedDoc =
                fragmentView.findViewById<AutoCompleteTextView>(R.id.filled_exposed_dropdown)
            val requestText = fragmentView.findViewById<TextInputEditText>(R.id.request_message)

            val payload = hashMapOf(
                "user" to (auth.currentUser?.email ?: "null"),
                "documentType" to selectedDoc.text.toString(),
                "requestNotes" to requestText.text.toString()
            )

            db.collection("docRequests")
                .document(getRandomString(10))
                .set(payload)
                .addOnSuccessListener {
                    Toast.makeText(
                        fragmentView.context, "Request successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener{
                    Toast.makeText(
                        fragmentView.context, "Request failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    fun getRandomString(length: Int): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
