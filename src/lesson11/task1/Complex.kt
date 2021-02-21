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
            var strPlus = s.split('+')
            val strMinus = s.split('-')
            if (strMinus.size > strPlus.size) {
                strPlus = strMinus
                i *= -1.0
            }
            if (strPlus.isEmpty()) return Pair(r, 0.0)
            else if ( strPlus[0].isEmpty()) return Pair(
                r,
                strPlus[1].filter { it != 'i' }.toDouble()
            )

            r = strPlus[0].toDouble()
            i *= strPlus[1].filter { it != 'i' }.toDouble()
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
    override fun toString(): String = "$re${if (im > 0) "+" else ""}${im}i"
}