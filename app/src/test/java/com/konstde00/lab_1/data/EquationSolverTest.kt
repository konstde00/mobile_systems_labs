package com.konstde00.lab_1.data

import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.ExpectedException

class EquationSolverTest {
    
    @get:Rule
    val thrown: ExpectedException = ExpectedException.none()

    @Test
    fun testSolveByMatrixMethod_2x2_UniqueSolution() {

        val coeffs = listOf(
            2.0, 3.0, 13.0,   // Equation 1: 2x + 3y = 13
            4.0, -1.0, 5.0    // Equation 2: 4x - y = 5
        )
        val numberOfEquations = 2
        
        val result = EquationSolver.solveByMatrixMethod(coeffs, numberOfEquations)
        
        assertTrue("Result should be a Pair for 2x2 systems.", result is Pair<*, *>)
        val (x, y) = result as Pair<Double, Double>
        assertEquals("x should be 2.0", 2.0, x, 1e-6)
        assertEquals("y should be 3.0", 3.0, y, 1e-6)
    }

    @Test
    fun testSolveByMatrixMethod_2x2_NoUniqueSolution() {
       
        val coeffs = listOf(
            1.0, 2.0, 3.0,    // Equation 1: x + 2y = 3
            2.0, 4.0, 6.0     // Equation 2: 2x + 4y = 6 (Linearly dependent)
        )
        val numberOfEquations = 2

        thrown.expect(Exception::class.java)
        thrown.expectMessage("No unique solution exists.")

        EquationSolver.solveByMatrixMethod(coeffs, numberOfEquations)
    }

    @Test
    fun testSolveByMatrixMethod_3x3_UniqueSolution() {
       
        val coeffs = listOf(
            1.0, 1.0, 1.0, 6.0,    // Equation 1: x + y + z = 6
            2.0, 5.0, 1.0, 16.0,   // Equation 2: 2x + 5y + z = 16
            3.0, 1.0, 4.0, 19.0    // Equation 3: 3x + y + 4z = 19
        )
        val numberOfEquations = 3

        val result = EquationSolver.solveByMatrixMethod(coeffs, numberOfEquations)

        assertTrue("Result should be a Triple for 3x3 systems.", result is Triple<*, *, *>)
        val (x, y, z) = result as Triple<Double, Double, Double>
        assertEquals("x should be -10.0", -10.0, x, 1e-6)
        assertEquals("y should be 5.0", 5.0, y, 1e-6)
        assertEquals("z should be 11.0", 11.0, z, 1e-6)
    }

    @Test
    fun testSolveByMatrixMethod_3x3_NoUniqueSolution() {
       
        val coeffs = listOf(
            1.0, 2.0, 3.0, 4.0,    // Equation 1: x + 2y + 3z = 4
            2.0, 4.0, 6.0, 8.0,    // Equation 2: 2x + 4y + 6z = 8 (Linearly dependent)
            3.0, 6.0, 9.0, 12.0    // Equation 3: 3x + 6y + 9z = 12 (Linearly dependent)
        )
        val numberOfEquations = 3

        thrown.expect(Exception::class.java)
        thrown.expectMessage("No unique solution exists.")

        EquationSolver.solveByMatrixMethod(coeffs, numberOfEquations)
    }

    @Test
    fun testSolveByMatrixMethod_3x3_FractionalSolution() {
       
        val coeffs = listOf(
            2.0, 0.0, 1.0, 4.0,    // Equation 1: 2x + 0y + z = 4
            1.0, 1.0, 0.0, 3.5,    // Equation 2: x + y + 0z = 3.5
            0.0, 1.0, 1.0, 3.0     // Equation 3: 0x + y + z = 3
        )
        val numberOfEquations = 3

        val result = EquationSolver.solveByMatrixMethod(coeffs, numberOfEquations)

        assertTrue("Result should be a Triple for 3x3 systems.", result is Triple<*, *, *>)
        val (x, y, z) = result as Triple<Double, Double, Double>
        assertEquals("x should be 1.5", 1.5, x, 1e-6)
        assertEquals("y should be 2.0", 2.0, y, 1e-6)
        assertEquals("z should be 1.0", 1.0, z, 1e-6)
    }

    @Test
    fun testSolveByGaussMethod_2x2_UniqueSolution() {
       
        val coeffs = listOf(
            2.0, 3.0, 13.0,   // Equation 1: 2x + 3y = 13
            4.0, -1.0, 5.0    // Equation 2: 4x - y = 5
        )
        val numberOfEquations = 2

        val result = EquationSolver.solveByGaussMethod(coeffs, numberOfEquations)

        assertTrue("Result should be a Pair for 2x2 systems.", result is Pair<*, *>)
        val (x, y) = result as Pair<Double, Double>
        assertEquals("x should be 2.0", 2.0, x, 1e-6)
        assertEquals("y should be 3.0", 3.0, y, 1e-6)
    }

    @Test
    fun testSolveByGaussMethod_2x2_NoUniqueSolution() {
       
        val coeffs = listOf(
            1.0, 2.0, 3.0,    // Equation 1: x + 2y = 3
            2.0, 4.0, 6.0     // Equation 2: 2x + 4y = 6 (Linearly dependent)
        )
        val numberOfEquations = 2


        thrown.expect(Exception::class.java)
        thrown.expectMessage("No unique solution exists.")

        EquationSolver.solveByGaussMethod(coeffs, numberOfEquations)
    }

    @Test
    fun testSolveByGaussMethod_3x3_UniqueSolution() {
       
        val coeffs = listOf(
            1.0, 1.0, 1.0, 6.0,    // Equation 1: x + y + z = 6
            2.0, 5.0, 1.0, 16.0,   // Equation 2: 2x + 5y + z = 16
            3.0, 1.0, 4.0, 19.0    // Equation 3: 3x + y + 4z = 19
        )
        val numberOfEquations = 3

        val result = EquationSolver.solveByGaussMethod(coeffs, numberOfEquations)

        assertTrue("Result should be a Triple for 3x3 systems.", result is Triple<*, *, *>)
        val (x, y, z) = result as Triple<Double, Double, Double>
        assertEquals("x should be -10.0", -10.0, x, 1e-6)
        assertEquals("y should be 5.0", 5.0, y, 1e-6)
        assertEquals("z should be 11.0", 11.0, z, 1e-6)
    }

    @Test
    fun testSolveByGaussMethod_3x3_NoUniqueSolution() {
       
        val coeffs = listOf(
            1.0, 2.0, 3.0, 4.0,    // Equation 1: x + 2y + 3z = 4
            2.0, 4.0, 6.0, 8.0,    // Equation 2: 2x + 4y + 6z = 8 (Linearly dependent)
            3.0, 6.0, 9.0, 12.0    // Equation 3: 3x + 6y + 9z = 12 (Linearly dependent)
        )
        val numberOfEquations = 3

        thrown.expect(Exception::class.java)
        thrown.expectMessage("No unique solution exists.")

        EquationSolver.solveByGaussMethod(coeffs, numberOfEquations)
    }

    @Test
    fun testSolveByGaussMethod_3x3_FractionalSolution() {
       
        val coeffs = listOf(
            2.0, 0.0, 1.0, 4.0,    // Equation 1: 2x + 0y + z = 4
            1.0, 1.0, 0.0, 3.5,    // Equation 2: x + y + 0z = 3.5
            0.0, 1.0, 1.0, 3.0     // Equation 3: 0x + y + z = 3
        )
        val numberOfEquations = 3

        val result = EquationSolver.solveByGaussMethod(coeffs, numberOfEquations)

        assertTrue("Result should be a Triple for 3x3 systems.", result is Triple<*, *, *>)
        val (x, y, z) = result as Triple<Double, Double, Double>
        assertEquals("x should be 1.5", 1.5, x, 1e-6)
        assertEquals("y should be 2.0", 2.0, y, 1e-6)
        assertEquals("z should be 1.0", 1.0, z, 1e-6)
    }
}
