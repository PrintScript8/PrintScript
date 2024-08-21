package rules.format

import error.Error
import error.Type
import node.dynamic.VariableType
import node.staticpkg.DeclarationType
import node.staticpkg.StaticNode

class CamelCaseRule : FormatRule {
    override fun analyzeFormat(root: DeclarationType): List<Error> {
        if (!root.name.matches(Regex("^[a-z][a-zA-Z0-9]*$"))) {
            return listOf(Error(Type.TYPO, "Declaration name ${root.name} is not in camelCase"))
        }
        return listOf()
    }
}