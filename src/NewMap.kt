import java.io.*

class NewMap { // Оболочка для map
    class FoundMes(_key: String, _pos: Int, _isKey: Boolean) { // Вспомогательный класс для поиска
        val key = _key
        val pos = _pos
        val isKey = _isKey
    }

    private val myMap: MutableMap<String, String> = mutableMapOf()

    init {
        load()
    }

    private fun load(filename: String = "data.json") {
        if (persistentStorage) {
            val file = File(filename)
            if (file.exists()) {
                val inputArray = file.bufferedReader().use { it.readText() }.split("\n")
                for (i in 0 until (inputArray.size - inputArray.size % 2) step 2)
                    set(inputArray[i], inputArray[i + 1])
            }
        }
    }

    fun write(filename: String = "data.json") {
        if (persistentStorage) {
            val file = File(filename)
            while (!file.exists())
                if (!file.createNewFile())
                    println("Error creating a new file")
            val bufferedWriter = file.bufferedWriter()
            for (key in getKeys())
                bufferedWriter.write("$key\n${get(key)}")
            bufferedWriter.close()
        }
    }

    fun set(key: String, value: String) {
        myMap[key] = value
    }

    fun del(key: String) {
        myMap.remove(key)
    }

    fun delValue(value: String): ArrayList<String> {
        val delKeys = ArrayList<String>()
        val keys = getKeys().toTypedArray()
        for (key in keys)
            if (get(key) == value) {
                del(key)
                delKeys.add(key)
            }
        return delKeys
    }

    fun get(key: String): String? {
        return myMap[key]
    }

    fun clear() {
        myMap.clear()
    }

    fun contains(key: String): Boolean {
        return myMap.containsKey(key)
    }

    fun containsValue(value: String): Boolean {
        return myMap.containsValue(value)
    }

    fun find(part: String): ArrayList<FoundMes> { // Возвращает ключ, индекс данной части в ключе/значении и ключ ли это
        val res = ArrayList<FoundMes>()
        for (key in getKeys())
            if (key.contains(part))
                res.add(FoundMes(key, key.indexOf(part), true))
            else if (get(key)!!.contains(part))
                res.add(FoundMes(key, get(key)!!.indexOf(part), false))
        return res
    }

    fun findRegex(regex: Regex): ArrayList<String> { // Возвращает только ключи
        val res = ArrayList<String>()
        for (key in getKeys())
            if (key.matches(regex) || get(key)!!.matches(regex))
                res.add(key)
        return res
    }

    fun isEmpty(): Boolean {
        return myMap.isEmpty()
    }

    fun printPair(key: String) {
        print("\"${printBold(key)}\" : \"${get(key)}\"")
    }

    fun getKeys(): Set<String> {
        return myMap.keys
    }
}
