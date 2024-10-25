import formatter.Formatter
import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
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
        LiteralType(LiteralValue.StringValue("Hello, World!"))
    )

    val nodes = listOf(assignation, println).listIterator()

    val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": 1,
            "newlineAfterSemicolon": true
        }
    }"""

    val formatter = Formatter(rules)

    val output = formatter.format(nodes)

    println(output)
}