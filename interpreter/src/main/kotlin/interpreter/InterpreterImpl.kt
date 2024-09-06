package interpreter

import node.TypeValue
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode

class InterpreterImpl : Interpreter {

    private val valueMap: MutableMap<String, Pair<Boolean, TypeValue>> = mutableMapOf()
    private val output: MutableList<String> = mutableListOf()

    override fun execute(list: List<StaticNode>): List<String> {
        for (node in list) {
            matchNode(node)
        }
        return output
    }

    private fun matchNode(node: StaticNode) {
        when (node) {
            is AssignationType, is DeclarationType, is ExpressionType,
            is IdentifierType, is ModifierType, is PrintLnType -> {
                val (map, result) = node.run(valueMap, "1.0")
                valueMap.putAll(map)
                output.addAll(result)
            }
        }
    }
}
