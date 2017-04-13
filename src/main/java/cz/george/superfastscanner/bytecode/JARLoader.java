package cz.george.superfastscanner.bytecode;

import cz.george.superfastscanner.bytecode.ClassParserVisitor;
import cz.george.superfastscanner.bytecode.clazz.Clazz;
import org.objectweb.asm.ClassReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by John on 4/8/2017.
 */
public class JARLoader {
    boolean resultReady = false;
    private Set<Clazz> parsedClasses = new HashSet<Clazz>();
    JarFile jarFile;

    public JARLoader(JarFile jar) throws IOException { jarFile = jar; loadJAR(); resultReady = true; }

    private void loadJAR() throws IOException {
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
        return resultReady ? parsedClasses : null;
    }


}
