package kacirekj.fastcallhierarchy.parsedbytecode;

import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Clazz;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.objectweb.asm.ClassReader;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Parse compiled classes in JAR file into Clazz objects.
 */
public class JARLoader {

    private File tempDir;
    private JarFile jarFile;
    private final String TEMP_DIR = "tmp";

    public JARLoader(JarFile jarFile) throws UnsupportedEncodingException {
        String currentLocation = URLDecoder.decode(
                JARLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath(),
                "UTF-8");
        this.tempDir = new File(currentLocation + File.separator + TEMP_DIR);
        this.jarFile = jarFile;
    }

    public Set<Clazz> parseArtifact(File artifact) throws IOException, ZipException {
        // extract recursively all JAR in artifact into tempDir
        extractAllJarsRecursively(artifact, tempDir);

        // get all previously extracted files
        List<File> allExtractedFiles = new ArrayList<>();
        listFilesRecursively(tempDir, allExtractedFiles);

        ClassParserVisitor completeClassVisitor = new ClassParserVisitor();
        Set<Clazz> parsedClasses = new HashSet<>();

        allExtractedFiles.stream().filter(f -> f.getName().endsWith(".class"))
                .forEach(clazz -> {
                    try (FileInputStream fis = new FileInputStream(clazz);
                         InputStream stream = new BufferedInputStream(fis, 1024);)
                    {
                        ClassReader reader = new ClassReader(stream);
                        reader.accept(completeClassVisitor, 0);
                        parsedClasses.add(completeClassVisitor.getParsedClassIfReady());

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        // delete temporary dir

        tempDir.delete();

        return parsedClasses;
    }

    private void extractAllJarsRecursively(File input, File output) throws IOException, ZipException {
        if (!output.exists()) {
            output.mkdir();
        }

        extractZip(input, output);

        while (true) {
            List<File> files = new ArrayList<>();
            listFilesRecursively(output, files);
            List<File> jars = files.stream().filter(x -> x.getName().endsWith(".jar")).collect(Collectors.toList());

            if (jars.size() == 0) {
                break;
            }

            jars.stream().parallel().forEach(jar -> {

                extractZip(jar, output);
                System.out.println(jar.getName() + " deleted: " + jar.delete());
            });
        }
    }

    private void extractZip(File input, File output) {
        try {
            ZipFile zipFile = new ZipFile(input);
            zipFile.extractAll(output.getPath());
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    // get all the files from a directory
    public void listFilesRecursively(File dir, List<File> files) {
        File[] fList = dir.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listFilesRecursively(file, files);
            }
        }
    }


}
