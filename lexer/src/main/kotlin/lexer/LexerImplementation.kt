package org.example.lexer

import org.example.token.Token
import org.example.token.TokenImpl
import java.io.File
import java.util.Scanner

class LexerImplementation : Lexer {

    private var file: File? = null

    override fun setFile(input: File) {
        file = input
    }

    override fun getTokens(): List<Token> {
        if (file == null) {
            return emptyList()
        }
        return readText(file!!)
    }

    private fun readText(file: File): List<Token> {
        val scanner = Scanner(file)
        val stringBuilder = StringBuilder()
        val tokenList = ArrayList<Token>()
        var lineNumber = 1
        val splitter = ElementSplitter()

        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine())
            val splitText = splitText(splitter, stringBuilder.toString())
            val provider = TypeProvider()

            for (string in splitText) {
                val tokenType = provider.getTokenType(string)
                tokenList.add(TokenImpl(tokenType, string, lineNumber))
                stringBuilder.setLength(0)
            }
            lineNumber += 1
        }
        scanner.close()
        return tokenList
    }

    private fun splitText(splitter: ElementSplitter, text: String): List<String> {
        return splitter.split(text)
    }
}
