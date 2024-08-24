package rules.format

import error.Error
import node.staticpkg.DeclarationType

interface FormatRule {
    fun analyzeFormat(root: DeclarationType): List<Error>
}
