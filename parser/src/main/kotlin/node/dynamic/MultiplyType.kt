package node.dynamic

import node.TypeValue
import operations.DynamicVisitorV1

class MultiplyType(val left: DynamicNode, val right: DynamicNode, override var result: LiteralValue?) : DynamicNode {
    override fun visit(visitor: DynamicVisitorV1) {
        visitor.acceptMultiply(this)
    }

    override fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): TypeValue {
        val left = left.execute(valueMap, version)
        val right = right.execute(valueMap, version)
        return TypeValue(left.value!! * right.value!!, left.type)
    }

    override fun toString(): String {
        return "MultiplyType(left='$left', right=$right)"
    }
}
