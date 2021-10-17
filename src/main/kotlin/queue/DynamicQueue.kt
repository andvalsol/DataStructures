package queue

class DynamicQueue<T> {
    private var elements: Array<Any?>

    private val minCapacity = 12

    private var size = 0

    constructor(initialCapacity: Int) {
        this.elements = arrayOfNulls(initialCapacity)
    }

    // when no parameters are given, initialize the array with the minCapacity
    constructor() {
        this.elements = arrayOfNulls(minCapacity)
    }

    fun isEmpty() = (size == 0)

    // For internal use only, so that we can determine if we need to increment the size of the list
    private fun isFull() = (size == elements.size)

    // Add an element at the beginning of the queue
    fun enqueue(element: T) {
        // This adds at the very end of the queue
        if (isFull()) {
            // Increase the size of the array and add the element

            // Create a new array of nulls
            val newArrayOfNulls = arrayOfNulls<Any?>(size +
                if (size  < minCapacity / 2) minCapacity
                else size shr 1)

            System.arraycopy(elements, 0, newArrayOfNulls, 0, size)

            elements = newArrayOfNulls

        }

        // Simply add the element
        elements[size] = element

        size++
    }

    // Return the first element of the queue
    fun dequeue(): T {
        if (isEmpty()) throw Exception("StackUnderflowException")

        val elementAtFirstPosition = elements[0]

        elements[0] = null

        System.arraycopy(elements, 1, elements, 0, size--)

        return elementAtFirstPosition as T
    }

    fun front(): T {
        if (isEmpty()) throw IndexOutOfBoundsException("Current size is 0")

        return elements[0] as T
    }

    fun rear() {
        if (isEmpty()) throw IndexOutOfBoundsException("Current size is 0")
    }
}
