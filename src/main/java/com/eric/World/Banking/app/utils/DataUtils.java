package com.eric.World.Banking.app.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {
  public static String dataToString(LocalDateTime dateTime){
    return dateTime.format(DateTimeFormatter.ofPattern("yyy-MM-dd 'T' HH:mm:ss"));
  }
}
