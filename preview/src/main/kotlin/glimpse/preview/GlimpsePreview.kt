package glimpse.preview

import glimpse.Color
import glimpse.Vector
import glimpse.cameras.camera
import glimpse.cameras.perspective
import glimpse.cameras.targeted
import glimpse.degrees
import glimpse.gles.BlendFactor
import glimpse.gles.DepthTestFunction
import glimpse.io.resource
import glimpse.jogl.glimpseFrame
import glimpse.jogl.menu
import glimpse.jogl.menuBar
import glimpse.jogl.menuItem
import glimpse.materials.Textured
import glimpse.models.sphere
import glimpse.textures.Texture
import glimpse.textures.TextureMagnificationFilter
import glimpse.textures.TextureMinificationFilter
import glimpse.textures.readTexture
import java.util.*

fun main(args: Array<String>) {

	var aspect: Float = 1.333f

	val camera = camera {
		targeted {
			position { Vector(5f, 60.degrees, 0.degrees).toPoint() }
		}
		perspective {
			fov { 30.degrees }
			aspect { aspect }
			distanceRange(1f to 20f)
		}
	}

	val model = sphere(12).transform {
		val time = (Date().time / 50L) % 360L
		rotateZ(time.degrees)
		rotateX(-23.5.degrees)
	}

	val textures = mutableMapOf<Textured.TextureType, Texture>()

	val material = Textured { textureType -> textures[textureType]!! }

	glimpseFrame("Glimpse Framework Preview") {
		menuBar {
			menu("Mesh") {
				menuItem("Sphere") {
				}
				menuItem("Cube") {
				}
			}
			menu("Shader") {
				menuItem("Plain") {
				}
				menuItem("Textured") {
				}
			}
			menu("Textures") {
				menuItem("Ambient…") {
				}
				menuItem("Diffuse…") {
				}
				menuItem("Specular…") {
				}
				menuItem("Normal map…") {
				}
			}
			menu("Lenses") {
				menuItem("Frustum") {
				}
				menuItem("Perspective") {
				}
				menuItem("Orthographic") {
				}
			}
		}
		onInit {
			Textured.init(this)
			clearColor = Color.BLACK
			clearDepth = 1f
			isDepthTest = true
			depthTestFunction = DepthTestFunction.LESS_OR_EQUAL
			isBlend = true
			blendFunction = BlendFactor.SRC_ALPHA to BlendFactor.ONE_MINUS_SRC_ALPHA
			isCullFace = false
			textureMagnificationFilter = TextureMagnificationFilter.LINEAR
			textureMinificationFilter = TextureMinificationFilter.LINEAR_MIPMAP_LINEAR
			textures[Textured.TextureType.AMBIENT] = Context.resource("ambient.png").readTexture(this) { withMipmap() }
			textures[Textured.TextureType.SPECULAR] = Context.resource("specular.png").readTexture(this) { withMipmap() }
			textures[Textured.TextureType.DIFFUSE] = Context.resource("diffuse.png").readTexture(this) { withMipmap() }
		}
		onResize { v ->
			viewport = v
			aspect = viewport.aspect
		}
		onRender {
			clearColorBuffer()
			clearDepthBuffer()
			material.render(model, camera)
		}
		onDispose {
			material.dispose()
		}
	}
}

object Context
