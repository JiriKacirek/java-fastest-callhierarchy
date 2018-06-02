/**
 *  Things in this package operates above Java Agent, which is util to parse classes from parsedbytecode. Therefore, this
 *  package contains raw parsed classes from parsedbytecode.
 *  This raw classes don't have resolved any real connections between itself, for example: if classes have some
 *  interfaces, this interfaces are not connected to some real classes but only to signatures.
 */
package cz.george.superfastscanner.parsedbytecode;