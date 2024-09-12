package inputreader

object InputQueueService {
    private lateinit var queue: Iterator<String>

    fun initialize(provider: Iterator<String>) {
        queue = provider
    }

    fun getInput(): String? {
        if (queue.hasNext()) {
            return queue.next()
        }
        return null
    }
}
