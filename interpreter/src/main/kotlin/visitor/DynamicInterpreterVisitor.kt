package visitor

import interpreter.InterpreterImpl
import node.dynamic.*
import operations.DynamicVisitor
import type.LiteralType

class DynamicInterpreterVisitor(private val interpreter: InterpreterImpl) : DynamicVisitor {

    override fun acceptLiteral(node: LiteralType) {
        return
    }

    override fun acceptVariable(node: VariableType) {
        if (interpreter.checkValue(node.name)) {
            node.result = interpreter.getValue(node.name).second
            node.canModify = interpreter.getValue(node.name).first
        } else {
            throw Exception("Variable not found")
        }
    }

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
}