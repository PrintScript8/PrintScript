package strategy.staticStrategy

import json.FormattingRules
import node.staticpkg.IfElseType
import strategy.FormatStrategy
import strategy.provider.DynamicStrategyProvider
import strategy.provider.StaticStrategyProvider
import java.io.Writer

class IfElseStrategy: FormatStrategy<IfElseType> {

    private val dynamicStrategyProvider = DynamicStrategyProvider()
    private val staticStrategyProvider = StaticStrategyProvider()

    override fun apply(node: IfElseType, rules: FormattingRules, writer: Writer) {

        if (rules.version == "1.0") {
            throw IllegalArgumentException("IfElse method is not supported in version 1.0")
        }

        val nodeIterator = node.ifBranch.iterator()

        writer.write("if (")

        val strategy = dynamicStrategyProvider.getStrategy(node.boolean, rules.version)
        strategy.apply(node.boolean, rules, writer)

        writer.write(") {\n")

        rules.indentation++
        writer.write("    ".repeat(rules.indentation))
        nodeIterator.forEach {
            val staticStrategy = staticStrategyProvider.getStrategy(it, rules.version)
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
                val staticStrategy = staticStrategyProvider.getStrategy(it, rules.version)
                staticStrategy.apply(it, rules, writer)
                writer.write("\n")
                if (elseIterator.hasNext()) {
                    writer.write("    ".repeat(rules.indentation))
                }
            }
        }
        rules.indentation--
        writer.write("    ".repeat(rules.indentation))
        writer.write("}")
    }
}