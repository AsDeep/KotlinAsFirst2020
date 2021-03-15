package lesson11.task1

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная, общая ценность в баллах -- 32.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */

class UnsignedBigInteger : Comparable<UnsignedBigInteger> {
    private var digitList = mutableListOf<Int>()

    private fun fromString(s: String) {
        if (s.isNotEmpty()) {
            for (i in s.trimStart('0')) {
                digitList.add(
                    Regex("""[0-9]""").matchEntire(i.toString())?.groupValues?.get(0)?.toIntOrNull()
                        ?: throw IllegalArgumentException()
                )
            }
        } else throw IllegalArgumentException()
        digitList.reverse()

    }

    /**
     * Конструктор из строки
     */
    constructor(s: String) {
        fromString(s)
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        fromString(i.toString())
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        val result = UnsignedBigInteger(0)
        for (i in 0 until max(this, other).digitList.size) {
            try {
                result.digitList.add(digitList[i] + other.digitList[i])
            } catch (ex: IndexOutOfBoundsException) {
                result.digitList.add(i, max(this, other).digitList[i])
            }
        }
        for (i in 0 until result.digitList.size) {
            if (result.digitList[i] >= 10) {
                result.digitList[i + 1] += 1
                result.digitList[i] %= 10
            }
        }
        return result
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        if (this < other) throw ArithmeticException()
        val result = UnsignedBigInteger(0)
        result.digitList = digitList
        println(result.digitList.size)

        for (i in 0 until result.digitList.size) {
            result.digitList[i] -= other.digitList[i]
            if (result.digitList[i] < 0) {
                result.digitList[i + 1] -= 1
                result.digitList[i] += 10
            }
        }
        return result

    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UnsignedBigInteger) return false
        return this.digitList == other.digitList
    }

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int {
        if (digitList.size != other.digitList.size) {
            return digitList.size.compareTo(other.digitList.size)
        } else {
            var i = digitList.size - 1
            while (digitList[i].compareTo(other.digitList[i]) == 0) {
                i -= 1
                if (i == 0) break
            }
            return digitList[i].compareTo(other.digitList[i])

        }
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String {
        digitList.reverse()
        return digitList.joinToString(separator = "").trimStart('0')
    }

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int {
        if (this > UnsignedBigInteger(Int.MAX_VALUE)) throw ArithmeticException()
        var result = 0
        for (i in 0 until digitList.size) {
            result += digitList[i] * 10.0.pow(i.toDouble()).toInt()
        }
        return result
    }

    fun max(a: UnsignedBigInteger, b: UnsignedBigInteger): UnsignedBigInteger {
        if (a > b) return a
        return b
    }



}