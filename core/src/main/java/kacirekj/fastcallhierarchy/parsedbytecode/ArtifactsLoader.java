package kacirekj.fastcallhierarchy.parsedbytecode;

import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Clazz;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.objectweb.asm.ClassReader;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Parse compiled classes in JAR file into Clazz objects.
 */
public class ArtifactsLoader {

    public final File TEMP_DIR;
    public final String TEMP_DIR_NAME = "tmp";

    public ArtifactsLoader() throws UnsupportedEncodingException {
        String currentLocation = URLDecoder.decode(
                ArtifactsLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath(),
                "UTF-8");
        this.TEMP_DIR = new File(currentLocation + File.separator + TEMP_DIR_NAME);
    }

    public Set<Clazz> extractJarAndParseFiles(File jarFile) throws IOException, ZipException {
        return extractJarAndParseFiles(jarFile, true);
    }

    public Set<Clazz> extractJarAndParseFiles(File jarFile, boolean deleteTempFiles) throws IOException, ZipException {
        extractJar(jarFile, TEMP_DIR);

        Set<Clazz> parsedClasses = parseFiles(TEMP_DIR);

        if(deleteTempFiles) {
            TEMP_DIR.delete();
        }

        return parsedClasses;
    }

    public Set<Clazz> parseFiles(File directory) {
        return parseFiles(listFiles(directory));
    }

    public Set<Clazz> parseFiles(List<File> filesToParse) {
        ClassParserVisitor completeClassVisitor = new ClassParserVisitor();
        Set<Clazz> parsedClasses = new HashSet<>();


        filesToParse.stream().filter(f -> f.getName().endsWith(".class"))
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
        return parsedClasses;
    }

    public void extractJar(File input, File output) throws IOException, ZipException {
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

    private List<File> listFiles(File dir) {
        List<File> allExtractedFiles = new ArrayList<>();
        listFilesRecursively(TEMP_DIR, allExtractedFiles);
        return allExtractedFiles;
    }
    private void listFilesRecursively(File dir, List<File> files) {
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
