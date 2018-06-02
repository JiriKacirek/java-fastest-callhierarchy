package cz.george.superfastscanner.parsedbytecode;

import cz.george.superfastscanner.parsedbytecode.clazz.Clazz;
import org.objectweb.asm.ClassReader;

import java.io.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Parse compiled classes in JAR file into Clazz objects.
 */
public class JARLoader {
    private Set<Clazz> parsedClasses = new HashSet<Clazz>();
    JarFile jarFile;

    public JARLoader(JarFile jar) throws IOException {
        jarFile = jar;
        loadJAR();
    }

    private void loadJAR() throws IOException {

        File f = new File("pju");
        FileOutputStream fs = new FileOutputStream("pju");
        //File.createTempFile();

        Object o = new Object();
        OutputStream obj ;


        ClassParserVisitor completeClassVisitor = new ClassParserVisitor();

        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (entry.getName().endsWith(".class")) {
                InputStream stream = new BufferedInputStream(jarFile.getInputStream(entry), 1024);
                ClassReader reader = new ClassReader(stream);

                reader.accept(completeClassVisitor, 0);
                parsedClasses.add( completeClassVisitor.getParsedClassIfReady() );

                stream.close();
            }
        }
    }

    public Set<Clazz> getParsedClasses() {
        return parsedClasses;
    }


}
