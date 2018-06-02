package cz.george.superfastscanner;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.jar.JarFile;

import cz.george.superfastscanner.hierarchytree.CallerNode;
import cz.george.superfastscanner.parsedbytecode.HashMaps;
import cz.george.superfastscanner.parsedbytecode.JARLoader;
import cz.george.superfastscanner.parsedbytecode.clazz.Clazz;
import cz.george.superfastscanner.parsedbytecode.clazz.Method;
import cz.george.superfastscanner.hierarchytree.ClassInheritanceNode;
import cz.george.superfastscanner.hierarchytree.hashmap.InheritanceHierarchyHashMap;
import cz.george.superfastscanner.utils.Utils;
import cz.george.superfastscanner.utils.tree.Node;
import org.junit.Before;
import org.junit.Test;

public class JavaScannerTest {

	JarFile jarFile;
	JARLoader jarLoader;
	//Method calle = new Method("com/db/lrc/creditrisk/cib/mapper/CommercialBankingMapper getFacilityPairsByOrgIds (Ljava/util/List;Ljava/lang/Long;Ljava/lang/Boolean;Ljava/lang/Boolean;)Ljava/util/List;");
	Method calle = new Method("cz/george/testmodule/A methodForA ()V");

	@Before
	public void init() throws IOException {
		File f = new File("C:\\Users\\Jiri Kacirek\\Desktop\\java-fastest-callhierarchy\\test-app\\target\\testmodule-1.0.1.jar");
		jarFile = new JarFile(f);
		jarLoader = new JARLoader(jarFile);
	}
	
	@Test
	public void printCallers() throws IOException {
		Set<Clazz> parsedClasses = jarLoader.getParsedClasses();
		HashMaps hashMaps = new HashMaps(parsedClasses);
		HierarchyGen hierarchyGen = new HierarchyGen(hashMaps);
		Node<Method> rootCallerNode = hierarchyGen.findCallers(calle);
		Utils.consolePrintNode(rootCallerNode);
		System.out.println();
		CallerNode callerNode = new CallerNode(calle, hashMaps);
	}

	@Test
	public void print() throws IOException {
		HashMaps tool = new HashMaps(jarLoader.getParsedClasses());
		System.out.println(tool.methodsMap.toString());

		ClassInheritanceNode node = new ClassInheritanceNode(new Clazz("cz/george/testmodule/A", null, null),  tool);

		System.out.println(tool.methodsMap.toString());

		InheritanceHierarchyHashMap inheritanceHierarchyMap = new InheritanceHierarchyHashMap(node);

		System.out.println(inheritanceHierarchyMap.toString());

	}
}
