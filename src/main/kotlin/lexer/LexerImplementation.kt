package org.example.lexer

import org.example.token.Token
import org.example.token.TokenImpl
import org.example.token.TokenType
import java.io.File
import java.util.Scanner

class LexerImplementation : Lexer {

    private var file: File? = null;

    override fun setFile(input: File) {
        file = input;
    }

    override fun getTokens(): List<Token> {

        if (file == null) {
            return emptyList()
        }
        return readText(file!!)
    }

    private fun readText(file: File) : List<Token> {

        val scanner = Scanner(file)
        val stringBuilder: StringBuilder = StringBuilder()

        val tokenList = ArrayList<Token>()

        var i = 1;
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine())

            val splitText: List<String> = splitText(stringBuilder);

            val provider = TypeProvider()
            for (string in splitText) {

                val tokenType: TokenType = provider.getTokenType(string)
                tokenList.add(TokenImpl(tokenType, string, i))
                stringBuilder.setLength(0)
            }
            i += 1
        }
        scanner.close()
        return tokenList
    }

    private fun splitText(stringBuilder: StringBuilder) : List<String> {
        return stringBuilder.toString().split(" ")
    }
}