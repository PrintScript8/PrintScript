package node

import node.dynamic.LiteralValue

class TypeValue(val value: LiteralValue?, val type: PrimType) {
    operator fun component1(): LiteralValue? {
        return value
    }
    operator fun component2(): PrimType {
        return type
    }
}
