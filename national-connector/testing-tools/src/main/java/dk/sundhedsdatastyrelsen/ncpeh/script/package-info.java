/**
 * This package is used to create command line runnable versions of most integration test call.
 * This is needed, as our integration tests are not idempotent, and require specific input to work. These command line
 * utilities are used to create data for all integration tests to run without ruining the next integration test
 */
package dk.sundhedsdatastyrelsen.ncpeh.script;
