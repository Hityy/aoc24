package days.seventeen

import kotlin.time.measureTime

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

//Register A: 47006051
//Register B: 0
//Register C: 0
//
//Program: 2,4,1,3,7,5,1,5,0,3,4,3,5,5,3,0

//    val program = parseProgram("2,4,1,3,7,5,1,5,0,3,4,3,5,5,3,0")
//    comboOperands[registerA] = 47006051
//    comboOperands[registerB] = 0
//    comboOperands[registerC] = 0
//    while(instructionPointer < program.size) {
//        val opcode = program[instructionPointer]
//        val operand = program[instructionPointer + 1]
//        val instruction = instructions[opcode]
//        instruction(operand)
//    }
//
//    println(output.joinToString(","))
////    4,6,3,5,6,3,5,2,1,0.

}

fun parseProgram(input: String) = input.split(',').map(String::toInt)//.chunked(2)

val adv = fun (operand: Int) {
    val value = comboOperands[operand] ?: throw Error("Not allowed operand: $operand in adv")
    val numerator = comboOperands[registerA] ?: throw Error("Not allowed value in registerA: $operand")
    val denominator = 2L shl (value.toInt() -1)
    val result = numerator / denominator.coerceAtLeast(1)

    comboOperands[registerA] = result
    instructionPointer += 2
}

val bxl = fun(operand: Int) {
    val B = comboOperands[registerB] ?: throw Error("Not allowed operand: $operand in bst")
    val result = B xor operand.toLong()

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
    if(comboOperands[registerA] == 0L) {
        instructionPointer = 17
        return
    }
    instructionPointer = operand
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

    output += result.toInt()
    instructionPointer += 2
}

val bdv  = fun (operand: Int) {
    val value = comboOperands[operand] ?: throw Error("Not allowed operand: $operand in bdv")
    val numerator = comboOperands[registerA] ?: throw Error("Not allowed value in registerA: $operand")
    val denominator = 2L shl (value.toInt() -1)
    val result = numerator / denominator.coerceAtLeast(1)

    comboOperands[registerB] = result
    instructionPointer += 2
}

val cdv = fun (operand: Int) {
    val value = comboOperands[operand] ?: throw Error("Not allowed operand: $operand in cdv")
    val numerator = comboOperands[registerA] ?: throw Error("Not allowed value in registerA: $operand")
    val denominator = 2L shl (value.toInt() -1)
    val result = numerator / denominator.coerceAtLeast(1)

    comboOperands[registerC] = result
    instructionPointer += 2
}

val output = mutableListOf<Int>()
val instructions = arrayOf(adv,bxl,bst,jnz,bxc,out,bdv,cdv)
var instructionPointer = 0
val comboOperands = arrayOf<Long?>(0,1,2,3,0,0,0,null) // 1, 2, 3 A, B ,C, Error
val registerA = 4
val registerB = 5
val registerC = 6

fun reset() {
    output.clear()
    instructionPointer = 0
    comboOperands[registerA] = 0
    comboOperands[registerB] = 0
    comboOperands[registerC] = 0
}

//    Register A: 117440
//    Register B: 0
//    Register C: 0
//
//    Program: 0,3,5,4,3,0


// robi 6 pętli
// schodzi A do 0 wtedy stop
// w sumie 18 instrukcji a mamy 3 instrukcje więc kazda po 6
// idac od tyłu znamy wynik out kazdej petli z tego mamy liste propozycji dla A
// potem kazda taka propozycje trzeba reveresem sprawdzic czy jest dobra
// brute force
// ??


//fun getIntFromModuleSeq(m: Int, rest: Int) = sequence {
//    var n = 0
//    while(true) {
//        yield(m*n++ + rest)
//    }
//}

// bierzesz pierwsza
// 0 tylko modulo % daje 0, więc A = 0
// robisz inv adv -> musisz znalesc licznik
//  3 = 28/(2^3)
// 3 = 2

// 2,4, 1,3, 7,5, 1,5 ,0,3, 4,3, 5,5 ,3,0
// (bst op4)   (bxl op 3)   (cdv op5)    (bxl op5)   (adv op3)    (bxc op3)   (out op5)     (jnz op0)
// op(4)=A->B  op(3)=3->B   op(5)=B->C   op(5)=5->B  op(3)=3->A  op(3)=3->B    op(5)=B->B    jmp to 0
// --------------
// last
// (5,4) -> out (op4) = 0 -> out = A/8 -> A = 0 (bo na tej liczbie jestesmy )
// wiemy
// (0,3) -> adv (op3) = 0 -> adv = A/2^3 -> A = 3 (bo nastepny sie zaczyna od 3)
//

// "0,3,5,4,3,0"
// (adv op3) -> (out op4) -> (jnz op0)
// 3,0 - 0*8+0 =0  0 = 3/8 -- A = 0
//
// A = prevA % 8
// prevA = A*8 + nextI
// Czy A zalezy od B i C ?


//  denominator = 8, nominator = prevA
//  A = N/D
//  0 = X/8 = 0 1..7
// adv 1 = X/8 -> X -> 8
// adv 8 = X/8 -> X -> 64


//val program = parseProgram("2,4, 1,3, 7,5, 1,5, 0,3, 4,3, 5,5 ,3,0")
// A=X
// 2,4 -> bst A -> X%8 -> writes to B -> B=X%8
// 1,3 -> bxl 3 -> B XOR 3 -> write to B -> B =  (X%8) XOR 3
// 7,5 -> cdv 5 -> trunc(X/2**B) -> write to C -> C = trunc(X/2**B)
// 1,5 -> bxl B -> B XOR 5 -> write to B -> B = B XOR 5 -> ((X%8) XOR 3) XOR 5
// 0,3 -> adv 3 -> trunc(X/2**3) -> write to A = X = trunc(X/2**3)
// 4,3 -> bxc 3 -> B XOR C -> write to B ->  B XOR C -> (((X%8) XOR 3) XOR 5) XOR trunc(X/2**B)
// 5,5 -> out B -> printl B%8
// 3,0 -> jmp 0

// B%8 = 0 -> B = 0
// B XOR C -> B = C = 0, 8, 16, 24 ..
// A = 0 -> 0 = trunc(X/8) A = 0..7 -> A
// B XOR 5 -> 0,8,16,24.. = B XOR 5 -> 5,13,21... -> B
// trunc(X/2**B) -> C = trunc(X/2**B) -> C = trunc(0..7/ 2^(5..13..21..))
//
// too low 16777216
// Int.MAX too loow 2147483647

// wynik to jakaś liczba ktorej modulo
// przejechaliśmy 8 pętli gdzie w kazdej A został podzielony przez 8 az do 0
// ostatni to musialbyc (0..7)

// przedostatni to musialbyc (0..7) = trunc(X/8)
// 1 = X/8 => X = 8 .. 15
// 2 = X/8 => X = 16 .. 23
// ..
// 7 = X/8 => X = 56 .. 63



// What numbers %8 = 2 ?
// for N < 8 N = 2
// for N > 8 N = N*n + 2

// 30 opcode 3 operand 0 jmp,Register A = 0 ends program
// 54 opcode 5 operand 4 -> out, A%8
// 03 opcode 0 operand 3 -> adv, A/2^(3) truncated

// adv operand 3 -> A/2^3 = A/8 = N1 -> A = 8*N1
// N1%8 = 3,0

// out 0 -> X%8 == 0 ->> 0 || 8...
//   ...
// out 3 -> x%8 == 3 ->> 3 || 11 || 19 ...
//   ...
// out 5 -> x%8 == 5 ->> 5 || 13 || 21 || 29...
//   ...
// out 4 -> x%8 == 4 ->> 4 || 12 || 20 || 28 || 36...
//   ...
// out 3 -> x%8 == 3 ->> 3 || 11 || 19 || 27 || 35 || 43...
//   ... adv() --> A = 8
// out 0 -> x%8 == 0 ->> 0 || 8  || 16 || 24 || 32 || 40 || 48...


//    runOnceForSeek(0,0)
// 6
//    runOnceForSeek(6,3)
// 49 53
//    runOnceForSeek(53,5)
// [425, 429, 430]
//    runOnceForSeek(425,5)
// [3401, 3407]
//    runOnceForSeek(3407,3)
// 25214
// 27262
//    runOnceForSeek(27262,4)
// 201717
// 218101
//    runOnceForSeek(218101,3)
// 1613743
// 1744815
//    runOnceForSeek(1744815,0)
// 12909948
// 13958524
//    runOnceForSeek(13958524,5)
// [103279586, 103279588]
// 13958524
//    runOnceForSeek(111668196,1)
// 826236710
// 893345574
//    runOnceForSeek(893345574,5)
// 6609893682
// 7146764594
//    runOnceForSeek(7146764594,7)

val program = parseProgram("2,4,1,3,7,5,1,5,0,3,4,3,5,5,3,0")
fun solveSeventeenDaySecondStar() {
    var result: Long?
    val time = measureTime {
        result = findRecursive(program,0).minOrNull()
    }
    println(time) // 10ms
    println(result)
}

fun findRecursive(program: List<Int>, lastValue: Long): List<Long> {
    if(program.isEmpty()) {
        return listOf(lastValue)
    }
    val seek = program.last()
    val restProgram = program.dropLast(1)
    val matches = runOnceForSeek(lastValue, seek)
    val res = mutableListOf<Long>()
    for(match in matches) {
        res += findRecursive(restProgram, match)
    }
    return res
}

fun runOnceForSeek(prev: Long, seek: Int): List<Long> {
    val seekRange = getSeekRange(prev * 8)
    return runProgramForRegister(seekRange, seek)
}

fun runProgramForRegister(range: LongRange, seek: Int): List<Long> {
    val buffer = mutableListOf<Long>()
    for(i in range) {
        comboOperands[registerA] = i
        comboOperands[registerB] = 0
        comboOperands[registerC] = 0
        while(instructionPointer < program.size) {
            val opcode = program[instructionPointer]
            val operand = program[instructionPointer + 1]
            val instruction = instructions[opcode]
            instruction(operand)
        }

        if(output.first() == seek) {
            buffer += i
        }
        reset()
    }
    return buffer
}

fun getSeekRange(i: Long): LongRange {
    return i..< minNumberModulo8(i + 1)
}

fun minNumberModulo8(i: Long): Long {
    var seek = i;
    while(seek < i + 7) {
        if(seek%8L == 0L) {
            break
        }
        seek++
    }
    return seek
}





