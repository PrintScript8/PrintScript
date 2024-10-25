import formatter.Formatter
import node.PrimType
import node.dynamic.*
import node.staticpkg.*

fun main() {

    val assignation = AssignationType(
        DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.NUMBER),
            "a"
        ),
        LiteralType(LiteralValue.StringValue("2"))
    )

    val println = PrintLnType(
        SumType(
            LiteralType(LiteralValue.NumberValue(2)),
            LiteralType(LiteralValue.NumberValue(3)),
            null),
        )

    val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "spaceAroundEquals": false,
            "newlineBeforePrintln": 2,
            "newlineAfterSemicolon": true
        }
    }"""

    val formatter = Formatter(rules)

    val nodes = listOf(println, assignation, println).listIterator()

    val output = formatter.format(nodes)
    val output2 = formatter.format(listOf(println).listIterator())

    println(output)
    println("-----------------")
    println(output2)
}