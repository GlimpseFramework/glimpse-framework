package org.glimpseframework.api.materials;

/**
 * OpenGL texture.
 * @author Slawomir Czerwinski
 */
public interface Texture {

	/**
	 * Generates texture.
	 * @throws TextureGeneratingException when generating texture fails
	 */
	void generate() throws TextureGeneratingException;

	/**
	 * Gets texture ID.
	 * @return texture ID
	 */
	int getId();
}
