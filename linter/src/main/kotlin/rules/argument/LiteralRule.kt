package rules.argument

import error.Error
import error.Type
import node.dynamic.LiteralType
import node.staticpkg.PrintLnType

class LiteralRule : ArgumentRule {
    override fun analyzeArguments(printLnType: PrintLnType): List<Error> {
        if (printLnType.argument !is LiteralType) {
            return listOf(error.Error(Type.ERROR, "Only literal is allowed as argument"))
        }
        return listOf()
    }
}
