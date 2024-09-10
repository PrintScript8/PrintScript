package reader

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class InputStreamReader(input: InputStream, bufferSize: Int = 100) : ReaderInterface {

    private val bufferedReader = BufferedReader(InputStreamReader(input), bufferSize)
    private var currentChar: Char? = null
    private val buffer = CharArray(bufferSize)
    private var bufferPosition = 0
    private var bufferEnd = 0
    private var markPosition: Int = 0

    override fun hasNextChar(): Boolean {
        if (currentChar != null) return true
        fillBufferIfNeeded()
        return bufferPosition < bufferEnd
    }

    override fun nextChar(): Char {
        if (currentChar != null || hasNextChar()) {
            val char = currentChar ?: buffer[bufferPosition++]
            currentChar = null
            return char
        }
        throw NoSuchElementException("No more characters")
    }

    override fun peek(): Char? {
        if (currentChar == null) {
            fillBufferIfNeeded()
            if (bufferPosition < bufferEnd) {
                currentChar = buffer[bufferPosition]
            }
        }
        return currentChar
    }

    override fun mark() {
        markPosition = bufferPosition
    }

    override fun reset() {
        bufferPosition = markPosition
        currentChar = null
    }

    private fun fillBufferIfNeeded() {
        if (bufferPosition >= bufferEnd) {
            bufferEnd = bufferedReader.read(buffer)
            bufferPosition = 0
            if (bufferEnd == -1) {
                bufferEnd = 0
            }
        }
    }

    override fun readUntil(delimiter: Char): String {
        val result = StringBuilder()

        while (hasNextChar()) {
            val char = nextChar()
            result.append(char)
            if (char == delimiter) {
                break
            }
        }

        return result.toString()
    }
}
