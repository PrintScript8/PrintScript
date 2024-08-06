package org.example.token

enum class TokenType {
    KEYWORD, /*Definen cierto comportamiento extra como let o var*/
    OPERAND, /*Operandos clasicos*/
    IDENTIFIER_TYPE, /*Identifica el tipo de lo que se trabaja String, Int*/
    ASSIGNATION, /*Indica la asignacion de una variable a otra*/
    ENDING, /*Por ahora solo incluye ';' e indica el final de una sentencia*/
    LITERAL_TYPE, /*el valor en si de las cosas "hola", 72*/
    UNKNOWN, /*Queda por las dudas*/
    IDENTIFIER_VAR /*Es un nombre de variable que la diferencia del resto*/
}