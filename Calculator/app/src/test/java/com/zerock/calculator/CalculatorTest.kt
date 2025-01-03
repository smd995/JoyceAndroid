package com.zerock.calculator

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class CalculatorTest {

    private lateinit var calculator: Calculator

    // 테스트가 실행되기 전에 Calculator 객체를 초기화
    @Before
    fun setUp() {
        calculator = Calculator()
    }

    // 덧셈 테스트
    @Test
    fun testAdd() {
        val result = calculator.add(3, 4)
        assertEquals(7, result)
    }

    // 뺄셈 테스트
    @Test
    fun testSubtract() {
        val result = calculator.subtract(10, 4)
        assertEquals(6, result)
    }

    // 덧셈 테스트: 음수 결과
    @Test
    fun testAddNegativeResult() {
        val result = calculator.add(-3, -7)
        assertEquals(-10, result)
    }

    // 뺄셈 테스트: 음수 결과
    @Test
    fun testSubtractNegativeResult() {
        val result = calculator.subtract(-3, 5)
        assertEquals(-8, result)
    }

    // 0 더하기 테스트
    @Test
    fun testAddZero() {
        val result = calculator.add(0, 5)
        assertEquals(5, result)
    }

    // 0 빼기 테스트
    @Test
    fun testSubtractZero() {
        val result = calculator.subtract(0, 5)
        assertEquals(-5, result)
    }
}