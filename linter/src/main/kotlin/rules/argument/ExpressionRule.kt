package rules.argument

import error.Type
import error.Error
import node.staticpkg.PrintLnType
import java.beans.Expression

class ExpressionRule : ArgumentRule {
    override fun analyzeArguments(printLnType: PrintLnType): List<Error> {
        if (printLnType.argument !is Expression){
            return listOf(error.Error(Type.ERROR, "Only expression is allowed as argument"))
        }
        return listOf()
    }
}