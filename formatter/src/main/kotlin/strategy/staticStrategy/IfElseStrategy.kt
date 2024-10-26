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

        val nodeIterator = node.ifBranch.iterator()

        writer.write("    ".repeat(rules.indentation))
        writer.write("if (")

        val strategy = dynamicStrategyFactory.getStrategy(node.boolean)
        strategy.apply(node.boolean, rules, writer)

        writer.write(") {\n")

        rules.indentation++
        writer.write("    ".repeat(rules.indentation))
        nodeIterator.forEach {
            val staticStrategy = staticStrategyFactory.getStrategy(it)
            staticStrategy.apply(it, rules, writer)
            writer.write("\n")
            if (nodeIterator.hasNext()) {
                writer.write("    ".repeat(rules.indentation))
            }
        }

        if (node.elseBranch != null) {
            rules.indentation--
            writer.write("} else {\n")
            rules.indentation++
            writer.write("    ".repeat(rules.indentation))
            val elseIterator = node.elseBranch!!.iterator()
            elseIterator.forEach {
                val staticStrategy = staticStrategyFactory.getStrategy(it)
                staticStrategy.apply(it, rules, writer)
                writer.write("\n")
                if (elseIterator.hasNext()) {
                    writer.write("    ".repeat(rules.indentation))
                }
            }
        }
        writer.write("}")
    }
}