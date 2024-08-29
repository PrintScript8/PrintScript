package interpreter

import node.dynamic.LiteralValue
import node.staticpkg.StaticNode
import visitor.StaticInterpreterVisitor

class InterpreterImpl : Interpreter {

    private val valueMap: MutableMap<String, Pair<Boolean, LiteralValue?>> = mutableMapOf()
    private val visitor = StaticInterpreterVisitor(this)
    private val output: MutableList<String> = mutableListOf()

    override fun execute(list: List<StaticNode>): List<String> {
        for (node in list) {
            node.visit(visitor)
        }
        return output
    }

    fun addValue(key: String, value: Pair<Boolean, LiteralValue?>) {
        valueMap[key] = value
    }

    fun checkValue(key: String): Boolean {
        return valueMap.containsKey(key)
    }

    fun getValue(key: String): Pair<Boolean, LiteralValue?> {
        return valueMap[key] ?: Pair(false, null)
    }

    fun addToList(value: String) {
        output.add(value)
    }
}
