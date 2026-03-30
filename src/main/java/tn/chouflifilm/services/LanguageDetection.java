package tn.chouflifilm.services;


import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;

public class LanguageDetection {
   static LanguageDetector detector = LanguageDetectorBuilder.fromLanguages(
            Language.FRENCH, Language.ENGLISH, Language.ARABIC, Language.SPANISH).build();

    public static String  afficherlangue(String mot){
        if ( detector.detectLanguageOf(mot)==Language.ENGLISH){
            return "en";
        }
        if ( detector.detectLanguageOf(mot)==Language.FRENCH){
            return "fr";
        }
        if ( detector.detectLanguageOf(mot)==Language.ARABIC){
            return "ar";
        }
        return "sp";
    }

}
