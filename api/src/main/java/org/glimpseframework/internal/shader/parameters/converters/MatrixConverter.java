package org.glimpseframework.internal.shader.parameters.converters;

import java.util.EnumSet;
import java.util.Set;
import org.glimpseframework.api.primitives.Matrix;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.AbstractConverter;
import org.glimpseframework.api.shader.parameters.converters.ShaderParameterAdapter;

class MatrixConverter extends AbstractConverter<Matrix> {

	MatrixConverter(ShaderParameterAdapter adapter) {
		super(adapter);
	}

	@Override
	protected void convertUniform(Parameter parameter, Matrix value) {
		adapter.uniformMatrix4(parameter.getName(), 1, false, value.get16f(), 0);
	}

	@Override
	public Set<Parameter.Type> getOutputTypes() {
		return EnumSet.of(Parameter.Type.MATRIX_4);
	}
}