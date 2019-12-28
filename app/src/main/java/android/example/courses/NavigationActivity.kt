package android.example.courses

import android.example.courses.dataClasses.Course
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mikhaellopez.circularimageview.CircularImageView
import com.squareup.picasso.Picasso

class NavigationActivity : AppCompatActivity(),
    CoursesFragment.OnCourseListFragmentListener {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_dest,
                R.id.courses_dest,
                R.id.schedule_dest,
                R.id.attendance_dest,
                R.id.grades_dest,
                R.id.documents_dest
            ),
            drawerLayout)

        setupActionBar(navController, appBarConfiguration)
        setupNavigationMenu(navController)
        setupNavHeader()
    }

    private fun setupNavHeader() {
        auth = FirebaseAuth.getInstance()

        val navView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navView.inflateHeaderView(R.layout.side_header_layout)

        val nameView = headerView.findViewById<TextView>(R.id.headerNameTextView)
        val photoView = headerView.findViewById<CircularImageView>(R.id.profileImage)

        var email = auth.currentUser?.email!!

        db.collection("users").document(email)
            .get()
            .addOnSuccessListener {document ->
                if (document != null) {
                    nameView.text = (document.data?.get("name") as String)

                    val link = (document.data?.get("pictureUrl") as String)
                    Picasso.get()
                        .load(link)
                        .fit()
                        .centerCrop()
                        .into(photoView)
                }
            }
            .addOnFailureListener{
                println(it)
            }
    }

    private fun setupActionBar(navController: NavController, appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun setupNavigationMenu(navController: NavController) {
        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val retValue = super.onCreateOptionsMenu(menu)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        if (navigationView == null) {
            menuInflater.inflate(R.menu.nav_side_menu, menu)
            return true
        }
        return retValue
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
                || super.onOptionsItemSelected(item)
    }

    override fun onCourseListFragmentInteraction(item: Course?) {
        /* A course was selected, redirecting to detail fragment */
        val bundle = bundleOf("courseName" to item?.name)
        findNavController(R.id.my_nav_host_fragment).navigate(R.id.action_courses_dest_to_courseDetailFragment, bundle)
    }
}
