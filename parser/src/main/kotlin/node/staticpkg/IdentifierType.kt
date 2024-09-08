package node.staticpkg

import node.PrimType
import node.TypeValue
import operations.StaticVisitorV1

// Usa un PrimType que dice el tipo de variable
class IdentifierType(val type: PrimType) : StaticNode {
    override fun visit(visitor: StaticVisitorV1) {
        visitor.acceptIdentifier(this)
    }

    override fun run(
        valueMap: Map<String, Pair<Boolean, TypeValue>>,
        version: String
    ): StaticResult {
        return StaticResult(valueMap, emptyList())
    }

    override fun format(version: String): String {
        return type.name.toLowerCase()
    }

    override fun toString(): String {
        return "IdentifierType($type)"
    }
}
