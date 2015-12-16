package org.glimpseframework.internal.shader.parameters.converters;

import java.util.EnumSet;
import java.util.Set;
import org.glimpseframework.api.shader.parameters.Parameter;
import org.glimpseframework.api.shader.parameters.converters.AbstractConverter;

class UnsupportedConverter extends AbstractConverter<Object> {

	UnsupportedConverter() {
		super(null);
	}

	@Override
	public Set<Parameter.Type> getOutputTypes() {
		return EnumSet.noneOf(Parameter.Type.class);
	}
}
