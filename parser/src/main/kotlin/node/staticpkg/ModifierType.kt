package node.staticpkg

import node.TypeValue
import operations.StaticVisitorV1

class ModifierType(val value: String, val canModify: Boolean) : StaticNode {
    override fun visit(visitor: StaticVisitorV1) {
        visitor.acceptModifier(this)
    }

    override fun execute(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): StaticResult {
        return StaticResult(valueMap, emptyList())
    }

    override fun format(version: String, indentLevel: Int): String {
        val indent = "  ".repeat(indentLevel)
        return "$indent$value"
    }

    override fun toString(): String {
        return "ModifierType(value='$value')"
    }
}
