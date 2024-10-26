import node.PrimType
import node.dynamic.*
import node.staticpkg.*
import provider.FormatterProvider

fun main() {

    val assignation = AssignationType(
        DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.NUMBER),
            "a"
        ),
        LiteralType(LiteralValue.NumberValue(2))
    )

    val println = PrintLnType(
        SumType(
            LiteralType(LiteralValue.NumberValue(2)),
            LiteralType(LiteralValue.NumberValue(3)),
            null),
        )

    val readInputAssignment = AssignationType(
        DeclarationType(
            ModifierType("let", true),
            IdentifierType(PrimType.STRING),
            "a"
        ),
        ReadInputType(
            LiteralType(LiteralValue.StringValue("Enter a string: ")),
                null)
    )

    val readEnvAssignation = AssignationType(
        DeclarationType(
            ModifierType("const", true),
            IdentifierType(PrimType.BOOLEAN),
            "a"
        ),
        ReadEnvType(
            LiteralType(LiteralValue.StringValue("HOME")),
            null)
    )

    val ifStructure = IfElseType(
        listOf(
            ExpressionType(
                VariableType("a", null),
                LiteralType(LiteralValue.NumberValue(2)),
            )
        ),
        LiteralType(LiteralValue.BooleanValue(false)),
        null
    )

    val ifElseStructure = IfElseType(
        listOf(
            ExpressionType(
                VariableType("a", null),
                LiteralType(LiteralValue.NumberValue(2)),
            )
        ),
        LiteralType(LiteralValue.BooleanValue(true)),
        listOf(ifStructure)
    )

    val rules = """{
        "rules": {
            "spaceBeforeColon": false,
            "spaceAfterColon": true,
            "spaceAroundEquals": true,
            "newlineBeforePrintln": 2,
            "newlineAfterSemicolon": true
        }
    }"""

    val formatter = FormatterProvider().provideFormatter(rules, "1.1")

    val nodes = listOf(println, assignation, println).listIterator()

    val output1 = formatter.format(nodes)
    val output2 = formatter.format(listOf(println).listIterator())
    val output3 = formatter.format(listOf(readInputAssignment, assignation).listIterator())
    val output4 = formatter.format(listOf(readEnvAssignation, readInputAssignment).listIterator())
    val output5 = formatter.format(listOf(ifStructure).listIterator())
    val output6 = formatter.format(listOf(ifElseStructure).listIterator())

    println(output1)
    println("-----------------")
    println(output2)
    println("-----------------")
    println(output3)
    println("-----------------")
    println(output4)
    println("-----------------")
    println(output5)
    println("-----------------")
    println(output6)
}