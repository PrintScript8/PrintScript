package error

class Error(private val type: Type, private val message: String) {
    override fun toString(): String {
        return "Error(type=$type, message='$message')"
    }
}
