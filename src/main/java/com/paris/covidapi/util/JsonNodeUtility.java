package com.paris.covidapi.util;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;

public class JsonNodeUtility {

    /**
     * Get the value of the given field name from the given object node.
     * @param node ObjectNode
     * @param key String
     * @return
     */
    public static String getStringValue(ObjectNode node, String key) {
        return node.get(key).asText();
    }

    //convertArrayNodeToList

    /**
     * Convert the given array node to a list of string.
     * @param values ArrayNode
     * @return List<String>
     */
    public static List<String> convertToList(ArrayNode values){
        final List<String> result= new ArrayList<>(values.size());
        values.forEach(jsonNode -> result.add(jsonNode.asText()));
        return result;
    }
}
