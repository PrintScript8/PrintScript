package rules.format

import error.Error
import error.Type
import node.staticpkg.DeclarationType
import node.staticpkg.StaticNode

class SnakeRule : FormatRule {

    override fun analyzeFormat(root: DeclarationType): List<Error> {
        if (!root.name.matches(Regex("^[a-z][a-z_0-9]*$"))) {
            return listOf(Error(Type.TYPO, "Declaration name ${root.name} is not in snake_case"))
        }
        return listOf()
    }
}