package strategy.dynamicStrategy

import json.FormattingRules
import node.dynamic.*
import strategy.FormatStrategy
import strategy.provider.DynamicStrategyProvider
import java.io.Writer

class OperationStrategy: FormatStrategy<DynamicNode> {

    private val dynamicFactory = DynamicStrategyProvider()

    override fun apply(node: DynamicNode, rules: FormattingRules, writer: Writer) {

        when (node) {
            is SumType -> {
                val left = node.left
                val operator = "+"
                val right = node.right
                writeOperation(left, operator, right, rules, writer)
            }
            is DivisionType -> {
                val left = node.left
                val operator = "/"
                val right = node.right
                writeOperation(left, operator, right, rules, writer)
            }
            is MultiplyType -> {
                val left = node.left
                val operator = "*"
                val right = node.right
                writeOperation(left, operator, right, rules, writer)
            }
            is SubtractType -> {
                val left = node.left
                val operator = "-"
                val right = node.right
                writeOperation(left, operator, right, rules, writer)
            }
            else -> throw IllegalArgumentException("Unknown node type")
        }
    }

    private fun writeOperation(left: DynamicNode, operator: String, right: DynamicNode, rules: FormattingRules, writer: Writer) {
        val leftStrategy = dynamicFactory.getStrategy(left, rules.version)
        leftStrategy.apply(left, rules, writer)
        writer.write(" $operator ")
        val rightStrategy = dynamicFactory.getStrategy(right, rules.version)
        rightStrategy.apply(right, rules, writer)
    }
}