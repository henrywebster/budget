package info.henrywebster.budget.ui

class UIParser() {

//    fun parse(s: InputStream) {
//        BufferedReader(InputStreamReader(s)).use {
//
//            var line = it.readLine()
//            while(line != null) {
//                println(line)
//                line = it.readLine()
//            }
//        }
//    }

    // TODO(kotlinify): use the when clause, stop repeating so many args

    fun parse(s: String) : UIBundle {
        val strings = s.split(" ")

        if (strings[0].equals("add"))
            return UIBundle(UIToken.ADD, strings.subList(1, strings.size).toList())
        else if(strings[0].equals("remove"))
            return UIBundle(UIToken.REMOVE, strings.subList(1, strings.size).toList())
        else
            throw IllegalArgumentException("Not proper keyword: " + s[0])
    }
}