*********************************************
WARNING: repo is damaged for some reasons now
*********************************************
Fastest Java Call Hierarchy generator on GitHub. :)

### PROJECT STATUS:
- Method call hierarchy for the given method bytecode signature is working and it had been tested on very complex banking application with success
- There isn't any Quick Start solution for users right now, it's just very raw library
- All Nested Classes all ignored - support will be implemented soon
- Polymorphism is supported for sure but resolving of concrete instance is impossible (read below)


This generator is very fast which is also the reason why it have been created. It's fastest then IntelliJ Idea generator or Eclipse generator. It's fastest then all GitHub generators I've seen. Generating method call hierarchy on my complex banking project which took hours in the IntelliJ will took few seconds with this generator.

The reason why it is so fast is that this generator works directly on Java bytecode (so .jar or .war file is input) and what's much more important it completely load the JAR files into the memory. Once .class files from JAR file are parsed and collected into various Hashmaps, search for conrecte method call hierarchy (which means recursively find usages of some method) is really fast.

To generate method call hierarchy just 2 things are needed: 

1. Jar/War file with your application including all dependencies for searching scope. (which is logical)
2. Method signature for which call hierarchy should be generated.

### Method signature:
This application works with bytecode. If you want to know how yours method signature should looks like, find compiled .class of our Class and you will be able to get something like this:

	com/some/package/ClassOfYourMethod nameOfYourMethod (Ljava/util/List;Ljava/lang/Long;Ljava/lang/Boolean;)Ljava/util/List;

Format is: `Owner MethodName MethodDescription`

Which mean:

	package com.some.package;
	
	class ClassOfYourMethod {
		// accessors and generic types are not included in bytecode
		private List<Anything> nameOfYourMethod(List<Anything> someList, Long someLong, Boolean someBoolean) {
			// body is not mandatory for your method, method signature will be the same for both classes 
			// and interfaces
		}
	}

### Polymorphism:

This application support polymorphism (otherwise it will be useless for sure) but take on mind that resolving of concrete instances during polymorphism is nearly impossible. Even IntelliJ Idea cannot do this. Think about it: instance of some superclass or interface can be obtained from various unresolvably custom places including Spring .xml config files and so on. 
Therefore Polymorphism in your code have this unavoidably consequence: your generated method call hierarchy tree is totally complete BUT not all Root-to-Leaf paths (where Root is your method and Leafs are end-callers) are nessesary valid in your application. For example let's have:

	interface I          { 	    void someMethod(){} }
	class A implements I { @override void someMethod(){} }
	class B implements I { @override void someMethod(){} }

	class SomeCaller     { 
 	  void letsCallA() {
	    I i = new A(); 
	    i.someMethod(); 
  	  }

	  void letsCallB() {
	    I i = new B();
	    i.someMethod(); 
  	  }
  
  	  void letsCallADirectly() {
	    A a = new A(); 
	    a.someMethod(); 
  	  }
	}

Method call hierarchy for A.someMethod() will looks like this:

	some/package/A someMethod ()V
	  some/package/I someMethod()V
	    some/package/SomeCaller letsCallA()V	// VALID PATH
	    some/package/SomeCaller letsCallB()V	// NOT VALID PATH
	  some/package/SomeCaller letsCallADirectly()V	// VALID PATH
		
		



