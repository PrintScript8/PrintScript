package node.staticpkg

import node.TypeValue
import node.dynamic.DynamicNode
import node.dynamic.ReadInputType
import operations.StaticVisitorV1

class AssignationType(val declaration: DeclarationType, val value: DynamicNode) : StaticNode {
    override fun visit(visitor: StaticVisitorV1) {
        visitor.acceptAssignation(this)
    }

    override fun execute(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): StaticResult {
        val (map, _) = declaration.execute(valueMap, version)
        val outList: MutableList<String> = mutableListOf()
        if (value is ReadInputType && version != "1.0") {
            val (_, inputText) = PrintLnType(value.argument).execute(map, version)
            outList.addAll(inputText)
        }
        val output = value.execute(map, version)
        return StaticResult(updateValueMap(map, declaration.name, output), outList)
    }

    override fun format(version: String, indentLevel: Int): String {
        val indent = "  ".repeat(indentLevel)
        val decl = (declaration.format(version, indentLevel)).dropLast(1)
        return "${indent}$decl = ${value.format(version)};"
    }

    override fun toString(): String {
        return "AssignationType(declaration='$declaration', value=$value)"
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
