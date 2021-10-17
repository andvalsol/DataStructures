package stack

class FixedStack<T> {
    private val elements: Array<Any?>
    private var size = 0

    constructor(capacity: Int) {
        this.elements = arrayOfNulls(capacity)
    }

    fun push(element: T) {
        if (isFull()) {
            // Means that we have reached the max capacity of the stack
            throw Exception("Overflow exception")
        }

        elements[size++] = element
    }

    fun pop(): T {
        if (isEmpty()) {
            throw Exception("Underflow exception")
        }

        val obj = elements[--size]
        elements[size] = null // We need to set the value to null again since we're removing entirely the value

        return obj as T
    }

    // Function that gets the element without removing it
    fun peek(): T {
        if (isEmpty()) throw Exception("No element has been added")

        return elements[size - 1] as T
    }

    fun isEmpty() = (size == 0)

    fun isFull() = (size == elements.size)
}
