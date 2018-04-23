class NewMap {
    val myMap: MutableMap<String, String> = mutableMapOf()

    
    init {
        load()
    }

    fun load(filename: String = "data.json") {
        //TODO

    }

    fun write(filename: String = "data.json") {
        //TODO
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
            //TODO: do
            return null //TODO: return deleted key
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

    fun find(part: String) : ArrayList<Pair<String, Pair<Int, Boolean>>> { // Возвращает ключ, индекс данной части в ключе/значении и ключ ли это
        //TODO: сделать класс вместо пары строки и пары числа с bool
        val res = ArrayList<Pair<String, Pair<Int, Boolean>>>()
        for (key in myMap.keys) {
            if (key.contains(part))
                res.add(key to (key.indexOf(part) to true))
            else if (myMap[key]!!.contains(part))
                res.add(key to (myMap[key]!!.indexOf(part) to false))
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
