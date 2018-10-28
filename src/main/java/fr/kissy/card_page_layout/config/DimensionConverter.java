package fr.kissy.card_page_layout.config;

import com.beust.jcommander.IStringConverter;

import java.awt.Dimension;

public class DimensionConverter implements IStringConverter<Dimension> {
  @Override
  public Dimension convert(String value) {
    String[] s = value.split("x");
    return new Dimension(Integer.parseInt(s[0], 10), Integer.parseInt(s[1], 10));
  }
}