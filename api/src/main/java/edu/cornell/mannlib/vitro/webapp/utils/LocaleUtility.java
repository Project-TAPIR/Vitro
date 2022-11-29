package edu.cornell.mannlib.vitro.webapp.utils;

import org.apache.commons.lang3.LocaleUtils;

import java.util.Locale;

public final class LocaleUtility {

    private LocaleUtility(){}

    public static Locale languageStringToLocale(String localeString){
        String[] parsedLoc = localeString.trim().split("_", -1);
        Locale locale = null;
        //regex pattern for locale tag with script and private-use subtag, e.g. sr_Latn_RS_x_uns
        if (localeString.matches("^[a-z]{1,3}_[A-Z][a-z]{3}_[A-Z]{2}_x_[a-z]{1,}"))
            locale = new Locale.Builder().setLanguage(parsedLoc[0]).setRegion(parsedLoc[2]).setScript(parsedLoc[1]).setExtension('x', parsedLoc[4]).build();
        //regex pattern for locale tag with script and private-use subtag, e.g. fr_CA_x_uqam
        if (localeString.matches("^[a-z]{1,3}_[A-Za-z]{2}_x_[a-z]{1,}"))
            locale = new Locale.Builder().setLanguage(parsedLoc[0]).setRegion(parsedLoc[1]).setExtension('x', parsedLoc[3]).build();
            //regex pattern for locale tag with script specified, e.g. sr_Latn_RS
        else if (localeString.matches("^[a-z]{1,3}_[A-Z][a-z]{3}_[A-Z]{2}"))
            locale = new Locale.Builder().setLanguage(parsedLoc[0]).setRegion(parsedLoc[2]).setScript(parsedLoc[1]).build();
            // other, just languge, e.g. es, or language + region, e.g. en_US, pt_BR, ru_RU, etc.
        else
            locale = LocaleUtils.toLocale(localeString);
        String localeLang = locale.toLanguageTag();
        return locale;
    }
}
