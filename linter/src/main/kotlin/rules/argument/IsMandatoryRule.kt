package rules.argument

import error.Error
import error.Type
import node.dynamic.LiteralType
import node.dynamic.VariableType
import node.staticpkg.PrintLnType

class IsMandatoryRule(private val isMandatory: Boolean) : ArgumentRule {
    override fun analyzeArguments(printLnType: PrintLnType): List<Error> {
        val argumentCondition = printLnType.argument !is LiteralType && printLnType.argument !is VariableType
        if (argumentCondition && isMandatory) {
            return listOf(error.Error(Type.ERROR, "Only literal or variable types are allowed as argument"))
        }
        return listOf()
    }
}
