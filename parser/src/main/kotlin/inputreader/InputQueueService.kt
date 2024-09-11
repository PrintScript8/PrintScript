package inputreader

object InputQueueService {
    private lateinit var queue: InputProvider

    fun initialize(provider: InputProvider) {
        queue = provider
    }

    fun getInput(): String? {
        return queue.input("")
    }
}
