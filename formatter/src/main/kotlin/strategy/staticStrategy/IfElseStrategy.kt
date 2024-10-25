package strategy.staticStrategy

import json.FormattingRules
import node.staticpkg.IfElseType
import strategy.FormatStrategy
import strategy.dynamicStrategy.utils.DynamicStrategyFactory
import strategy.staticStrategy.utils.StaticStrategyFactory
import java.io.Writer

class IfElseStrategy: FormatStrategy<IfElseType> {

    private val dynamicStrategyFactory = DynamicStrategyFactory()
    private val staticStrategyFactory = StaticStrategyFactory()

    override fun apply(node: IfElseType, rules: FormattingRules, writer: Writer) {

        writer.write("if (")
        val strategyCondition = dynamicStrategyFactory.getStrategy(node.boolean)
        strategyCondition.apply(node.boolean, rules, writer)
        writer.write(") {\n")
        writer.write(" ".repeat(rules.indentation * 4))
        for (child in node.ifBranch) {
            val strategy = staticStrategyFactory.getStrategy(child)
            strategy.apply(child, rules, writer)
            if (child !is IfElseType) {
                writer.write("\n")
                writer.write(" ".repeat(rules.indentation * 4))
            }
        }
        writer.write("} else {\n")
        writer.write(" ".repeat(rules.indentation * 4))
        if (node.elseBranch?.isNotEmpty() == true) {
            for (child in node.elseBranch!!) {
                val strategy = staticStrategyFactory.getStrategy(child)
                strategy.apply(child, rules, writer)
                if (child !is IfElseType) {
                    writer.write("\n")
                    writer.write(" ".repeat(rules.indentation * 4))
                }
            }
        }
        writer.write("}")
    }
}