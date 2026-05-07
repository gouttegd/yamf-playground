package org.incenp.yamf.playground.util;

import org.incenp.linkml.core.ObjectConverter;

/**
 * A converter that can be used to override the default converter for a given
 * type.
 * <p>
 * This is a hack to test one way of dealing with an extension that creates a
 * derived class from a base class, and where we have no way of knowing that we
 * should expect a derived class.
 */
public class OverridingConverter extends ObjectConverter {

    private Class<?> visible;

    /**
     * Creates a new instance.
     * 
     * @param klass   The type to convert to and from.
     * @param visible The type that will be registered to a ConverterContext.
     */
    public OverridingConverter(Class<?> klass, Class<?> visible) {
        super(klass);
        this.visible = visible;
    }

    @Override
    public Class<?> getType() {
        return visible;
    }
}
