package type

import node.dynamic.DynamicNode
import operations.DynamicVisitor

sealed class LiteralValue {
    data class NumberValue(val number: Number) : LiteralValue()
    data class StringValue(val string: String) : LiteralValue()
}

class LiteralType(override var result: LiteralValue?): DynamicNode {
    override fun visit(visitor: DynamicVisitor) {
        visitor.acceptLiteral(this)
    }
}