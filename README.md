Fast Method call hierarchy Generator
====================================

This is fastest Method Call Hierarchy generator on GitHub. :) (as I'm aware)

### Quick start guide

Use case: you want quickly write to console method call hierarchy above some method. Follow this steps:
1. Download or Clone this repository
2. Open project in IntelliJ IDEA (It's not nessesary but project is build and configured in this IDE)
3. Look in /core/src/test/java/QuickStartGuideIT where is prepared example for testing JAR file.
4. Modify this unit test to load your own JAR. Done.

Donwside: it only works above jar you have to provide so it's not such comfortable as generating Find usages hierarchy in some IDE. But it's blazingly faster! Use it when your hierarchy is so large that it takes too long to generate it in an IDE.

### Project status
- Method call hierarchy for the given method bytecode signature is working but it was not tested on more complerx JAR
- All Nested Classes all ignored - support will be implemented (one day..)
- Polymorphism is supported for sure but resolving of concrete instance is impossible. Read bellow.

### TODO
- test that generator is working on some more complex JAR with intense use of abstraction
- add some lazy hierarchy generation which will generate hierarchies layer per layer
- add parameter to set maximum layers because of possible permutations above polymorphism
- maybe add some permutations filter as currently it can generate lot of redundant paths in code

### About
This generator is very fast which is also the reason why it have been created. It's fastest then IntelliJ Idea 
generator or Eclipse generator. It's fastest then all GitHub generators I've seen. Generating method call hierarchy on 
my complex banking project which took hours in the IntelliJ will took few minutes with this generator.

The reason why it is so fast is that this generator works directly on Java bytecode (so .jar or .war file is input) 
and what is much more important it completely loads the JAR file into the memory, parse all classes into HashMaps and 
only then is looking for method usages. This is quite the opossite to current IDE generators which seems to simply 
iterate over the classes again and again.

To generate method call hierarchy just 2 things are needed: 

1. Jar/War file with your application including all dependencies for searching scope. (which is logical)
2. Method signature for which call hierarchy should be generated.

### Method signature
This application works with bytecode. If you want to know how yours method signature should looks like, find 
compiled .class of our Class and you must will be able to get something like this:

com/some/package/ClassOfYourMethod nameOfYourMethod (Ljava/util/List;Ljava/lang/Long;Ljava/lang/Boolean;)Ljava/util/List;

Format is: Owner MethodName MethodDescription

Which can means:

    package com.some.package;

    class ClassOfYourMethod {
	    // accessors and generic types are not included in bytecode
	    private List<Anything> nameOfYourMethod(List<Anything> someList, Long someLong, Boolean someBoolean) {
		// body is not mandatory for your method, method signature will be the same for both classes and interfaces
	    }
    }

### Polymorphism
This application support polymorphism (otherwise it will be useless for sure) but take on mind that resolving of concrete instances during polymorphism is nearly impossible. Even IntelliJ idea cannot do this. Think about it: instance of some superclass or interface can be obtained from various unresolvably custom places including Spring .xml config files and so on. 
Therefore Polymorphism in your code have this unavoidably consequence: your generated method call hierarchy tree is totally complete BUT not all Root-to-Leaf paths (where Root is your method and Leafs are end-callers) are really valid in your application. For example let's have:

    interface I          { 			 void someMethod(){} }
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
            some/package/SomeCaller letsCallA()V 	  // VALID PATH
            some/package/SomeCaller letsCallB()V 	  // NOT VALID PATH
        some/package/SomeCaller letsCallADirectly()V  // VALID PATH
            
		



