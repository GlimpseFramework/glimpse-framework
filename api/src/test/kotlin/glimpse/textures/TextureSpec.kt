package glimpse.textures

import com.nhaarman.mockito_kotlin.verify
import glimpse.gles.GLES
import glimpse.gles.UniformLocation
import glimpse.test.GlimpseSpec

class TextureSpec : GlimpseSpec() {

	init {

		"Applying a texture" should {
			"succeed if texture is not deleted" {
				val glesMock = glesMock()
				val texture = Texture(TextureHandle(1))
				texture.apply(UniformLocation(10), 5)
				verify(glesMock).activeTexture(5)
				verify(glesMock).bindTexture2D(TextureHandle(1))
				verify(glesMock).uniformInt(UniformLocation(10), 5)
			}
			"cause an exception if texture is deleted" {
				glesMock()
				val texture = Texture(TextureHandle(1))
				texture.deleted = true
				shouldThrow<AssertionError> {
					texture.apply(UniformLocation(10), 5)
				}
			}
		}

		"Deleting a texture" should {
			"succeed" {
				glesMock()
				val texture = Texture(TextureHandle(1))
				texture.delete()
				texture.deleted shouldBe true
			}
			"delete texture in GLES implementation" {
				val glesMock = glesMock()
				val texture = Texture(TextureHandle(1))
				texture.delete()
				verify(glesMock).deleteTexture(TextureHandle(1))
			}
		}

	}

	private fun glesMock(): GLES = glesMock {}
}