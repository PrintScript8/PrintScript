package interpreter

import node.staticpkg.StaticNode
import visitor.InterpreterVisitor

class InterpreterImpl : Intepreter {

    private val valueMap: MutableMap<String, Pair<Boolean, ResultType?>> = mutableMapOf()
    private val visitor = InterpreterVisitor(this)

    override fun excecute(list: List<StaticNode>) {
        for (node in list) {
            node.visit(visitor)
        }
    }

    fun addValue(key: String, value: Pair<Boolean, ResultType?>) {
        valueMap[key] = value
    }

    fun checkValue(key: String): Boolean {
        return valueMap.containsKey(key)
    }

}