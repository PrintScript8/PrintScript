package node.staticpkg

import node.TypeValue
import node.dynamic.DynamicNode
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
        val output = value.execute(map, version)
        return StaticResult(updateValueMap(map, declaration.name, output), emptyList())
    }

    override fun format(version: String): String {
        val decl = (declaration.format(version)).dropLast(1)
        return "$decl = ${value.format(version)};"
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
