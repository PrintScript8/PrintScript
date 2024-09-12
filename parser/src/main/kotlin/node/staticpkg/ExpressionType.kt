package node.staticpkg

import node.TypeValue
import node.dynamic.DynamicNode
import node.dynamic.ReadInputType
import node.dynamic.VariableType
import operations.StaticVisitorV1

class ExpressionType(val variable: VariableType, val value: DynamicNode) : StaticNode {
    override fun visit(visitor: StaticVisitorV1) {
        visitor.acceptExpression(this)
    }

    override fun execute(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): StaticResult {
        val result = value.execute(valueMap, version)
        val outList: MutableList<String> = mutableListOf()
        if (value is ReadInputType && version != "1.0") {
            val (_, inputText) = PrintLnType(value.argument).execute(valueMap, version)
            outList.addAll(inputText)
        }
        val updatedMap = updateValueMap(valueMap, variable.name, result)
        return StaticResult(updatedMap, outList)
    }

    override fun format(version: String): String {
        return "${variable.format(version)} = ${value.format(version)};"
    }

    override fun toString(): String {
        return "ExpressionType(variable='$variable', value=$value)"
    }

    private fun updateValueMap(
        originalMap: Map<String, Pair<Boolean, TypeValue>>,
        key: String,
        newValue: TypeValue
    ): Map<String, Pair<Boolean, TypeValue>> {
        require(originalMap[key]?.second?.value == null || originalMap[key]?.first == true) {
            "Cannot modify a constant!!"
        }
        val updatedMap = originalMap.toMutableMap()
        require(originalMap[key]!!.second.type == newValue.type) {
            "Type mismatch: expected ${originalMap[key]!!.second.type}, found ${newValue.type}"
        }
        updatedMap[key] = Pair(originalMap[key]?.first!!, newValue)
        return updatedMap
    }
}
