import java.io.*

class NewMap {
    class FoundMes(_key: String, _pos: Int, _isKey: Boolean) {
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
                val br = BufferedReader(InputStreamReader(file.inputStream()))
                //TODO: add reading by klaxon
            }
        }
    }

    fun write(filename: String = "data.json") {
        if (persistentStorage) {
            val file = File(filename)
            while (!file.exists())
                if (!file.createNewFile())
                    println("Error creating a new file")

            val bw = BufferedWriter(OutputStreamWriter(file.outputStream()))
            //TODO: add writing by klaxon
        }
    }

    fun set(key: String, value: String) {
        myMap[key] = value
    }

    fun del(key: String) : Boolean {
        if (myMap.containsKey(key)) {
            myMap.remove(key)
            return true
        }
        return false
    }

    fun delValue(value: String) : ArrayList<String>? {
        if (myMap.containsValue(value)) {
            val delKeys = ArrayList <String>()
            for (key in myMap.keys)
                if (myMap[key] == value) {
                    myMap.remove(key)
                    delKeys.add(key)
                }
            return delKeys
        }
        return null
    }

    fun get(key: String) : String? {
        return myMap.getOrDefault(key, null)
    }

    fun clear() {
        myMap.clear()
    }

    fun contains(key: String) : Boolean {
        return myMap.containsKey(key)
    }

    fun containsValue(value: String) : Boolean {
        return myMap.containsValue(value)
    }

    fun find(part: String) : ArrayList<FoundMes> { // Возвращает ключ, индекс данной части в ключе/значении и ключ ли это
        val res = ArrayList<FoundMes>()
        for (key in myMap.keys) {
            if (key.contains(part))
                res.add(FoundMes(key, key.indexOf(part), true))
            else if (myMap[key]!!.contains(part))
                res.add(FoundMes(key, myMap[key]!!.indexOf(part), false))
        }
        return res
    }

    fun isEmpty() : Boolean {
        return myMap.isEmpty()
    }

    fun printPair(key: String) : Boolean {
        if (contains(key)) {
            print("\"")
            printBold(key)
            print("\" : \"" + get(key) + "\"")
            return true
        }
        return false
    }

    fun getMap() : MutableMap <String, String> {
        return myMap
    }
}
