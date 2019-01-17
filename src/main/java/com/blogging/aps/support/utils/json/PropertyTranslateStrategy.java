package com.blogging.aps.support.utils.json;

import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;

/**
 * json命名处理
 * 
 * @author zhouchangqing
 * 
 */
public class PropertyTranslateStrategy extends PropertyNamingStrategy {

    private JackJsonNamingParse jackJsonNamingParse = null;
    private JackJsonNamingFormat jackJsonNamingFormat = null;

    public PropertyTranslateStrategy(JackJsonNamingParse nameParse) {
        this.setJackJsonNamingParse(nameParse);
    }

    public PropertyTranslateStrategy(JackJsonNamingFormat nameFormat) {
        this.setJackJsonNamingFormat(nameFormat);
    }

    public PropertyTranslateStrategy() {
    }

    @Override
    public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return serializeProperty(defaultName);
    }

    @Override
    public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return deserializeProperty(defaultName);
    }

    private String deserializeProperty(String input) {
        if (jackJsonNamingParse != null && jackJsonNamingParse.getParseMap().containsKey(input)
                && jackJsonNamingParse.getParseMap() != null) {
            return jackJsonNamingParse.getParseMap().get(input);
        }
        return input;
    }

    private String serializeProperty(String input) {
        if (jackJsonNamingFormat != null && jackJsonNamingFormat.getFilterSet().contains(input)) {
            return input;
        }
        if (jackJsonNamingFormat != null && jackJsonNamingFormat.getFormatMap().containsKey(input)
                && jackJsonNamingFormat.getFormatMap().get(input) != null) {
            return jackJsonNamingFormat.getFormatMap().get(input);
        }
        return input;
    }

    public JackJsonNamingParse getJackJsonNamingParse() {
        return jackJsonNamingParse;
    }

    public void setJackJsonNamingParse(JackJsonNamingParse jackJsonNamingParse) {
        this.jackJsonNamingParse = jackJsonNamingParse;
    }

    public JackJsonNamingFormat getJackJsonNamingFormat() {
        return jackJsonNamingFormat;
    }

    public void setJackJsonNamingFormat(JackJsonNamingFormat jackJsonNamingFormat) {
        this.jackJsonNamingFormat = jackJsonNamingFormat;
    }

}
