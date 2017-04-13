/**
 *  Things in this package operates above Java Agent, which is util to parse classes from bytecode. Therefore, this
 *  package contains raw parsed classes from bytecode.
 *  This raw classes don't have resolved any real connections between itself, for example: if classes have some
 *  interfaces, this interfaces are not connected to real classes.
 *  Resolve such dependencies between all parsed classes could be cpu time expensive. Therefore, bytecode-parsed-classes
 *  are in complete state above given JAR file and Complete-Class-Dependencies-Graph is created above as much as needed.
 */
package cz.george.superfastscanner.bytecode;