package com.proAndroid.todoapp

data class MenuOrder(
    val nameOfFood : String,
    val quantity: Int
)

class MenuOrderClass(
    val nameOfFood : String,
    val quantity: Int
)

// designed them to be immutable
val menuOrder = MenuOrder("pizza", 1)
val menuOrderDuplicate = MenuOrder("pizza", 1)

val menuOrderClass = MenuOrderClass("pizzaClass", 1)
val menuOrderClassDuplicate = MenuOrderClass("pizzaClass", 1)

fun main(){

    val newMenuOrder = menuOrder.copy(quantity = 3) // can't do menuOrderClass.copy()
    println(menuOrder) //shows the properties value
    println(newMenuOrder) //shows the properties value with quantity =3
    println(menuOrderClass) //shows the address of class

    println("dataclass equals ${menuOrder == menuOrderDuplicate}" ) //true because values are same
    println("regularClass equals ${menuOrderClass == menuOrderClassDuplicate}" ) //false because class addresses are different
}
