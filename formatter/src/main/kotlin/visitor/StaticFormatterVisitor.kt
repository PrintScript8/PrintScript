package visitor

import formatter.FormatterImpl
import node.staticpkg.AssignationType
import node.staticpkg.DeclarationType
import node.staticpkg.ExpressionType
import node.staticpkg.IdentifierType
import node.staticpkg.ModifierType
import node.staticpkg.PrintLnType
import operations.StaticVisitor
import java.util.Locale

class StaticFormatterVisitor(val formatter: FormatterImpl) : StaticVisitor {

    private var output: StringBuilder = StringBuilder()
    private val dynamicVisitor = DynamicFormatterVisitor(formatter, this)

    override fun acceptAssignation(node: AssignationType) {
        node.declaration.visit(this)
        output.append(" = ")
        node.value.visit(dynamicVisitor)
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
        val type = node.type.name
        output.append(lowerCase(type))
    }

    override fun acceptPrintLn(node: PrintLnType) {
        output.append("println(")
        node.argument.visit(dynamicVisitor)
        output.append(")")
    }

    override fun acceptExpression(node: ExpressionType) {
        node.variable.visit(dynamicVisitor)
        output.append(" = ")
        node.value.visit(dynamicVisitor)
    }

    fun getOutput(): String {
        val result = output.toString()
        output.clear()
        return result
    }

    fun appendOutput(text: String) {
        val formattedText = text.replace("\"\"", "\"")
        output.append(formattedText)
    }

    private fun lowerCase(input: String): String {
        return input.lowercase(Locale.getDefault())
    }
}
