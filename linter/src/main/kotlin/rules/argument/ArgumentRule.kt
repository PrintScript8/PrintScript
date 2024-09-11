package rules.argument

import error.Error
import node.Node

interface ArgumentRule {
    fun analyzeArguments(node: Node): List<Error>
}
