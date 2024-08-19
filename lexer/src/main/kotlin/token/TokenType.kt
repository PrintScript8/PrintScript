package org.example.token

enum class TokenType {
    MODIFIER, /*Definen cierto comportamiento extra como let o var*/
    OPERAND, /*Operandos clasicos*/
    IDENTIFIER_TYPE, /*Identifica el tipo de lo que se trabaja String, Int*/
    ASSIGNATION, /*Indica la asignacion de una variable a otra*/
    ENDING, /*Por ahora solo incluye ';' e indica el final de una sentencia*/
    COLON, /*Incluye solamente el :*/
    STRING_LITERAL, NUMBER_LITERAL, /*el valor en si de las cosas "hola", 72*/
    UNKNOWN, /*Queda por las dudas*/
    NATIVE_METHOD, /*para los metodos nativos del lenguaje como*/
    PARENTHESIS,
    USER_METHOD,
    IDENTIFIER_VAR /*Es un nombre de variable que la diferencia del resto*/
}

/**
 *     MODIFIER, compisite
 *     OPERAND, composite
 *     IDENTIFIER_TYPE, leaf
 *     ASSIGNATION, composite
 *     ENDING, no tiene
 *     STRING_LITERAL, NUMBER_LITERAL, leaf
 *     UNKNOWN, no tiene
 *     NATIVE_METHOD, leaf --> el nombre FunctionDeclaration si es composite
 *     PARENTHESIS, no tiene
 *     USER_METHOD, lo mismo que NATIVE_METHOD
 *     IDENTIFIER_VAR leaf
 */