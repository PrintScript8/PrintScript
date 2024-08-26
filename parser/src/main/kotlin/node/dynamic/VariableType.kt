package node.dynamic

import operations.DynamicVisitor


class VariableType(val name: String, override var result: LiteralValue?, var canModify: Boolean) : DynamicNode {
    override fun visit(visitor: DynamicVisitor) {
        visitor.acceptVariable(this)
    }

    override fun toString(): String {
        return "VariableType(name='$name')"
    }
}