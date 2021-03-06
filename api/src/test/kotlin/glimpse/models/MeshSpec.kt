package glimpse.models

import glimpse.Point
import glimpse.TextureCoordinates
import glimpse.Vector
import glimpse.buffers.toList
import glimpse.test.GlimpseSpec

class MeshSpec : GlimpseSpec() {

	companion object {
		val positions = listOf(
				Point(1f, 2f, 3f), Point(4f, 5f, 6f), Point(7f, 8f, 9f),
				Point(11f, 12f, 13f), Point(14f, 15f, 16f), Point(17f, 18f, 19f),
				Point(21f, 22f, 23f), Point(24f, 25f, 26f), Point(27f, 28f, 29f))
		val textureCoordinates = listOf(
				TextureCoordinates(1f, 2f), TextureCoordinates(3f, 4f), TextureCoordinates(5f, 6f),
				TextureCoordinates(11f, 12f), TextureCoordinates(13f, 14f), TextureCoordinates(15f, 16f),
				TextureCoordinates(21f, 22f), TextureCoordinates(23f, 24f), TextureCoordinates(25f, 26f))
		val normals = listOf(
				Vector(1f, 2f, 3f), Vector(4f, 5f, 6f), Vector(7f, 8f, 9f),
				Vector(11f, 12f, 13f), Vector(14f, 15f, 16f), Vector(17f, 18f, 19f),
				Vector(21f, 22f, 23f), Vector(24f, 25f, 26f), Vector(27f, 28f, 29f))
	}

	init {

		"Initializing a mesh with a list of positions of a wrong size" should {
			"cause an exception" {
				shouldThrow<IllegalArgumentException> {
					val positions = listOf(Point(1f, 2f, 3f), Point(4f, 5f, 6f))
					val textureCoordinates = listOf(TextureCoordinates(1f, 2f), TextureCoordinates(3f, 4f))
					val normals = listOf(Vector(1f, 2f, 3f), Vector(4f, 5f, 6f))
					Mesh(positions, textureCoordinates, normals)
				}
			}
		}

		"Initializing a mesh with a list of texture coordinates of a wrong size" should {
			"cause an exception" {
				shouldThrow<IllegalArgumentException> {
					val textureCoordinates = listOf(TextureCoordinates(1f, 2f), TextureCoordinates(3f, 4f), TextureCoordinates(5f, 6f))
					Mesh(positions, textureCoordinates, normals)
				}
			}
		}

		"Initializing a mesh with a list of normals of a wrong size" should {
			"cause an exception" {
				shouldThrow<IllegalArgumentException> {
					val normals = listOf(Vector(1f, 2f, 3f), Vector(4f, 5f, 6f), Vector(7f, 8f, 9f))
					Mesh(positions, textureCoordinates, normals)
				}
			}
		}

		"Vertices of a mesh" should {
			"should have a correct size" {
				Mesh(positions, textureCoordinates, normals).vertices should haveSize(9)
			}
			"should have a correct values" {
				Mesh(positions, textureCoordinates, normals).vertices shouldBe listOf(
						Vertex(Point(1f, 2f, 3f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)),
						Vertex(Point(4f, 5f, 6f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)),
						Vertex(Point(7f, 8f, 9f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f)),
						Vertex(Point(11f, 12f, 13f), TextureCoordinates(11f, 12f), Vector(11f, 12f, 13f)),
						Vertex(Point(14f, 15f, 16f), TextureCoordinates(13f, 14f), Vector(14f, 15f, 16f)),
						Vertex(Point(17f, 18f, 19f), TextureCoordinates(15f, 16f), Vector(17f, 18f, 19f)),
						Vertex(Point(21f, 22f, 23f), TextureCoordinates(21f, 22f), Vector(21f, 22f, 23f)),
						Vertex(Point(24f, 25f, 26f), TextureCoordinates(23f, 24f), Vector(24f, 25f, 26f)),
						Vertex(Point(27f, 28f, 29f), TextureCoordinates(25f, 26f), Vector(27f, 28f, 29f)))
			}
		}

		"Faces of a mesh" should {
			"should have a correct size" {
				Mesh(positions, textureCoordinates, normals).faces should haveSize(3)
			}
			"should have a correct values" {
				Mesh(positions, textureCoordinates, normals).faces shouldBe listOf(
						Face(
								Vertex(Point(1f, 2f, 3f), TextureCoordinates(1f, 2f), Vector(1f, 2f, 3f)),
								Vertex(Point(4f, 5f, 6f), TextureCoordinates(3f, 4f), Vector(4f, 5f, 6f)),
								Vertex(Point(7f, 8f, 9f), TextureCoordinates(5f, 6f), Vector(7f, 8f, 9f))),
						Face(
								Vertex(Point(11f, 12f, 13f), TextureCoordinates(11f, 12f), Vector(11f, 12f, 13f)),
								Vertex(Point(14f, 15f, 16f), TextureCoordinates(13f, 14f), Vector(14f, 15f, 16f)),
								Vertex(Point(17f, 18f, 19f), TextureCoordinates(15f, 16f), Vector(17f, 18f, 19f))),
						Face(
								Vertex(Point(21f, 22f, 23f), TextureCoordinates(21f, 22f), Vector(21f, 22f, 23f)),
								Vertex(Point(24f, 25f, 26f), TextureCoordinates(23f, 24f), Vector(24f, 25f, 26f)),
								Vertex(Point(27f, 28f, 29f), TextureCoordinates(25f, 26f), Vector(27f, 28f, 29f))))
			}
		}

		"Positions buffer" should {
			"be a direct buffer" {
				Mesh(positions, textureCoordinates, normals).positionsBuffer.isDirect shouldBe true
			}
			"have correct size" {
				Mesh(positions, textureCoordinates, normals).positionsBuffer.capacity() shouldBe 27
			}
			"contain correct values" {
				Mesh(positions, textureCoordinates, normals).positionsBuffer.toList() shouldBe listOf(
						1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f,
						11f, 12f, 13f, 14f, 15f, 16f, 17f, 18f, 19f,
						21f, 22f, 23f, 24f, 25f, 26f, 27f, 28f, 29f)
			}
		}

		"Augmented positions buffer" should {
			"be a direct buffer" {
				Mesh(positions, textureCoordinates, normals).augmentedPositionsBuffer.isDirect shouldBe true
			}
			"have correct size" {
				Mesh(positions, textureCoordinates, normals).augmentedPositionsBuffer.capacity() shouldBe 36
			}
			"contain correct values" {
				Mesh(positions, textureCoordinates, normals).augmentedPositionsBuffer.toList() shouldBe listOf(
						1f, 2f, 3f, 1f, 4f, 5f, 6f, 1f, 7f, 8f, 9f, 1f,
						11f, 12f, 13f, 1f, 14f, 15f, 16f, 1f, 17f, 18f, 19f, 1f,
						21f, 22f, 23f, 1f, 24f, 25f, 26f, 1f, 27f, 28f, 29f, 1f)
			}
		}

		"Texture coordinates buffer" should {
			"be a direct buffer" {
				Mesh(positions, textureCoordinates, normals).textureCoordinatesBuffer.isDirect shouldBe true
			}
			"have correct size" {
				Mesh(positions, textureCoordinates, normals).textureCoordinatesBuffer.capacity() shouldBe 18
			}
			"contain correct values" {
				Mesh(positions, textureCoordinates, normals).textureCoordinatesBuffer.toList() shouldBe listOf(
						1f, 2f, 3f, 4f, 5f, 6f,
						11f, 12f, 13f, 14f, 15f, 16f,
						21f, 22f, 23f, 24f, 25f, 26f)
			}
		}

		"Normals buffer" should {
			"be a direct buffer" {
				Mesh(positions, textureCoordinates, normals).normalsBuffer.isDirect shouldBe true
			}
			"have correct size" {
				Mesh(positions, textureCoordinates, normals).normalsBuffer.capacity() shouldBe 27
			}
			"contain correct values" {
				Mesh(positions, textureCoordinates, normals).normalsBuffer.toList() shouldBe listOf(
						1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f,
						11f, 12f, 13f, 14f, 15f, 16f, 17f, 18f, 19f,
						21f, 22f, 23f, 24f, 25f, 26f, 27f, 28f, 29f)
			}
		}

		"Augmented normals buffer" should {
			"be a direct buffer" {
				Mesh(positions, textureCoordinates, normals).augmentedNormalsBuffer.isDirect shouldBe true
			}
			"have correct size" {
				Mesh(positions, textureCoordinates, normals).augmentedNormalsBuffer.capacity() shouldBe 36
			}
			"contain correct values" {
				Mesh(positions, textureCoordinates, normals).augmentedNormalsBuffer.toList() shouldBe listOf(
						1f, 2f, 3f, 1f, 4f, 5f, 6f, 1f, 7f, 8f, 9f, 1f,
						11f, 12f, 13f, 1f, 14f, 15f, 16f, 1f, 17f, 18f, 19f, 1f,
						21f, 22f, 23f, 1f, 24f, 25f, 26f, 1f, 27f, 28f, 29f, 1f)
			}
		}

	}
}
