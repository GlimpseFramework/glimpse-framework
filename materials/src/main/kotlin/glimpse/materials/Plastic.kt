package glimpse.materials

import glimpse.Color
import glimpse.Matrix
import glimpse.Vector
import glimpse.cameras.Camera
import glimpse.gles.GLES
import glimpse.models.Model
import glimpse.shaders.Program
import glimpse.shaders.shaderProgram

/**
 * Plastic material.
 */
class Plastic(val diffuse: Color, val ambient: Color = diffuse, val shininess: Float = 1f, val specularExponent: Float = 20f) : Material {

	companion object {
		fun init(gles: GLES) {
			PlasticShaderHelper.init(gles)
		}
	}

	override fun render(model: Model, camera: Camera) {
		val mvpMatrix = camera.cameraMatrix * model.modelMatrix
		PlasticShaderHelper.use()
		PlasticShaderHelper["u_DiffuseColor"] = diffuse
		PlasticShaderHelper["u_AmbientColor"] = ambient
		PlasticShaderHelper["u_Shininess"] = shininess
		PlasticShaderHelper["u_SpecularExponent"] = specularExponent
		PlasticShaderHelper["u_MVPMatrix"] = mvpMatrix
		PlasticShaderHelper["u_NormalMatrix"] = mvpMatrix.inverse().transpose()
		PlasticShaderHelper["u_LightPosition"] = mvpMatrix * camera.position.translateBy(Vector.Z_UNIT * 2f)
		PlasticShaderHelper["u_CameraPosition"] = mvpMatrix * camera.position
		PlasticShaderHelper.drawMesh(model.mesh)
	}

	override fun dispose() {
		PlasticShaderHelper.dispose()
	}
}

internal object PlasticShaderHelper : ShaderHelper() {

	override val program: Program by lazy {
		shaderProgram(gles) {
			vertexShader {
				"""
				uniform mat4 u_MVPMatrix;
				uniform mat4 u_NormalMatrix;

				attribute vec4 a_VertexPosition;
				attribute vec4 a_VertexNormal;

				uniform vec4 u_LightPosition;
				uniform vec4 u_CameraPosition;

				varying vec3 v_Normal;
				varying vec3 v_LightDirection;
				varying vec3 v_CameraDirection;

				void main() {
					vec4 position = u_MVPMatrix * a_VertexPosition;

					v_Normal = normalize(u_NormalMatrix * a_VertexNormal).xyz;
					v_LightDirection = normalize(vec3(u_LightPosition - position));
					v_CameraDirection = normalize(vec3(u_CameraPosition - position));

					gl_Position = position;
				}
				""".trimIndent()
			}
			fragmentShader {
				"""
				uniform vec4 u_DiffuseColor;
				uniform vec4 u_AmbientColor;

				uniform float u_Shininess;
				uniform float u_SpecularExponent;

				varying vec3 v_Normal;
				varying vec3 v_LightDirection;
				varying vec3 v_CameraDirection;

				void main() {
					float specularFactor = pow(max(0.0, dot(reflect(-v_LightDirection, v_Normal), v_CameraDirection)), u_SpecularExponent);

					float diffuseValue = max(0.0, dot(v_Normal, v_LightDirection));
					float specularValue = u_Shininess * specularFactor;

					vec3 diffuse = u_DiffuseColor.rgb * 0.5 * diffuseValue;
					vec3 ambient = u_AmbientColor.rgb * 0.2;
					vec3 specular = vec3(1.0, 1.0, 1.0) * specularValue;

					gl_FragColor = vec4(specular + diffuse + ambient, u_DiffuseColor.a);
				}
				""".trimIndent()
			}
		}
	}

	override val vertexPositionAttributeName = "a_VertexPosition"
	override val vertexTextureCoordinatesAttributeName = null
	override val vertexNormalAttributeName = "a_VertexNormal"
}