package interpreter

import node.TypeValue
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IdentifierType
import node.staticpkg.IfElseType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode

class InterpreterV2(var iterator: Iterator<StaticNode>) : Interpreter {

    private val valueMap: MutableMap<String, Pair<Boolean, TypeValue>> = mutableMapOf()
    private val output: MutableList<String> = mutableListOf()

    override fun execute(): List<String> {
        while (iterator.hasNext()) {
            val node = iterator.next()
            matchNode(node)
        }
        return output
    }

    private fun matchNode(node: StaticNode) {
        when (node) {
            is AssignationType, is DeclarationType, is ExpressionType,
            is IdentifierType, is ModifierType, is PrintLnType, is IfElseType -> {
                val (map, result) = node.execute(valueMap, "1.1")
                valueMap.putAll(map)
                output.addAll(result)
            }
        }
    }

    // no se si este esta bien
    fun iterator(): Iterator<String> {
        return output.iterator()
    }
}
