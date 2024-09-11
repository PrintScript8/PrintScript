package rules.argument

import error.Error
import error.Type
import node.Node
import node.dynamic.LiteralType
import node.dynamic.VariableType
import node.staticpkg.PrintLnType

class PrintIsMandatoryRule(private val isMandatory: Boolean) : ArgumentRule {
    override fun analyzeArguments(node: Node): List<Error> {
        if (node !is PrintLnType) {
            return listOf()
        }
        return errors(node)
    }

    private fun errors(node: PrintLnType): List<Error> {
        val argumentCondition = node.argument !is LiteralType && node.argument !is VariableType
        if (argumentCondition && isMandatory) {
            return listOf(Error(Type.ERROR, "Only literal or variable types are allowed as argument"))
        }
        return listOf()
    }
}
