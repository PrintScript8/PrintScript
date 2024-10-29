package node.dynamic

import node.TypeValue

class VariableType(val name: String, override var result: LiteralValue?) : DynamicNode {

    override fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): TypeValue {
        require(valueMap.containsKey(name)) { "Variable $name not found in valueMap" }
        return valueMap[name]!!.second
    }

    override fun toString(): String {
        return "VariableType(name='$name')"
    }
}
