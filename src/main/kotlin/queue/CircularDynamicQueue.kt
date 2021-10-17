package queue

class CircularDynamicQueue<T> {
    var front = -1
    var rear = -1

    var elements = arrayOfNulls<Any?>(12)

    val size
        get() = elements.size

    val isFull
        get() = ((rear + 1) % size == front)

    val isEmpty
        get() = (front == -1 && rear == -1)


    fun enqueue(element: T) {
        if (isFull) {
            val newArray = arrayOfNulls<Any?>(size + 12)

            System.arraycopy(elements, 0, newArray, 0, size)

            elements = newArray
        } else if (isEmpty) front = 0

        rear = (rear + 1) % size

        elements[rear] = element
    }

    fun dequeue(): T? {
        return if (isEmpty) null
        else {
            val element = elements[front]

            elements[front] = null

            if (rear == front) {
                rear = -1
                front = -1
            }

            element as T
        }
    }
}
