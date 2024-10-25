package strategy.staticStrategy

import json.FormattingRules
import node.staticpkg.PrintLnType
import strategy.FormatStrategy
import strategy.dynamicStrategy.DynamicStrategyFactory
import java.io.Writer

class PrintLnStrategy: FormatStrategy<PrintLnType> {

    private val dynamicFactory = DynamicStrategyFactory()

    override fun apply(node: PrintLnType, rules: FormattingRules, writer: Writer) {

        val newlineBeforePrintln = rules.newlineBeforePrintln
        if (newlineBeforePrintln > 0) {
            writer.write("\n".repeat(newlineBeforePrintln))
        }

        writer.write("println(")

        val dynamicStrategy = dynamicFactory.getStrategy(node.argument)
        dynamicStrategy.apply(node.argument, rules, writer)

        writer.write(");\n")
    }
}