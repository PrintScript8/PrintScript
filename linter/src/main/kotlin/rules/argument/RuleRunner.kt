package rules.argument

import error.Error
import node.Node

class RuleRunner(val leftRule: ArgumentRule?, val rightRule: ArgumentRule?) : ArgumentRule {
    override fun analyzeArguments(node: Node): List<Error> {
        val leftErrors = leftRule?.analyzeArguments(node) ?: listOf()
        val rightErrors = rightRule?.analyzeArguments(node) ?: listOf()
        return leftErrors + rightErrors
    }
}
