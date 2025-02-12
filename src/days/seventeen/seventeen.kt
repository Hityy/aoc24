package days.seventeen

import kotlin.math.pow


// 3 bit computer
// computer has program listof 3bit numbers : 0,1,2,3,4,5,6,7
// 000..111
// Computer has 3 registers A,B,C -> each register can store integer number
// computer recognies 8 instructions
// each instruction is a 3 bit number opcode
// after each instruction is 3-bit operand


// Pointer of instructions reads opcode
// starts from 0
// instruction of jump , skip
// after each instruction pointer increments by 2
// if pointer tries to read opcode after range program ends

// example: 0,1,2,3 ->
// opcode 0 operator 1
// opcode 2 operator 3
// ends

// operand literalny
// 7 -> 7
// 3 -> 3
// operand kombinowany
// 0-3 -> 0-3
// 4 -> is value of register A
// 5 -> is value of register B
// 6 -> is value of register C
// 7 -> not allowed, error

// 8 instructions
// adv (0) -> division -> A/(operand komb)
// licznik: register A, mianownik : 2^(operand kombi)
// example:
// operand 2, A/(2^2)
// operand 5, A/(2^B)
// result parsed to Int, stored in A
// pointer increases by 2

// bxl (1) -> B XOR operand litaral -> result stored in B
// pointer increases by 2

// bst (2) -> operand komb % 8 -> result stored in B (zachowane najnizsze 3 bity)
// pointer increases by 2

// jnz (3) -> if A == 0 -> Nothing
// if A != 0 -> jump to (operand literal)
// pointer not increases

// bxc (4) -> B XOR C -> result store in B,
// reads operand but ignores it
// pointer increases by 2

// out (5) -> (operand komb) % 8 -> println
// if many values should join by ,
// pointer increases by 2

// bdv (6) -> adv but stores in B
// pointer increases by 2
// cdv (7) -> adv but stores in C
// pointer increases by 2

// EXAMPLES

// Register C=9, program 2,6
// Opcode 2, operand 6 -> Combo operand 6 = reads from C -> C = 9 so:
// bst: 9%8=1 -> save in B
// bst (operand 6) -> bst (C) -> bst( C%8) = 1
// 1

// Register A=10 program: 5,0,5,1,5,4
// Opcode 5 operand 0
// Opcode 5 operand 1
// Opcode 5 operand 4
// out: print
// print (operand 0) -> 0%8 = 0
// print (operand 1) -> 1%8 = 1
// print (operand 4) -> 10%8 = 2
// 0,1,2

// Register B = 29 program: 1,7
// opcode 1 operand 7
// bxl (operand 7) -> B XOR 7 -> 29 XOR 7 -> 26 -> B - 26 pointer 2

// Register B = 2024 C = 43690 program: 4,0
// opcode 4 operand 0
// bxc (operand 0) -> B XOR C -> 2024 XOR 43690 -> B = 44354

// Register A = 2024 program: 0,1,5,4,3,0
// opcode 0 operand 1
// opcode 5 operand 4
// opcode 3 operand 0
// adv (operand 1) 0
// out (operand 4) 2
// jnz (opernad 0) 4
// adv -> 2024 / 2^1 = 1012 -> A = 1012
// out (operand 4) -> A % 8 -> 1012 % 8 = 4 -> print 4,
// jnz -> jump to opcode 0
// adv (operand 1)  -> 1012 / 2^1 = 506 -> A = 506
// out (operand 4) -> A % 8 -> 506 % 8 = 2 -> print 2,
// jnz -> jump to opcode 0
// adv (operand 1)  -> 506 / 2^1 = 253 -> A = 253
// out (operand 4) -> A % 8 -> 253 % 8 = 5 -> print 5,
// jnz -> jump to opcode 0
// adv (operand 1)  -> 253 / 2^1 = 126 -> A = 126
// out (operand 4) -> A % 8 -> 126 % 8 = 6 -> print 6,
// jnz -> jump to opcode 0
// adv (operand 1)  -> 126 / 2^1 = 63 -> A = 63
// out (operand 4) -> A % 8 -> 63 % 8 = 7 -> print 7,
// jnz -> jump to opcode 0
// adv (operand 1)  -> 63 / 2^1 = 63 -> A = 31
// out (operand 4) -> A % 8 -> 31 % 8 = 7 -> print 7,
// jnz -> jump to opcode 0
// adv (operand 1)  -> 31 / 2^1 = 15 -> A = 15
// out (operand 4) -> A % 8 -> 15 % 8 = 7 -> print 7,
// jnz -> jump to opcode 0
// adv (operand 1)  -> 15 / 2^1 = 7 -> A = 7
// out (operand 4) -> A % 8 -> 7 % 8 = 7 -> print 7,
// jnz -> jump to opcode 0
// adv (operand 1)  -> 7 / 2^1 = 3 -> A = 3
// out (operand 4) -> A % 8 -> 3 % 8 = 3 -> print 3,
// jnz -> jump to opcode 0
// adv (operand 1)  -> 3 / 2^1 = 1 -> A = 1
// out (operand 4) -> 1 % 8 -> 1 % 8 = 1 -> print 1,
// jnz -> jump to opcode 0
// adv (operand 1)  -> 1 / 2^1 = 0 -> A = 0
// out (operand 4) -> 0 % 8 -> 0 % 8 = 1 -> print 0,

fun solveSeventeenDayFirstStar() {
    test1()
}

fun test1() {
//    C=9 program 2,6
//    val operand = 6
//    comboOperands[registerC] = 9
//    val instruction = instructions[2]
//    instruction(operand)
//    println(comboOperands[registerB])
// 1


//    A=10 program 5,0,5,1,5,4
//    val program = parseProgram("5,0,5,1,5,4")
//    comboOperands[registerA] = 10
//
//    while(instructionPointer < program.size) {
//        val (opcode,operand) = program[instructionPointer]
//        println("$opcode,$operand")
//        val instruction = instructions[opcode]
//        instruction(operand)
//        instructionPointer++
//    }
//
//    println(output)


////    A=2024 program 0,1,5,4,3,0
//    val program = parseProgram("0,1,5,4,3,0")
//    comboOperands[registerA] = 2024
//    while(instructionPointer < program.size) {
//        val opcode = program[instructionPointer]
//        val operand = program[instructionPointer + 1]
//        println("$opcode,$operand")
//        val instruction = instructions[opcode]
//        instruction(operand)
////        println("pointer $instructionPointer")
////        println("registerA: ${comboOperands[registerA]}")
////        counter++
//    }
//
//    println(output)


//    //    B=29 program 1,7
//    val program = parseProgram("1,7")
//    comboOperands[registerB] = 29
//    while(instructionPointer < program.size) {
//        val opcode = program[instructionPointer]
//        val operand = program[instructionPointer + 1]
//        println("$opcode,$operand")
//        val instruction = instructions[opcode]
//        instruction(operand)
//    }
//
//    println(comboOperands[registerB])

    //    B=2024 C = 43690 program 4,0
//    val program = parseProgram("4,0")
//    comboOperands[registerB] = 2024
//    comboOperands[registerC] = 43690
//    while(instructionPointer < program.size) {
//        val opcode = program[instructionPointer]
//        val operand = program[instructionPointer + 1]
//        println("$opcode,$operand")
//        val instruction = instructions[opcode]
//        instruction(operand)
//    }
//// B to 44354
//    println(comboOperands[registerB])

//    Register A: 729
//    Register B: 0
//    Register C: 0
//
//    Program: 0,1,5,4,3,0

//    val time = measureTime {
//        val program = parseProgram("0,1,5,4,3,0")
//        comboOperands[registerA] = 729
//        comboOperands[registerB] = 0
//        comboOperands[registerC] = 0
//        while(instructionPointer < program.size) {
//            val opcode = program[instructionPointer]
//            val operand = program[instructionPointer + 1]
////            println("$opcode,$operand")
//            val instruction = instructions[opcode]
//            instruction(operand)
//        }
//    }
//    println(time)
//    println(output)
//    println("//    4,6,3,5,6,3,5,2,1,0.")



//    Register A: 47006051
//Register B: 0
//Register C: 0
//
//Program: 2,4,1,3,7,5,1,5,0,3,4,3,5,5,3,0

    val program = parseProgram("2,4,1,3,7,5,1,5,0,3,4,3,5,5,3,0")
    comboOperands[registerA] = 47006051
    comboOperands[registerB] = 0
    comboOperands[registerC] = 0
    while(instructionPointer < program.size) {
        val opcode = program[instructionPointer]
        val operand = program[instructionPointer + 1]
        val instruction = instructions[opcode]
        instruction(operand)
    }

    println(output.joinToString(","))
//    4,6,3,5,6,3,5,2,1,0.

}

fun parseProgram(input: String) = input.split(',').map(String::toInt)//.chunked(2)



val adv = fun (operand: Int) {
    val value = comboOperands[operand] ?: throw Error("Not allowed operand: $operand in adv")
    val numerator = comboOperands[registerA] ?: throw Error("Not allowed value in registerA: $operand")
    val denominator = 2 shl value - 1
    val result = numerator / denominator
//    if(result > Int.MAX_VALUE) throw Error("Not allowed result INT: $result")

//    println("adv $result")
    comboOperands[registerA] = result
    instructionPointer += 2
}

val bxl = fun(operand: Int) {
//    val value = comboOperands[operand] ?: throw Error("Not allowed operand: $operand in bst")
    val B = comboOperands[registerB] ?: throw Error("Not allowed operand: $operand in bst")
    val result = B xor operand
    comboOperands[registerB] = result
    instructionPointer += 2
}

val bst = fun(operand: Int) {
    val value = comboOperands[operand] ?: throw Error("Not allowed operand: $operand in bst")
    val result = value % 8
    comboOperands[registerB] = result
    instructionPointer += 2
}

val jnz = fun(operand: Int) {
    if(comboOperands[registerA] == 0) {
        instructionPointer = 17
        return
    }
    println("jnz = $operand $instructionPointer")
    instructionPointer = operand*2
}

val bxc = fun(operand: Int) {
    val B = comboOperands[registerB] ?: throw Error("Not allowed operand: $operand in bst")
    val C = comboOperands[registerC] ?: throw Error("Not allowed operand: $operand in bst")
    val result = B xor C
    comboOperands[registerB] = result
    instructionPointer += 2
}

val out = fun (operand: Int) {
    val value = comboOperands[operand]  ?: throw Error("Not allowed operand: $operand in out")
    val result = value % 8
    output += result
    instructionPointer += 2
}

val bdv  = fun (operand: Int) {
    val value = comboOperands[operand] ?: throw Error("Not allowed operand: $operand in bdv")
    val numerator = comboOperands[registerA] ?: throw Error("Not allowed value in registerA: $operand")
//    val denominator = 2.0.pow(value.toDouble()).toLong()
    val denominator = 2 shl value - 1
    val result = numerator / denominator
//    if(result > Int.MAX_VALUE) throw Error("Not allowed result INT: $result")

    comboOperands[registerB] = result.toInt()
    instructionPointer += 2
}

val cdv = fun (operand: Int) {
    val value = comboOperands[operand] ?: throw Error("Not allowed operand: $operand in cdv")
    val numerator = comboOperands[registerA] ?: throw Error("Not allowed value in registerA: $operand")
    val denominator = 2.0.pow(value.toDouble()).toLong()
//    val denominator = 2 shl value - 1
    val result = numerator / denominator
//    if(result > Int.MAX_VALUE) throw Error("Not allowed result INT: $result")

    comboOperands[registerC] = result.toInt()
    instructionPointer += 2
}

val output = mutableListOf<Int>()
val instructions = arrayOf(adv,bxl,bst,jnz,bxc,out,bdv,cdv)
var instructionPointer = 0
val comboOperands = arrayOf(0,1,2,3,0,0,0,null) // 1, 2, 3 A, B ,C, Error
val registerA = 4
val registerB = 5
val registerC = 6
val cacheModulo8 = mutableMapOf<Int,Int>()
