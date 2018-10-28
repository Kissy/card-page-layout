package fr.kissy.card_page_layout.config;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.IStringConverterFactory;

import java.awt.Dimension;

public class DimensionConverterFactory implements IStringConverterFactory {
    public Class<? extends IStringConverter<?>> getConverter(Class forType) {
        if (forType.equals(Dimension.class))
            return DimensionConverter.class;
        else return null;
    }
}