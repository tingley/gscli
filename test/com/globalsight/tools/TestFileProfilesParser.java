package com.globalsight.tools;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.stream.XMLInputFactory;

import org.junit.Test;

public class TestFileProfilesParser {

    XMLInputFactory factory = XMLInputFactory.newFactory();
    
    @Test
    public void test() throws Exception {
        try {
            List<FileProfile> profiles = 
                    new FileProfilesParser(factory).parse(SAMPLE_FILE_PROFILE_XML);
            assertEquals(6, profiles.size());
            expect(profiles.get(0), "1071", "OpenTM2", "89", "OpenTM2 profile",
                   new TreeSet<String>(Arrays.asList(new String[] { "htm", "html"})),
                   "en_US", set("de_DE"));
            expect(profiles.get(1), "1072", "OpenTM2-TXT", "89", null, 
                    set("txt"), "en_US", set("de_DE"));
            expect(profiles.get(2), "1022", "Second project text", "62", null, 
                    set("txt"), "en_US", set("fr_FR"));
            expect(profiles.get(3), "1053", "Transman Word 2003", "108", null, 
                    set("doc"), "en_US", set("fr_FR", "pt_PT"));
            expect(profiles.get(4), "1090", "TransmanXML", "89", null, 
                    set("xml"), "en_US", set("de_DE"));
            expect(profiles.get(5), "1089", "XML test for TransMan", "89", null, 
                    set("xml"), "en_US", set("de_DE"));
        }
        catch (Exception e) {
            fail(e.getMessage());
            throw e;
        }
    }
    
    private SortedSet<String> set(String...s) {
        return new TreeSet<String>(Arrays.asList(s));
    }
    
    private void expect(FileProfile fp, String id, String name, String l10nProfile,
            String description, SortedSet<String> extensions,
            String sourceLocale, SortedSet<String> targetLocales) {
        assertEquals(id, fp.getId());
        assertEquals(name, fp.getName());
        assertEquals(l10nProfile, fp.getL10nProfile());
        assertEquals(description, fp.getDescription());
        assertEquals(extensions, fp.getFileExtensions());
        assertEquals(sourceLocale, fp.getSourceLocale());
        assertEquals(targetLocales, fp.getTargetLocales());
    }
    
    static final String SAMPLE_FILE_PROFILE_XML = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<fileProfileInfo>" +
        "      <fileProfile>" +
        "            <id>1071</id>" +
        "            <name>OpenTM2</name>" +
        "            <l10nprofile>89</l10nprofile>" +
        "            <description>OpenTM2 profile</description>" +
        "            <fileExtensionInfo>" +
        "                  <fileExtension>html</fileExtension>" +
        "                  <fileExtension>htm</fileExtension>" +
        "            </fileExtensionInfo>" +
        "            <localeInfo>" +
        "                  <sourceLocale>en_US</sourceLocale>" +
        "                  <targetLocale>de_DE</targetLocale>" +
        "            </localeInfo>" +
        "      </fileProfile>" +
        "      <fileProfile>" +
        "            <id>1072</id>" +
        "            <name>OpenTM2-TXT</name>" +
        "            <l10nprofile>89</l10nprofile>" +
        "            <description></description>" +
        "            <fileExtensionInfo>" +
        "                  <fileExtension>txt</fileExtension>" +
        "            </fileExtensionInfo>" +
        "            <localeInfo>" +
        "                  <sourceLocale>en_US</sourceLocale>" +
        "                  <targetLocale>de_DE</targetLocale>" +
        "            </localeInfo>" +
        "      </fileProfile>" +
        "      <fileProfile>" +
        "            <id>1022</id>" +
        "            <name>Second project text</name>" +
        "            <l10nprofile>62</l10nprofile>" +
        "            <description></description>" +
        "            <fileExtensionInfo>" +
        "                  <fileExtension>txt</fileExtension>" +
        "            </fileExtensionInfo>" +
        "            <localeInfo>" +
        "                  <sourceLocale>en_US</sourceLocale>" +
        "                  <targetLocale>fr_FR</targetLocale>" +
        "            </localeInfo>" +
        "      </fileProfile>" +
        "      <fileProfile>" +
        "            <id>1053</id>" +
        "            <name>Transman Word 2003</name>" +
        "            <l10nprofile>108</l10nprofile>" +
        "            <description></description>" +
        "            <fileExtensionInfo>" +
        "                  <fileExtension>doc</fileExtension>" +
        "            </fileExtensionInfo>" +
        "            <localeInfo>" +
        "                  <sourceLocale>en_US</sourceLocale>" +
        "                  <targetLocale>fr_FR</targetLocale>" +
        "                  <targetLocale>pt_PT</targetLocale>" +
        "            </localeInfo>" +
        "      </fileProfile>" +
        "      <fileProfile>" +
        "            <id>1090</id>" +
        "            <name>TransmanXML</name>" +
        "            <l10nprofile>89</l10nprofile>" +
        "            <description></description>" +
        "            <fileExtensionInfo>" +
        "                  <fileExtension>xml</fileExtension>" +
        "            </fileExtensionInfo>" +
        "            <localeInfo>" +
        "                  <sourceLocale>en_US</sourceLocale>" +
        "                  <targetLocale>de_DE</targetLocale>" +
        "            </localeInfo>" +
        "      </fileProfile>" +
        "      <fileProfile>" +
        "            <id>1089</id>" +
        "            <name>XML test for TransMan</name>" +
        "            <l10nprofile>89</l10nprofile>" +
        "            <description></description>" +
        "            <fileExtensionInfo>" +
        "                  <fileExtension>xml</fileExtension>" +
        "            </fileExtensionInfo>" +
        "            <localeInfo>" +
        "                  <sourceLocale>en_US</sourceLocale>" +
        "                  <targetLocale>de_DE</targetLocale>" +
        "            </localeInfo>" +
        "      </fileProfile>" +
        "</fileProfileInfo>";
}
