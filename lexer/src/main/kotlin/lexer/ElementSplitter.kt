package org.example.lexer

class ElementSplitter {

    private var input: String = ""
    private val separators = setOf("=", "+", "-", "*", "/", ":", ";")
    private var result = mutableListOf<String>()
    private var sb = StringBuilder()
    private var inQuotes = false
    private var currentQuote = ' '
    private var i = 0

    fun split(text: String): List<String> {
        input = text
        while (i < input.length) {
            val c = input[i]

            when {
                isQuote(c) -> handleQuote(c)
                inQuotes -> sb.append(c)
                c.isWhitespace() -> handleWhitespace()
                c == '(' -> handleOpeningParenthesis(c)
                c == ')' -> handleClosingParenthesis(c)
                isSeparator(c.toString()) -> handleSeparator(c)
                else -> handleDefault(c)
            }
            i++
        }

        addRemainingToken()
        val rs: List<String> = result
        reset()
        return rs
    }

    private fun isQuote(c: Char): Boolean {
        return c == '"' || c == '\''
    }

    private fun handleQuote(c: Char) {
        if (inQuotes && c == currentQuote) {
            inQuotes = false
            sb.append(c)
            result.add(sb.toString())
            sb.clear()
        } else if (!inQuotes) {
            inQuotes = true
            currentQuote = c
            addTokenIfNotEmpty()
            sb.append(c)
        } else {
            sb.append(c)
        }
    }

    private fun handleWhitespace() {
        addTokenIfNotEmpty()
    }

    private fun isSeparator(c: String): Boolean {
        return separators.contains(c)
    }

    private fun handleSeparator(c: Char) {
        addTokenIfNotEmpty()
        result.add(c.toString())
    }

    private fun handleOpeningParenthesis(c: Char) {
        if (sb.isNotEmpty()) {
            result.add(sb.append(c).toString())
            sb.clear()
        } else {
            result.add(c.toString())
        }
    }

    private fun handleClosingParenthesis(c: Char) {
        addTokenIfNotEmpty()
        result.add(c.toString())
    }

    private fun handleDefault(c: Char) {
        if (isDoubleCharOperator()) {
            addTokenIfNotEmpty()
            result.add(input.substring(i, i + 2))
            i++ // Skip next character as it's part of the operator
        } else {
            sb.append(c)
        }
    }

    private fun isDoubleCharOperator(): Boolean {
        return i + 1 < input.length && separators.contains(input.substring(i, i + 2))
    }

    private fun addTokenIfNotEmpty() {
        if (sb.isNotEmpty()) {
            result.add(sb.toString())
            sb.clear()
        }
    }

    private fun addRemainingToken() {
        if (sb.isNotEmpty()) {
            result.add(sb.toString())
        }
    }

    private fun reset() {
        input = ""
        result = mutableListOf()
        sb = StringBuilder()
        inQuotes = false
        currentQuote = ' '
        i = 0
    }
}
