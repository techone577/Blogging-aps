package com.blogging.aps.support.utils.json;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;

/**
 * json命名处理
 * 
 * @author zhouchangqing
 * 
 */
public class UnderlineFilterStrategy extends PropertyNamingStrategy {
    private Set<String> filterSet = new HashSet<String>();

    public UnderlineFilterStrategy() {
    }

    public UnderlineFilterStrategy(Set<String> filterSet) {
        this.setFilterSet(filterSet);
    }

    @Override
    public String nameForGetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
        if (config != null && config instanceof SerializationConfig && filterSet.contains(defaultName)) {
            return defaultName;
        }
        return convert(defaultName);
    }

    @Override
    public String nameForSetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
        return convert(defaultName);
    }

    private String convert(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, len = input.length(); i < len; ++i) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append('_');
                c = Character.toLowerCase(c);
            }
            result.append(c);
        }
        return result.toString();
    }

    public Set<String> getFilterSet() {
        return filterSet;
    }

    public void setFilterSet(Set<String> filterSet) {
        this.filterSet = filterSet;
    }

}
