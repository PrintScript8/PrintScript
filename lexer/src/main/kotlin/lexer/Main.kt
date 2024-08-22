package lexer

import rule.*
import token.Whitespace

// Función main para probar el lexer
fun main() {
    val rules = listOf(
        WhitespaceRule(),
        IdentifierRule(),
        NumberLiteralRule(),
        StringLiteralRule(),
        DeclarationRule(),
        AssignationRule(),
        EndingRule(),
        OperationRule(listOf(PlusOperation, MinusOperation, MultiplyOperation, DivideOperation))
    )
    val lexer = Lexer(rules)
    val inputs = listOf("let a:\n" +
            "hola")

    for (input in inputs) {
        //println("Tokenizing input: \"$input\"")
        try {
            val tokens = lexer.tokenize(input)
            val filteredTokens = tokens.filter { it.type != Whitespace }
            for (token in filteredTokens) {
                println(token)
            }
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }
}

