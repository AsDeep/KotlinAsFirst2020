@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */
    companion object {

        fun getValues(s: String): Pair<Double, Double> {
            var r = 0.0
            var i = 1.0
            val res = Regex("""[+-]?\d+[.]*[\d]*""").findAll(s.replace(" ", ""))
            val parts = mutableListOf<Double>()
            for (i in res) {
                parts.add(i.value.toDoubleOrNull() ?: 1.0)
            }
            if (s == "i") return Pair(0.0, 1.0)
            if (s == "-i") return Pair(0.0, -1.0)
            if (parts.size == 1 && s.contains("i")) {
                r = 0.0
                i = parts[0]
            } else if (parts.size == 1) {
                r = parts[0]
                i = 0.0
            } else {
                r = parts[0]
                i = parts[1]
            }
            return Pair(r, i)
        }
    }

    constructor(s: String) : this(getValues(s).first, getValues(s).second)

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(re + other.re, im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-re, -im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex =
        Complex(re * other.re - im * other.im, re * other.im + im * other.re)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex = Complex(
        ((re * other.re) + (im * other.im)) / ((other.re * other.re) + (other.im * other.im)),
        ((other.re * im) - (re * other.im)) / ((other.re * other.re) + (other.im * other.im))
    )

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (other !is Complex) return false
        return re == other.re && im == other.im
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String = "$re${if (im >= 0) "+" else ""}${im}i"
}