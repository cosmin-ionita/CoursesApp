package android.example.courses.dataClasses

data class Course(val id: Int, val name: String) {
    override fun toString(): String = name
}
