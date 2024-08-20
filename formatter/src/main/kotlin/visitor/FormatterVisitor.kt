package visitor

import formatter.FormatterImpl
import node.dynamic.*
import node.staticpkg.*
import operations.DynamicVisitor
import operations.StaticVisitor
import type.LiteralType
import type.LiteralValue

class FormatterVisitor(val formatter: FormatterImpl) : StaticVisitor, DynamicVisitor {

    private var output: StringBuilder = StringBuilder()

    override fun acceptNone(node: NoneType) {
        TODO("Not yet implemented")
    }

    override fun acceptAssignation(node: AssignationType) {
        node.declaration.visit(this)
        output.append(" = ")
        node.value.visit(this)
    }

    override fun acceptModifier(node: ModifierType) {
        output.append("${node.value} ")
    }

    override fun acceptDeclaration(node: DeclarationType) {
        node.modifier.visit(this)
        output.append("${node.name}: ")
        node.type.visit(this)
    }

    override fun acceptIdentifier(node: IdentifierType) {
        output.append("Identifier")
    }

    override fun acceptPrintLn(node: PrintLnType) {
        output.append("println(")
        node.argument.visit(this)
        output.append(")")
    }

    override fun acceptExpression(node: ExpressionType) {
        node.value.visit(this)
        output.append(" = ")
        node.value.visit(this)
    }

    override fun acceptSum(node: SumType) {
        node.left.visit(this)
        output.append(" + ")
        node.right.visit(this)
    }

    override fun acceptSubtract(node: SubtractType) {
        node.left.visit(this)
        output.append(" - ")
        node.right.visit(this)
    }

    override fun acceptMultiply(node: MultiplyType) {
        if (node.left is LiteralType) {
            output.append("(");
            node.left.visit(this)
            output.append(")");
        }
        else {
            node.left.visit(this)
        }
        output.append(" * ")
        if (node.right is LiteralType) {
            output.append("(");
            node.right.visit(this)
            output.append(")");
        }
        else {
            node.right.visit(this)
        }
    }

    override fun acceptDivision(node: DivisionType) {
        node.left.visit(this)
        output.append(" / ")
        node.right.visit(this)
    }

    override fun acceptLiteral(node: LiteralType) {
        if (node.result is LiteralValue.StringValue) {
            output.append("\"${node.result}\"")
        } else {
            output.append(node.result.toString())
        }
    }

    override fun acceptVariable(node: VariableType) {
        output.append(node.name)
    }

    fun getOutput(): String {
        val result = output.toString()
        output.clear()
        return result
    }
}
