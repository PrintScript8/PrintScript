package interpreter

import node.dynamic.LiteralValue
import node.staticpkg.StaticNode
import visitor.StaticInterpreterVisitor

class InterpreterImpl(var iterator: Iterator<StaticNode>) : Interpreter {

    private val valueMap: MutableMap<String, Pair<Boolean, LiteralValue?>> = mutableMapOf()
    private val visitor = StaticInterpreterVisitor(this)
    private val output: MutableList<String> = mutableListOf()

    override fun execute(): List<String> {
        while (iterator.hasNext()) {
            val node = iterator.next()
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

    // no se si este esta bien
    fun iterator(): Iterator<String> {
        return output.iterator()
    }

}
