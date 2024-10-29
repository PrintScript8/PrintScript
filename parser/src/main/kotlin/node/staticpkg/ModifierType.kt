package node.staticpkg

import node.TypeValue

class ModifierType(val value: String, val canModify: Boolean) : StaticNode {

    override fun execute(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): StaticResult {
        return StaticResult(valueMap, emptyList())
    }

    override fun toString(): String {
        return "ModifierType(value='$value')"
    }
}
