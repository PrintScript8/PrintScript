package interpreter

import node.TypeValue
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import node.staticpkg.StaticNode

class InterpreterV1(private var nodeIterator: Iterator<StaticNode>) : Interpreter {

    private val valueMap: MutableMap<String, Pair<Boolean, TypeValue>> = mutableMapOf()

    override fun iterator(): Iterator<String> {
        return processNodes(nodeIterator).iterator()
    }

    private fun processNodes(iterator: Iterator<StaticNode>): Sequence<String> {
        return sequence {
            while (iterator.hasNext()) {
                val node = iterator.next()
                val (map, result) = matchNode(node)
                valueMap.putAll(map)
                yieldAll(result)
            }
        }
    }

    private fun matchNode(node: StaticNode): Pair<Map<String, Pair<Boolean, TypeValue>>, List<String>> {
        when (node) {
            is AssignationType, is DeclarationType, is ExpressionType,
            is IdentifierType, is ModifierType, is PrintLnType -> {
                val (map, result) = node.execute(valueMap, "1.0")
                return Pair(map, result)
            }
            else -> throw IllegalArgumentException("Operation not supported")
        }
    }
}
