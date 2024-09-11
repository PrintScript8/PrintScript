package test

import interpreter.InterpreterProvider
import node.PrimType
import node.dynamic.LiteralValue
import node.dynamic.ReadInputType
import node.dynamic.VariableType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType

fun main() {

    val declaration = DeclarationType(
        ModifierType("let", true),
        IdentifierType(PrimType.NUMBER),
        "input"
    )

    val readInputType = ReadInputType("Enter a number", null)

    val expression = ExpressionType(
        VariableType("input", LiteralValue.NumberValue(3), true),
        readInputType
    )

    val printLn = PrintLnType(VariableType("input", LiteralValue.StringValue(""), true))

    val astList = listOf(declaration, expression, printLn)
    val version = "1.1"

    val interpreter = InterpreterProvider(astList.iterator()).provideInterpreter(version)
    val output: Iterator<String> = interpreter.execute()
    val expected = "10"

    print(expected == output.next())
}
