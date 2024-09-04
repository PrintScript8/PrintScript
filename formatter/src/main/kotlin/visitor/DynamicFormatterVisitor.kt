package visitor

import formatter.FormatterImpl
import node.dynamic.DivisionType
import node.dynamic.LiteralType
import node.dynamic.LiteralValue
import node.dynamic.MultiplyType
import node.dynamic.SubtractType
import node.dynamic.SumType
import node.dynamic.VariableType
import operations.DynamicVisitor

class DynamicFormatterVisitor(
    val formatter: FormatterImpl,
    private val staticVisitor: StaticFormatterVisitor
) : DynamicVisitor {

    override fun acceptSum(node: SumType) {
        node.left.visit(this)
        staticVisitor.appendOutput(" + ")
        node.right.visit(this)
    }

    override fun acceptSubtract(node: SubtractType) {
        node.left.visit(this)
        staticVisitor.appendOutput(" - ")
        node.right.visit(this)
    }

    override fun acceptMultiply(node: MultiplyType) {
        node.left.visit(this)
        staticVisitor.appendOutput(" * ")
        node.right.visit(this)
//        if (node.left is LiteralType) {
//            output.append("(");
//            node.left.visit(this)
//            output.append(")");
//        }
//        else {
//            node.left.visit(this)
//        }
//        output.append(" * ")
//        if (node.right is LiteralType) {
//            output.append("(");
//            node.right.visit(this)
//            output.append(")");
//        }
//        else {
//            node.right.visit(this)
//        }
    }

    override fun acceptDivision(node: DivisionType) {
        node.left.visit(this)
        staticVisitor.appendOutput(" / ")
        node.right.visit(this)
    }

    override fun acceptLiteral(node: LiteralType) {
        if (node.result is LiteralValue.StringValue) {
            staticVisitor.appendOutput("\"${node.result}\"")
        } else {
            staticVisitor.appendOutput(node.result.toString())
        }
    }

    override fun acceptVariable(node: VariableType) {
        staticVisitor.appendOutput(node.name)
    }
}
