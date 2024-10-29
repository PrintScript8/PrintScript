package utils

import java.nio.file.Paths

class PathFactory(private val basePath: String) {

    fun providePath(number: Int, type: String): String {
        return filePath(number, type)
    }

    private fun filePath(fileNumber: Int, type: String): String {
        val fileName = "$type$fileNumber"
        return trim(getBasePath(fileName, type))
    }

    private fun getBasePath(fileName: String, type: String): String {
        val projectRoot = Paths.get("").toAbsolutePath().toString()
        val packagePath = Paths.get(projectRoot, basePath).resolve(type)
        return packagePath.resolve(fileName).toString()
    }

    private fun trim(filePath: String): String {
        val fileString = java.io.File(filePath).readText()
        return fileString.trimIndent().trim()
    }
}
