package visitor

import interpreter.InterpreterImpl
import node.staticpkg.*
import node.dynamic.DivisionType
import node.dynamic.MultiplyType
import node.dynamic.SubtractType
import node.dynamic.SumType
import operations.DynamicVisitor
import operations.StaticVisitor
import type.LiteralType
import type.LiteralValue

class InterpreterVisitor(private val interpreter: InterpreterImpl) : StaticVisitor, DynamicVisitor {
    override fun acceptSum(node: SumType) {
        node.left.visit(this)
        node.right.visit(this)
        node.result = node.left.result!! + node.right.result!!
    }

    override fun acceptSubtract(node: SubtractType) {
        node.left.visit(this)
        node.right.visit(this)
        node.result = node.left.result!! - node.right.result!!
    }

    override fun acceptMultiply(node: MultiplyType) {
        node.left.visit(this)
        node.right.visit(this)
        node.result = node.left.result!! * node.right.result!!
    }

    override fun acceptDivision(node: DivisionType) {
        node.left.visit(this)
        node.right.visit(this)
        node.result = node.left.result!! / node.right.result!!
    }

    override fun acceptNone(node: NoneType) {
        error("Cannot accept none")
    }

    override fun acceptAssignation(node: AssignationType) {
        if (node.declaration.modifier.canModify) {
            node.value.visit(this)
            interpreter.addValue(node.declaration.value, Pair(node.declaration.modifier.canModify, node.value.result))
        } else {
            throw Exception("Cannot modify a constant")
        }
    }

    override fun acceptLiteral(node: LiteralType) {
        return
    }

    override fun acceptDeclaration(node: DeclarationType) {
        if (interpreter.checkValue(node.value)) {
            interpreter.addValue(node.value, Pair(node.modifier.canModify, null))
        } else {
            throw Exception("Missing modifier!")
        }
    }

    override fun acceptIdentifier(node: IdentifierType) {
        error("Cannot accept identifier")
    }

    override fun acceptModifier(node: ModifierType) {
        error("Cannot accept modifier")
    }
}