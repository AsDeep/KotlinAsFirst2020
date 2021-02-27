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
    var phoneToNameMap = hashMapOf<String, String>()

    companion object {
        fun checkPhone(p: String): Boolean {
            Regex("""[0-9+*#-]+""").matchEntire(p.replace(" ", "")) ?: throw IllegalArgumentException()
            return true
        }
    }

    class Person(var name: String, vararg numbers: String) {

        init {
            name = Regex("""[a-zA-Zа-яА-Я]+ [a-zA-Zа-яА-Я]+""").matchEntire(name.trim())?.toString()
                ?: throw IllegalArgumentException()

        }

        val numList = numbers.toMutableList()

        override fun equals(other: Any?): Boolean {
            if (other !is Person) {
                return false
            }
            return other.name == name && other.numList == numList
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
        return if (name in personMap.keys) {
            personMap.remove(name)
            true
        } else false
    }

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean {
        if (checkPhone(phone) && name in personMap.keys && phone !in phoneToNameMap.keys) {
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
        return if (name in personMap.keys && phone in phoneToNameMap.keys) {
            personMap.remove(name)
            phoneToNameMap.remove(phone)
            true
        } else false
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> {
        if (name in personMap.keys) {
            return personMap[name]?.numList?.toSet() ?: throw IllegalArgumentException()
        }
        return emptySet()
    }

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? {
        return if (phone in phoneToNameMap.keys) {
            phoneToNameMap[phone]
        } else null
    }

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is PhoneBook) {
            return false
        }
        return (other.personMap.keys == personMap.keys) && (other.phoneToNameMap == phoneToNameMap)
    }

    override fun hashCode(): Int {
        var result = personMap.hashCode()
        result = 31 * result + phoneToNameMap.hashCode()
        return result
    }


}