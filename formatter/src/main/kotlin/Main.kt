import formatter.Formatter
import node.PrimType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType

fun main() {

    val assignation = AssignationType(
        DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.NUMBER),
            "a"
        ),
        LiteralType(LiteralValue.StringValue("2"))
    )

    val rules = """{
        "rules": {
            "spaceBeforeColon": true,
            "spaceAfterColon": true,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": 1,
            "newlineAfterSemicolon": true,
            "maxSpacesBetweenTokens": 2,
            "spaceAroundOperators": true
        }
    }"""

    val formatter = Formatter(rules)

    val output = formatter.format(assignation)

    println(output)
}