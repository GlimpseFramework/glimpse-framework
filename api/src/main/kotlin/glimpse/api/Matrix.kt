package glimpse.api

/**
 * Matrix defining three-dimensional affine transformations.
 */
data class Matrix(private val matrix: List<Float>) {

	companion object {

		/**
		 * Identity matrix.
		 */
		val IDENTITY = Matrix((0..15)
				.map { it.toRowCol() }
				.map {
					val (row, col) = it
					if (row === col) 1f else 0f
				}
		)

		/**
		 * Null matrix.
		 */
		val NULL = Matrix((0..15).map { 0f })

		private fun Int.toRowCol() = Pair(this % 4, this / 4)
	}

	init {
		require(matrix.size === 16) {
			"Matrix must consist of exactly 16 (4×4) elements. ${matrix.size} elements were provided instead."
		}
	}

	internal val _16f : Array<Float> by lazy { matrix.toTypedArray() }

	/**
	 * Returns a string representation of the [Matrix].
	 */
	override fun toString() =
			(0..3).map { row ->
				(0..3).map { col -> this[row, col] }
						.joinToString(separator = " ", prefix = "| ", postfix = " |") { "%8.2f".format(it) }
			}.joinToString(separator = "\n", prefix = "\n", postfix = "\n") { it }

	/**
	 * Gets element of the matrix at the given position.
	 *
	 * @param row Row index.
	 * @param col Column index.
	 */
	operator fun get(row: Int, col: Int) = matrix[col * 4 + row]

	/**
	 * Multiplies this matrix by the [other] matrix.
	 */
	operator fun times(other: Matrix) =
			Matrix((0..15)
					.map { it.toRowCol() }
					.map {
						val (row, col) = it
						(0..3).map { this[row, it] * other[it, col] }.reduce { sum, next -> sum + next }
					}
			)
}
