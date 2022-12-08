import java.io.File

fun main(args: Array<String>) {
    println("AOC 2022, Day 7, Part 1 starting!!!")

    val fileSystem = FileSystem()

    File(args[0]).forEachLine {
        processLine(it, fileSystem)
    }

    fileSystem.dumpFileSystem()

    val answer = fileSystem.calculateAnswer(100000)
    println("Answer: $answer")
    if(answer != 1490523L) {
        println("ERROR DID NOT WORK")
    }

    println("AOC 2022, Day 7, Part 1 completed!!")
}

fun processLine(line:String, fileSystem: FileSystem) {
    if(line[0] == '$') {
        val command = line.substring(2, 4)
        // command == "ls" does nothing
        if(command == "cd") {
            val param = line.substring(5)
            fileSystem.changeDirectory(param)
        }
    } else {
        // Is this line a NNN FileName
        if(line[0].isDigit()) {
            val splitLine = line.split(' ')
            val fileSize = splitLine[0].toLong()
            val fileName = splitLine[1]
            fileSystem.addFile(MyFileData(fileName, fileSize, MyDirectoryData(fileSystem.currentDirectory)))
        } else {
            val dirName = line.substring(4)
            fileSystem.addDirectory(MyDirectoryData(dirName))
        }
    }
}

abstract class MyFileObject {
    abstract val objectName:String
    abstract val objectType:String
}
data class MyDirectoryData (
    override val objectName:String
) : MyFileObject() {
    override val objectType: String
        get() = "directory"
}

data class MyFileData (
    override val objectName: String,
    val fileSize:Long,
    val directory:MyDirectoryData): MyFileObject() {
    override val objectType: String
        get() = "file"
}

class FileSystem {
    var currentDirectory: String = "/"
    private val files:HashMap<MyFileData,MyDirectoryData> = HashMap()
    private val directories:HashMap<MyDirectoryData,MyDirectoryData> = HashMap()

    fun changeDirectory(newDirectory: String) {
        //println("tlarsen,L66: Start requested $newDirectory -> changing directory from $currentDirectory")
        if (newDirectory == "/") {
            currentDirectory = newDirectory
            //println("tlarsen,L69: requested $newDirectory -> changing directory from $currentDirectory to $newDirectory")
            return
        }
        if(newDirectory.compareTo("..") == 0) {
            // move up one directory
            val directories = currentDirectory.split('/')
            var tempDirectory = ""

            for(i in 0 until directories.lastIndex) {
                if(directories[i].isEmpty()) {
                    continue
                } else {
                    tempDirectory += "/${directories[i]}"
                }
            }

            if(tempDirectory.isEmpty()) {
                tempDirectory = "/"
                //throw Exception("tempDirectory is empty! currentDirectory = $currentDirectory, newDirectory = $newDirectory, directories = $directories")
                //println("tlarsen,L96: tempDirectory was empty! currentDirectory = $currentDirectory, newDirectory = $newDirectory, directories = $directories\"")
            }
            //println("tlarsen,L89: requested $newDirectory -> changing directory from $currentDirectory to $tempDirectory")
            currentDirectory = tempDirectory
        } else {

            //val oldCurrentDirectory = currentDirectory

            if ((currentDirectory == "/") || (currentDirectory.isEmpty())) {
                currentDirectory = "/$newDirectory"
            } else {
                currentDirectory += "/$newDirectory"
            }
            //println("tlarsen,L101: requested $newDirectory -> changing directory from $oldCurrentDirectory to $currentDirectory")
        }
    }

    fun addFile(file:MyFileData) {
        files[file] = MyDirectoryData(currentDirectory)
    }

    fun addDirectory(directory: MyDirectoryData) {
        directories[directory] = MyDirectoryData(currentDirectory)
    }

    fun dumpFileSystem() {
        println("Dumping file system: ${files.size} Files, ${directories.size} Directories:")

        println("****files: ")
        for(file in files) {
            //println("  File: ${file.value.objectName}/${file.key.objectName}, size: ${file.key.fileSize}")
            println("${file.value.objectName}/${file.key.objectName} ${file.key.fileSize}")
        }
    }
    fun calculateAnswer(atMost: Int):Long {
        val answerHashMap:HashMap<String,Long> = HashMap()
        answerHashMap.put("/", 0)

        for(directory in directories) {
            if(directory.value.objectName.compareTo("/") != 0) {
                answerHashMap.put("${directory.value.objectName}/${directory.key.objectName}", 0)
            } else {
                //println("tlarsen,L137: ${directory.key.objectName} and ${directory.value.objectName}")
                answerHashMap.put("/${directory.key.objectName}", 0)
            }
        }

        for(file in files) {
            val splitDirectory = file.value.objectName.split("/")

            var theDirectory = "/"
            for(directory in splitDirectory) {
                if(directory.isEmpty())  {
                    theDirectory = "/"
                } else {
                    theDirectory += "/$directory"
                }

                if(theDirectory.startsWith("//")) {
                    theDirectory = "/$directory"
                }

                //var dirTotal = answerHashMap[theDirectory]!!
                var dirTotal = answerHashMap.getOrDefault(theDirectory, 0)
                dirTotal += file.key.fileSize
                answerHashMap[theDirectory] = dirTotal
            }
        }

        return answerHashMap.filter { it.value < atMost }.values.sum()
    }
}