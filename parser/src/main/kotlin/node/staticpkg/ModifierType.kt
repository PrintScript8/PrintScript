package node.staticpkg

import operations.StaticVisitor

class ModifierType(val value: String, val canModify: Boolean) : StaticNode {
    override fun visit(visitor: StaticVisitor) {
        visitor.acceptModifier(this)
    }

    override fun toString(): String {
        return "ModifierType(value='$value')"
    }
}
