package com.blogging.aps.support.utils.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JackJsonNamingFormat {
    private Set<String> filterFiled = new HashSet<String>(0);
    private Map<String, String> nameFormat = new HashMap<String, String>(0);

    public void set(String orgFiledName, String formatFiledName) {
        nameFormat.put(orgFiledName, formatFiledName);
    }

    public Map<String, String> getFormatMap() {
        return nameFormat;
    }

    public void setFilter(String orgFiledName) {
        filterFiled.add(orgFiledName);
    }

    public Set<String> getFilterSet() {
        return filterFiled;
    }
}
