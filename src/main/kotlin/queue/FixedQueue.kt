package queue

class FixedQueue<T> {
    private val elements: Array<Any?>

    private var size = 0

    constructor(capacity: Int) {
        this.elements = arrayOfNulls(capacity)
    }

    // Function that adds an elements at the end of the stack
    fun enqueue(element: T) {
        if (isFull()) throw Exception("StackOverflowException")

        elements[size++] = element
    }

    // Function that returns the first element of the queue and removes it
    fun dequeue(): T {
        if (isEmpty()) throw Exception("StackUnderflowException")

        val elementAtFirstPosition = elements[0]

        System.arraycopy(elements, 1, elements, 0, size--)

        return elementAtFirstPosition as T
    }

    fun front(): T {
        if (isEmpty()) throw IndexOutOfBoundsException("Current size of the list is 0, insert at least one element")

        return elements[0] as T
    }

    fun rear(): T {
        if (isEmpty()) throw IndexOutOfBoundsException("Current size of the list is 0, insert at least one element")

        return elements[size - 1] as T
    }

    fun isEmpty() = (size == 0)

    fun isFull() = (size == elements.size)
}
