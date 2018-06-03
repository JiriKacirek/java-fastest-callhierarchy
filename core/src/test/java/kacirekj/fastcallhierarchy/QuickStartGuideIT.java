package kacirekj.fastcallhierarchy;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Set;
import java.util.jar.JarFile;

import kacirekj.fastcallhierarchy.parsedbytecode.ParsedClassesContainer;
import kacirekj.fastcallhierarchy.parsedbytecode.JARLoader;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Clazz;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Method;
import kacirekj.fastcallhierarchy.utils.Utils;
import kacirekj.fastcallhierarchy.datastructures.Node;
import org.junit.Before;
import org.junit.Test;

public class QuickStartGuideIT {
	JARLoader jarLoader;
	Method calle = new Method("cz/george/testmodule/A methodForA ()V"); // method call

	@Before
	public void init() throws IOException {
		// 0. Load testing JAR file (replace with your JAR)
		String runPath = URLDecoder.decode(
				QuickStartGuideIT.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
		File testJAR = new File(runPath + File.separator + "fastcallhierarchy-testproject-1.0.1.jar");

		// 1. Create JARLoader.
		JarFile jarFile = new JarFile(testJAR);
		jarLoader = new JARLoader(jarFile);
	}
	
	@Test
	public void printCallers() {
		// 2. Parse all classes in the given JAR
		Set<Clazz> parsedClasses = jarLoader.getParsedClasses();

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
