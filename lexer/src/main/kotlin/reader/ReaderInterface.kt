package reader

interface ReaderInterface {
    fun hasNextChar(): Boolean // If there is a next character returns true otherwise false
    fun nextChar(): Char // Returns and consumes the next character or error if there is none
    fun peek(): Char? // Returns the next character without consuming it and null if there is none
    fun mark() // Save the current state of the reader
    fun reset() // Restore the reader to the last saved state
    fun readUntil(delimiter: Char): String // Read characters until the delimiter is found
}
