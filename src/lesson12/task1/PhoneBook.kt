@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "Телефонная книга".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 14.
 * Объект класса хранит список людей и номеров их телефонов,
 * при чём у каждого человека может быть более одного номера телефона.
 * Человек задаётся строкой вида "Фамилия Имя".
 * Телефон задаётся строкой из цифр, +, *, #, -.
 * Поддерживаемые методы: добавление / удаление человека,
 * добавление / удаление телефона для заданного человека,
 * поиск номера(ов) телефона по заданному имени человека,
 * поиск человека по заданному номеру телефона.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class PhoneBook {

    var personMap = mutableMapOf<String, Person>()
    var phoneToNameMap = mutableMapOf<String, String>()

    companion object {
        fun checkPhone(p: String): Boolean {
            Regex("""[0-9+*#-]+""").matchEntire(p.replace(" ", "")) ?: throw IllegalArgumentException()
            return true
        }
    }

    class Person(var name: String, vararg numbers: String) {

        init {
            name = Regex("""[a-zA-Zа-яА-Я]+ [a-zA-Zа-яА-Я]+""").matchEntire(name.trim())?.groupValues?.get(0)
                ?: throw IllegalArgumentException()
        }

        val numList = numbers.toMutableSet()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Person) {
                return false
            }
            return other.name == name && other.numList == numList
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + numList.hashCode()
            return result
        }

    }

    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean {
        if (name !in personMap.keys) {
            personMap.put(name, Person(name))
            return true
        } else return false
    }

    /**
     * Убрать человека.
     * Возвращает true, если человек был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun removeHuman(name: String): Boolean {
        for (num in personMap[name]?.numList ?: return false) {
            phoneToNameMap.remove(num)
        }
        personMap.remove(name)
        return true
    }

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean {
        if (checkPhone(phone) && phone !in phoneToNameMap.keys) {
            personMap[name]?.numList?.add(phone) ?: return false
            phoneToNameMap.put(phone, name)
            return true
        } else
            return false
    }

    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean {
        personMap[name]?.numList?.remove(phone) ?: return false
        phoneToNameMap.remove(phone) ?: return false
        return true
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> = personMap[name]?.numList ?: emptySet()

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? = phoneToNameMap[phone]


    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneBook) {
            return false
        }
        return personMap == other.personMap
    }

    override fun hashCode(): Int {
        var result = personMap.hashCode()
        result = 31 * result + phoneToNameMap.hashCode()
        return result
    }


}