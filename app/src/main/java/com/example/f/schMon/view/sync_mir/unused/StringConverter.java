/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir.unused;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Mir Ferdous on 26/11/2017.
 */
public class StringConverter implements JsonSerializer<String>,
        JsonDeserializer<String> {/*if a member string variable is null it will convert to blank string "" so that
            gson does not ignore that field*/

    public JsonElement serialize(String src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        if (src == null) {
            return new JsonPrimitive("");
        } else {
            return new JsonPrimitive(src.toString());
        }
    }

    public String deserialize(JsonElement json, Type typeOfT,
                              JsonDeserializationContext context)
            throws JsonParseException {
        return json.getAsJsonPrimitive().getAsString();
    }
}