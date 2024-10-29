package node.staticpkg

import node.PrimType
import node.TypeValue

// Usa un PrimType que dice el tipo de variable
class IdentifierType(val type: PrimType) : StaticNode {

    override fun execute(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): StaticResult {
        return StaticResult(valueMap, emptyList())
    }

    override fun toString(): String {
        return "IdentifierType($type)"
    }
}
