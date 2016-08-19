package glimpse.api

import io.kotlintest.properties.Gen
import io.kotlintest.specs.WordSpec

class MatrixSpec : WordSpec() {

	companion object {
		private val delta = 0.01f
		private val m = Matrix((1..16).map { it.toFloat() })
		val integers = Gen.choose(1, 100)
		val matrices: Gen<Matrix> = object : Gen<Matrix> {
			override fun generate(): Matrix = Matrix((0..15).map { integers.generate().toFloat() }.toList())
		}
	}

	init {

		"String representation of a matrix" should {
			"be properly a formatted matrix of numbers" {
				m.toString() shouldBe """|
						||     1.00     5.00     9.00    13.00 |
						||     2.00     6.00    10.00    14.00 |
						||     3.00     7.00    11.00    15.00 |
						||     4.00     8.00    12.00    16.00 |
						|""".trimMargin()
			}
		}

		"Matrix multiplication" should {
			"return the original matrix when multiplied by identity matrix" {
				m * Matrix.IDENTITY shouldBe m
			}
			"return the original matrix when identity matrix multiplied by it" {
				Matrix.IDENTITY * m shouldBe m
			}
			"be correctly calculated for two matrices" {
				m * Matrix((21..36).map { it.toFloat() }) shouldBe
						Matrix(listOf(
								650.0f, 740.0f, 830.0f, 920.0f,
								762.0f, 868.0f, 974.0f, 1080.0f,
								874.0f, 996.0f, 1118.0f, 1240.0f,
								986.0f, 1124.0f, 1262.0f, 1400.0f))
			}
		}

		"Matrix multiplied by vector" should {
			val v = Vector(1f, 2f, 3f)
			"be equal to original vector if the matrix is an identity matrix" {
				Matrix.IDENTITY * v shouldBe v
			}
			"be correctly calculated" {
				m * v shouldBe Vector(51f, 58f, 65f)
			}
		}

		"Matrix transposition" should {
			"return a transposed matrix" {
				m.transpose() shouldBe
						Matrix(listOf(
								1.0f, 5.0f, 9.0f, 13.0f,
								2.0f, 6.0f, 10.0f, 14.0f,
								3.0f, 7.0f, 11.0f, 15.0f,
								4.0f, 8.0f, 12.0f, 16.0f))
			}
		}

		"Inverse matrix" should {
			"be identity matrix for an identity matrix" {
				Matrix.IDENTITY.inverse() shouldBeRoughly Matrix.IDENTITY
			}
			"give identity matrix when multiplied by the original matrix" {
				forAll(matrices) { matrix ->
					matrix * matrix.inverse() isRoughly Matrix.IDENTITY
				}
			}
		}
	}

	private infix fun Float.isRoughly(other: Float) =
			Math.abs(other - this) < delta

	private infix fun Matrix.isRoughly(other: Matrix) =
			(0..3).all { row ->
				(0..3).all { col ->
					this[row, col] isRoughly other[row, col]
				}
			}

	private infix fun Matrix.shouldBeRoughly(other: Matrix) {
		this isRoughly other shouldBe true
	}

}