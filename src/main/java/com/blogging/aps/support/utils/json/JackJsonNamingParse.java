package com.blogging.aps.support.utils.json;

import java.util.HashMap;
import java.util.Map;

public class JackJsonNamingParse {
    private Map<String, String> nameParse = new HashMap<String, String>(0);

    public void set(String orgFiledName, String parseFiledName) {
        nameParse.put(orgFiledName, parseFiledName);
    }

    public Map<String, String> getParseMap() {
        return nameParse;
    }
}
