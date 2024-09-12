package node.dynamic

import node.TypeValue

class VariableType(val name: String, override var result: LiteralValue?, var canModify: Boolean) : DynamicNode {
    // TODO: Sacar el canModify de la logica de los variables

    override fun execute(valueMap: Map<String, Pair<Boolean, TypeValue>>, version: String): TypeValue {
        require(valueMap.containsKey(name)) { "Variable $name not found in valueMap" }
        return valueMap[name]!!.second
    }

    override fun format(version: String): String {
        return name
    }

    override fun toString(): String {
        return "VariableType(name='$name')"
    }
}
