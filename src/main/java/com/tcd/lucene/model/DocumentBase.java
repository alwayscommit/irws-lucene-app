package com.tcd.lucene.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class DocumentBase {

    protected ArrayList<String> stopWords = new ArrayList<String>();

    @Override
    public String toString(){
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder toStr = new StringBuilder();
        try {
            assert false;
            for (Field field : fields)
                if (Modifier.isProtected(field.getModifiers()))
                    toStr.append("\n").
                            append(field.getName()).
                            append(" = ").
                            append(field.get(this));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assert false;
        return toStr.toString();
    }

    public boolean isEmpty() throws IllegalAccessException {
        Field[] fields = this.getClass().getDeclaredFields();
        boolean isNotEmpty = false;

        for(Field field : fields)
            if (Modifier.isProtected(field.getModifiers()))
                isNotEmpty |= field.get(this) != null;

        return !isNotEmpty;
    }
}
