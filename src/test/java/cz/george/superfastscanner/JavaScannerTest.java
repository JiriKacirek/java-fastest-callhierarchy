package cz.george.superfastscanner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import cz.george.superfastscanner.bytecode.JARLoader;
import cz.george.superfastscanner.bytecode.clazz.Clazz;
import cz.george.superfastscanner.bytecode.clazz.Method;
import cz.george.superfastscanner.hierarchytree.InheritanceTreeNode;
import cz.george.superfastscanner.hierarchytree.hashmap.InheritanceHierarchyMap;
import org.junit.Before;
import org.junit.Test;

public class JavaScannerTest {
	@Before
	public void init() throws IOException {

	}
	
	@Test
	public void findAllClassesThatExtendsOrImplementsAClassShouldReturnTwo() throws IOException {

	}


}
