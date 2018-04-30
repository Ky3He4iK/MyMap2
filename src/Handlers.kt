import java.util.regex.PatternSyntaxException

open class HandlerBase {
    open fun isMatch(command: String, myMap: NewMap): Boolean = false
    open fun onMatch(data: String?, myMap: NewMap) {}
}

class HandlerAdd : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "add"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        var mData = data
        if (mData == null) {
            print("Please, enter the key: ")
            mData = readLine()!!
        }
        if (myMap.contains(mData))
            println("This key already exists!")
        else {
            print("And now, value for this key: ")
            myMap.set(mData, readLine()!!)
            println("Added successfully")
        }
    }
}

class HandlerClear : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "clear"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        if (getAssent("All pairs will be deleted. Continue?")) {
            myMap.clear()
            println("Cleared!")
        }
    }
}

class HandlerDel : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "del"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        var mData = data
        if (mData == null) {
            print("Please, enter the key: ")
            mData = readLine()!!
        }
        if (myMap.contains(mData)) {
            println("These pair will be deleted:")
            myMap.printPair(mData)
            if (getAssent("Are you sure?")) {
                myMap.del(mData)
                println("Deleted successfully")
            }
        } else
            println("I haven't this key!")
    }
}

class HandlerDelByVal : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "del_v"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        var mData = data
        if (mData == null) {
            print("Please, enter the value: ")
            mData = readLine()!!
        }
        val found = myMap.findByValues(mData)
        if (found.isEmpty())
            println("I haven't any pair with this value!")
        else {
            println("These pairs will be deleted")
            for (key in found)
                myMap.printPair(key)
            if (getAssent("Are you sure?"))
                myMap.del(found)
        }
    }
}

class HandlerFind : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "find"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        var mData = data
        if (mData == null) {
            print("Please, enter the part of key or value: ")
            mData = readLine()!!
        }
        val found = myMap.find(mData)
        if (found.isEmpty())
            println("I can't found pair with $mData")
        else
            for (foundWrite in found) {
                print("\"")
                if (foundWrite.isKey)
                    print(foundWrite.key.substring(0, foundWrite.pos) + toBoldString(mData) +
                            foundWrite.key.substring(foundWrite.pos + mData.length))
                else
                    print(foundWrite.key)
                print("\" : \"")
                if (foundWrite.isKey)
                    print(myMap.get(foundWrite.key))
                else
                    print(myMap.get(foundWrite.key)!!.substring(0, foundWrite.pos) + toBoldString(mData) +
                            myMap.get(foundWrite.key)!!.substring(foundWrite.pos + mData.length))
                println("\"")
            }
    }
}

class HandlerHelp : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "help"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        for (com in help)
            if (com.first.isEmpty())
                println()
            else
                println("${toBoldString(com.first)} - ${com.second}")
    }
}

class HandlerList : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "list"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        if (!myMap.isEmpty())
            for (key in myMap.getKeys())
                HandlerShow().onMatch(key, myMap)
        else
            println("I haven't any data yet :(")
    }
}

class HandlerLoad : HandlerBase() {

    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "load"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        if (!myMap.isEmpty())
            if (!getAssent("I have a data that will be erased, continue?"))
                return
        var mData = data
        if (mData == null) {
            print("Please, enter filename (empty line for \"data.txt\"): ")
            mData = readLine()!!
        }
        val res = if (mData.isEmpty()) myMap.load() else myMap.load(mData)
        if (res)
            println("Loaded successfully!")
        else
            println("Seems like this file doesn't exists. Operation canceled")
    }

}

class HandlerRegex : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "regex"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        var mData = data
        if (mData == null) {
            print("Please, enter the regex: ")
            mData = readLine()!!
        }
        try {
            val found = myMap.findRegex(mData.toRegex())
            if (found.isEmpty())
                println("I can't found keys or values that matches $mData")
            else
                for (foundString in found)
                    println("\"${toBoldString(foundString)}\" : \"${myMap.get(foundString)}\"")
        } catch (e: PatternSyntaxException) {
            println("Seems like there is an error with your expression: ${e.description}")
        }
    }
}

class HandlerSet : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "set"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        var mData = data
        if (mData == null) {
            print("Please, enter the key: ")
            mData = readLine()!!
        }
        if (myMap.contains(mData)) {
            print("And now, value for this key: ")
            myMap.set(mData, readLine()!!)
            println("Set successfully")
        } else
            println("I haven't this key!")
    }
}

class HandlerShow : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "show"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        var mData = data
        if (mData == null) {
            print("Please, enter the key: ")
            mData = readLine()!!
        }
        if (myMap.contains(mData))
            myMap.printPair(mData)
        else
            println("I haven't  this key!")
    }
}

class HandlerStop : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "stop" || command.toLowerCase() == "stop"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        if (getAssent("Seems like I have some data. Write it to disk?")) {
            var mData: String? = null
            if (mData == null) {
                print("Please, enter filename (empty line for \"data.txt\"): ")
                mData = readLine()!!
            }
            if (mData.isEmpty())
                myMap.write()
            else
                myMap.write(mData)
        }
        //myMap.write()
        println("Goodbye...")
    }
}

class HandlerShowShort : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.contains(" = ") && !myMap.contains(command)
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        val sepPlace = data!!.indexOf(" = ")
        val key = data.substring(0, sepPlace)
        val value = data.substring(sepPlace + " = ".length)
        if (myMap.contains(key)) {
            myMap.set(key, value)
            println("set successfully")
        } else
            println("I haven't this key")
    }
}

class HandlerWrite : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean {
        return command.toLowerCase() == "write"
    }

    override fun onMatch(data: String?, myMap: NewMap) {
        var mData = data
        if (mData == null) {
            print("Please, enter filename (empty line for \"data.txt\"): ")
            mData = readLine()!!
        }

        if (mData.isEmpty())
            myMap.write()
        else
            myMap.write(mData)
        println("Written successfully!")
    }
}

class HandlerDefault : HandlerBase() {
    override fun isMatch(command: String, myMap: NewMap): Boolean = true

    override fun onMatch(data: String?, myMap: NewMap) {
        if (myMap.contains(data!!))
            myMap.printPair(data)
        else
            println("I don't know this command or key\n${toBoldString("help")} for list of available details")
    }
}
