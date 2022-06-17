package ru.migplus.site.utils;

import com.ibm.icu.text.Transliterator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrUtils {

    public static final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

    public static String getCyrillicToLatin(String src) {
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        String result = toLatinTrans.transliterate(src).toLowerCase().replace(" ", "_");
        return result;
    }

    public static String generateFullName(String surename, String name, String patronymic) {
        if (surename != null && name != null && patronymic != null)
            return surename + " " + name.substring(0, 1) + "." + patronymic.substring(0, 1) + ".";
        return "";
    }

    public static boolean notEmptyOrNull(String st) {
        return st != null && !st.isEmpty();
    }
}
