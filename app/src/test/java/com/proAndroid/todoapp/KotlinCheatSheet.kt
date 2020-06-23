package com.proAndroid.todoapp


// DEFINE VARIABLE
var number = 5 // type inference: INT
var string = "someString"
var float = 5F
/**********************************************************************/


// EXPLICIT VARIABLE TYPE
var explicitNumber : Int = 5
var explicitString : String = "someString"
var explicitFloat : Float = 5F
/**********************************************************************/


// CONSTANT, CAN'T MUTATE
val item = 1

fun tryMutating() {
//    item = 5 // is an error
}
/**********************************************************************/


// you can have main directly outside the class
fun main(){
    println("hello world!")

    // is illegal to mutate the value, because it is constant
    // item = 6 // error
}
/**********************************************************************/


// function with return type as int
fun functionWithReturn(param: Int): Int {
    return 5
}

// also this is legal, this basically returns the someString
fun functionWithReturn(param: String) = "someString " + param
/**********************************************************************/


// CLASS
// same file can have multiple classes
class AnimalExample1  { // note name is different than the class
    val name = "animal name"
}


// CLASS WITH CONSTRUCTOR
class AnimalExample2  { // note name is different than the class
    private var name: String

    constructor(name: String) {
        this.name = name
    }
}

// CLASS WITH CONSTRUCTOR
open class Animal(private val param: String) {

}
/***************************************************************/

// EXTENDING BASE CLASS
class Dog(name: String) : Animal(name) {

    // secondary constructor
    constructor() : this("default") {

    }
}

/***********************************/
//DATA CLASS, we will talk about this more
data class AnimalDataClass(
    val name: String,
    val age: Int
)
/****************************************************/

// EXTENSION METHODS, you can operate within the string. You don't have to extend the string,
// which is not possible anyway
fun String.doubleString(): String {
    return this + this
}


fun testString(){
    // using extension function
    println("aTry".doubleString())
}

/****************************************/
interface Phone {
    fun getName(): String
}

class PhoneImpl: Phone {
    override fun getName(): String {
        return "somePhone"
    }
}

/********************************************/

// NULL SAFETY
fun nullSafety1(){
    // ensure that it is never null
    var neverNullString = "neverNullString"
    // neverNullString = null //invalid compile time error

    // it may not may not be null
    var doubtfulString : String? // can be null
    doubtfulString  = "not null now"// now its not null
    doubtfulString.doubleString()
    doubtfulString = null // is valid

    doubtfulString?.doubleString() //run only if it not null
    doubtfulString!!.doubleString() //run no mattter if it is null
}

/*****************/
fun immutableList(){
    val immutableList = listOf(1, 2, 3, 4) //creates a list of 4 ints
//    immutableList.add //error
}

fun mutableList(){
    val mutableList = mutableListOf(1,2,3,4)
    mutableList.add(5)
}

fun hashMap(){
    val idMapName = hashMapOf(
        1 to "nameA",
        2 to "nameB"
    )

    idMapName[3] = "name3"
}


fun array(){
    val array = arrayOf(1,2,3,4)
}

/**********************/
fun listOperator(){
    val immutableList = listOf(1,2,3,4,5,6,7,8,9, 10)

    // multiply by 3
    var newList = immutableList.map { eachNumber -> eachNumber * 3 }
    newList =  immutableList.map { it * 3 } // also same

    // filter less than 15
    val lessThan15 = newList.filter { it < 15 }

    // also same
    immutableList.map { it * 3 }
        .filter { it < 15 }

}


/*******************************/
fun testFunction(test: String) {

}

fun callFunctionAsParam(){
    callFunctionAsParam(testFunction)
}

fun callFunctionAsParam(testFunction: (String) -> Any) {

}

