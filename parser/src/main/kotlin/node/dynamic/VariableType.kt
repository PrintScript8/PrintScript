package node.dynamic

import node.TypeValue
import operations.DynamicVisitorV1

class VariableType(val name: String, override var result: LiteralValue?, var canModify: Boolean) : DynamicNode {
    override fun visit(visitor: DynamicVisitorV1) {
        visitor.acceptVariable(this)
    }

    override fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): TypeValue {
        require(valueMap.containsKey(name)) { "Variable $name not found in valueMap" }
        return valueMap[name]!!.second
    }

    override fun toString(): String {
        return "VariableType(name='$name')"
    }
}
