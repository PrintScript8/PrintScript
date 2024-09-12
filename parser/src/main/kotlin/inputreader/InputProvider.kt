package inputreader

interface InputProvider {
    fun input(name: String?): String?
}
