package rules.argument

import error.Error
import error.Type
import node.Node
import node.dynamic.LiteralType
import node.dynamic.ReadInputType
import node.dynamic.VariableType

class InputIsMandatoryRule(private val isMandatory: Boolean) : ArgumentRule {
    override fun analyzeArguments(node: Node): List<Error> {
        if (node !is ReadInputType) {
            return listOf()
        }
        return errors(node)
    }

    private fun errors(node: ReadInputType): List<Error> {
        val argumentCondition = node.argument !is LiteralType && node.argument !is VariableType
        if (argumentCondition && isMandatory) {
            return listOf(Error(Type.ERROR, "Only literal or variable types are allowed as argument"))
        }
        return listOf()
    }
}
