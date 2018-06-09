package kacirekj.fastcallhierarchy;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Set;
import java.util.jar.JarFile;

import kacirekj.fastcallhierarchy.parsedbytecode.ArtifactsLoader;
import kacirekj.fastcallhierarchy.parsedbytecode.ParsedClassesContainer;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Clazz;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Method;
import kacirekj.fastcallhierarchy.utils.Utils;
import kacirekj.fastcallhierarchy.datastructures.Node;
import net.lingala.zip4j.exception.ZipException;
import org.junit.Before;
import org.junit.Test;

public class QuickStartGuideIT {
	Method calle = new Method("java/lang/Object hashCode ()I"); // method call
	Set<Clazz> parsedClasses;

	@Before
	public void init() throws IOException, ZipException {
		// 0. Load testing JAR file (replace with your JAR)
		String runPath = URLDecoder.decode(
				QuickStartGuideIT.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
		File testJAR = new File( "C:\\Users\\Jiri Kacirek\\Downloads\\liferay-ce-portal-tomcat-7.0-ga6-20180320170724974.zip");

		// 1. Create ArtifactsLoader.
		JarFile jarFile = new JarFile(testJAR);

		// 2. Parse all classes in the given JAR
		ArtifactsLoader jarLoader = new ArtifactsLoader();
		//jarLoader.extractJar(testJAR, jarLoader.TEMP_DIR);
		parsedClasses = jarLoader.parseFiles(jarLoader.TEMP_DIR);

	}
	
	@Test
	public void printCallers() {

		// 3. Put and transofrm parsed classes into container (it will create hashmaps above this classes for quicker searching)
		ParsedClassesContainer parsedClassesContainer = new ParsedClassesContainer(parsedClasses);

		// 4. Put this container into HierarchyGen util class
		HierarchyGen hierarchyGen = new HierarchyGen(parsedClassesContainer);

		// it will return complete Method call hierarchy as root tree Node
		Node<Method> rootCallerNode = hierarchyGen.findCallHierarchy(calle);

		// 5. This will recursively walk through the tree and print hierarchy to console
		Utils.consolePrintNode(rootCallerNode);
	}
}
