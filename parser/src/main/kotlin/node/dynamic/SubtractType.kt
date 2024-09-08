package node.dynamic

import node.TypeValue
import operations.DynamicVisitorV1

class SubtractType(val left: DynamicNode, val right: DynamicNode, override var result: LiteralValue?) : DynamicNode {
    override fun visit(visitor: DynamicVisitorV1) {
        visitor.acceptSubtract(this)
    }

    override fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): TypeValue {
        val left = left.execute(valueMap, version)
        val right = right.execute(valueMap, version)
        return TypeValue(left.value!! - right.value!!, left.type)
    }

    override fun format(version: String): String {
        return "${left.format(version)} - ${right.format(version)}"
    }

    override fun toString(): String {
        return "SubtractType(left='$left', right=$right)"
    }
}
