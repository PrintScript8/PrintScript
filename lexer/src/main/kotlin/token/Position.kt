package token

// Define la clase Position
data class Position(val row: Int, val startColumn: Int, val endColumn: Int) {

    override fun toString(): String {
        return "row=$row, startColumn=$startColumn, endColumn=$endColumn"
    }

    companion object {
        fun initial(): Position {
            return Position(row = 1, startColumn = 1, endColumn = 1)
        }
    }
}
