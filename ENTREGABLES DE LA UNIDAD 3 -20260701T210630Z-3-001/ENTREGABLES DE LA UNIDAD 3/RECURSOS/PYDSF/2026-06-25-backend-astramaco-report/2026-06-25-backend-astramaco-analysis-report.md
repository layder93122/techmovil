# Code analysis
## backend-astramaco 
#### Version 0.0.1-SNAPSHOT 

**By: Administrator**

*Date: 2026-06-25*

## Introduction
This document contains results of the code analysis of backend-astramaco

Backend Astramaco

## Configuration

- Quality Profiles
    - Names: Sonar way [Java]; Sonar way [XML]; 
    - Files: AZ8ARTqTqN0ZfjutJlsK.json; AZ8ARTtpqN0ZfjutJl4I.json; 


 - Quality Gate
    - Name: Sonar way
    - File: Sonar way.xml

## Synthesis

### Analysis Status

Reliability | Security | Security Review | Maintainability |
:---:|:---:|:---:|:---:
A | A | E | A |

### Quality gate status

| Quality Gate Status | OK |
|-|-|

Metric|Value
---|---
Reliability Rating on New Code|OK
Security Rating on New Code|OK
Maintainability Rating on New Code|OK


### Metrics

Coverage | Duplications | Comment density | Median number of lines of code per file | Adherence to coding standard |
:---:|:---:|:---:|:---:|:---:
87.9 % | 6.2 % | 3.7 % | 31.0 | 99.3 %

### Tests

Total | Success Rate | Skipped | Errors | Failures |
:---:|:---:|:---:|:---:|:---:
0 | 0 % | 0 | 0 | 0

### Detailed technical debt

Reliability|Security|Maintainability|Total
---|---|---|---
-|-|6d 6h 44min|6d 6h 44min


### Metrics Range

\ | Cyclomatic Complexity | Cognitive Complexity | Lines of code per file | Coverage | Comment density (%) | Duplication (%)
:---|:---:|:---:|:---:|:---:|:---:|:---:
Min | 0.0 | 0.0 | 5.0 | 72.7 | 0.0 | 0.0
Max | 461.0 | 266.0 | 4437.0 | 100.0 | 22.9 | 55.6

### Volume

Language|Number
---|---
Java|4437
XML|218
Total|4655


## Issues

### Issues count by severity and types

Type / Severity|INFO|MINOR|MAJOR|CRITICAL|BLOCKER
---|---|---|---|---|---
BUG|0|0|0|0|0
VULNERABILITY|0|0|0|0|0
CODE_SMELL|2|152|34|46|0


### Issues List

Name|Description|Type|Severity|Number
---|---|---|---|---
String literals should not be duplicated|Duplicated string literals make the process of refactoring error-prone, since you must be sure to update all occurrences. <br /> On the other hand, constants can be referenced from many places, but only need to be updated in a single place. <br /> Noncompliant Code Example <br /> With the default threshold of 3: <br />  <br /> public void run() { <br />   prepare("action1");                              // Noncompliant - "action1" is duplicated 3 times <br />   execute("action1"); <br />   release("action1"); <br /> } <br />  <br /> @SuppressWarning("all")                            // Compliant - annotations are excluded <br /> private void method1() { /* ... */ } <br /> @SuppressWarning("all") <br /> private void method2() { /* ... */ } <br />  <br /> public String method3(String a) { <br />   System.out.println("'" + a + "'");               // Compliant - literal "'" has less than 5 characters and is excluded <br />   return "";                                       // Compliant - literal "" has less than 5 characters and is excluded <br /> } <br />  <br /> Compliant Solution <br />  <br /> private static final String ACTION_1 = "action1";  // Compliant <br />  <br /> public void run() { <br />   prepare(ACTION_1);                               // Compliant <br />   execute(ACTION_1); <br />   release(ACTION_1); <br /> } <br />  <br /> Exceptions <br /> To prevent generating some false-positives, literals having less than 5 characters are excluded.|CODE_SMELL|CRITICAL|44
Generic wildcard types should not be used in return types|It is highly recommended not to use wildcard types as return types. Because the type inference rules are fairly complex it is <br /> unlikely the user of that API will know how to use it correctly. <br /> Let’s take the example of method returning a "List&lt;? extends Animal&gt;". Is it possible on this list to add a Dog, a Cat, …​ we simply don’t <br /> know. And neither does the compiler, which is why it will not allow such a direct use. The use of wildcard types should be limited to method <br /> parameters. <br /> This rule raises an issue when a method returns a wildcard type. <br /> Noncompliant Code Example <br />  <br /> List&lt;? extends Animal&gt; getAnimals(){...} <br />  <br /> Compliant Solution <br />  <br /> List&lt;Animal&gt; getAnimals(){...} <br />  <br /> or <br />  <br /> List&lt;Dog&gt; getAnimals(){...} <br /> |CODE_SMELL|CRITICAL|1
Cognitive Complexity of methods should not be too high|Cognitive Complexity is a measure of how hard the control flow of a method is to understand. Methods with high Cognitive Complexity will be <br /> difficult to maintain. <br /> Exceptions <br /> equals and hashCode methods are ignored because they might be automatically generated and might end up being difficult to <br /> understand, especially in presence of many fields. <br /> See <br />  <br />    Cognitive Complexity  <br /> |CODE_SMELL|CRITICAL|1
Deprecated code should be removed|This rule is meant to be used as a way to track code which is marked as being deprecated. Deprecated code should eventually be removed. <br /> Noncompliant Code Example <br />  <br /> class Foo { <br />   /** <br />    * @deprecated <br />    */ <br />   public void foo() {    // Noncompliant <br />   } <br />  <br />   @Deprecated            // Noncompliant <br />   public void bar() { <br />   } <br />  <br />   public void baz() {    // Compliant <br />   } <br /> } <br /> |CODE_SMELL|INFO|2
Generic exceptions should never be thrown|Using such generic exceptions as Error, RuntimeException, Throwable, and Exception prevents <br /> calling methods from handling true, system-generated exceptions differently than application-generated errors. <br /> Noncompliant Code Example <br />  <br /> public void foo(String bar) throws Throwable {  // Noncompliant <br />   throw new RuntimeException("My Message");     // Noncompliant <br /> } <br />  <br /> Compliant Solution <br />  <br /> public void foo(String bar) { <br />   throw new MyOwnRuntimeException("My Message"); <br /> } <br />  <br /> Exceptions <br /> Generic exceptions in the signatures of overriding methods are ignored, because overriding method has to follow signature of the throw declaration <br /> in the superclass. The issue will be raised on superclass declaration of the method (or won’t be raised at all if superclass is not part of the <br /> analysis). <br />  <br /> @Override <br /> public void myMethod() throws Exception {...} <br />  <br /> Generic exceptions are also ignored in the signatures of methods that make calls to methods that throw generic exceptions. <br />  <br /> public void myOtherMethod throws Exception { <br />   doTheThing();  // this method throws Exception <br /> } <br />  <br /> See <br />  <br />    MITRE, CWE-397 - Declaration of Throws for Generic Exception  <br />    CERT, ERR07-J. - Do not throw RuntimeException, Exception, or Throwable  <br /> |CODE_SMELL|MAJOR|10
Deprecated elements should have both the annotation and the Javadoc tag|Deprecation should be marked with both the @Deprecated annotation and @deprecated Javadoc tag. The annotation enables tools such as <br /> IDEs to warn about referencing deprecated elements, and the tag can be used to explain when it was deprecated, why, and how references should be <br /> refactored. <br /> Noncompliant Code Example <br />  <br /> class MyClass { <br />  <br />   @Deprecated <br />   public void foo1() {    // Noncompliant: Add the missing @deprecated Javadoc tag. <br />   } <br />  <br />   /** <br />     * @deprecated <br />     */ <br />   public void foo2() {    // Noncompliant: Add the missing @Deprecated annotation. <br />   } <br />  <br /> } <br />  <br /> Compliant Solution <br />  <br /> class MyClass { <br />  <br />   /** <br />     * @deprecated (when, why, refactoring advice...) <br />     */ <br />   @Deprecated <br />   public void foo1() { <br />   } <br />  <br /> } <br />  <br /> Exceptions <br /> The members and methods of a deprecated class or interface are ignored by this rule. The classes and interfaces themselves are still subject to <br /> it. <br />  <br /> /** <br />  * @deprecated (when, why, etc...) <br />  */ <br /> @Deprecated <br /> class Qix  { <br />  <br />   public void foo() {} // Compliant; class is deprecated <br />  <br /> } <br />  <br /> /** <br />  * @deprecated (when, why, etc...) <br />  */ <br /> @Deprecated <br /> interface Plop { <br />  <br />   void bar(); <br />  <br /> } <br /> |CODE_SMELL|MAJOR|2
Try-catch blocks should not be nested|Nesting try/catch blocks severely impacts the readability of source code because it makes it too difficult to understand <br /> which block will catch which exception.|CODE_SMELL|MAJOR|15
Unused method parameters should be removed|Unused parameters are misleading. Whatever the values passed to such parameters, the behavior will be the same. <br /> Noncompliant Code Example <br />  <br /> void doSomething(int a, int b) {     // "b" is unused <br />   compute(a); <br /> } <br />  <br /> Compliant Solution <br />  <br /> void doSomething(int a) { <br />   compute(a); <br /> } <br />  <br /> Exceptions <br /> The rule will not raise issues for unused parameters: <br />  <br />    that are annotated with @javax.enterprise.event.Observes  <br />    in overrides and implementation methods  <br />    in interface default methods  <br />    in non-private methods that only throw or that have empty bodies  <br />    in annotated methods, unless the annotation is @SuppressWarning("unchecked") or @SuppressWarning("rawtypes"), in <br />   which case the annotation will be ignored  <br />    in overridable methods (non-final, or not member of a final class, non-static, non-private), if the parameter is documented with a proper <br />   javadoc.  <br />  <br />  <br /> @Override <br /> void doSomething(int a, int b) {     // no issue reported on b <br />   compute(a); <br /> } <br />  <br /> public void foo(String s) { <br />   // designed to be extended but noop in standard case <br /> } <br />  <br /> protected void bar(String s) { <br />   //open-closed principle <br /> } <br />  <br /> public void qix(String s) { <br />   throw new UnsupportedOperationException("This method should be implemented in subclasses"); <br /> } <br />  <br /> /** <br />  * @param s This string may be use for further computation in overriding classes <br />  */ <br /> protected void foobar(int a, String s) { // no issue, method is overridable and unused parameter has proper javadoc <br />   compute(a); <br /> } <br />  <br /> See <br />  <br />    CERT, MSC12-C. - Detect and remove code that has no effect or is never executed <br />    <br /> |CODE_SMELL|MAJOR|3
Unused assignments should be removed|A dead store happens when a local variable is assigned a value that is not read by any subsequent instruction. Calculating or retrieving a value <br /> only to then overwrite it or throw it away, could indicate a serious error in the code. Even if it’s not an error, it is at best a waste of resources. <br /> Therefore all calculated values should be used. <br /> Noncompliant Code Example <br />  <br /> i = a + b; // Noncompliant; calculation result not used before value is overwritten <br /> i = compute(); <br />  <br /> Compliant Solution <br />  <br /> i = a + b; <br /> i += compute(); <br />  <br /> Exceptions <br /> This rule ignores initializations to -1, 0, 1, null, true, false and "". <br /> See <br />  <br />    MITRE, CWE-563 - Assignment to Variable without Use ('Unused Variable')  <br />    CERT, MSC13-C. - Detect and remove unused values  <br />    CERT, MSC56-J. - Detect and remove superfluous code and values  <br /> |CODE_SMELL|MAJOR|2
Deprecated annotations should include explanations|Since Java 9, @Deprecated has two additional arguments to the annotation: <br />  <br />    since allows you to describe when the deprecation took place  <br />    forRemoval, indicates whether the deprecated element will be removed at some future date  <br />  <br /> In order to ease the maintainers work, it is recommended to always add one or both of these arguments. <br /> This rule reports an issue when @Deprecated is used without any argument. <br /> Noncompliant Code Example <br />  <br /> @Deprecated <br />  <br /> Compliant Solution <br />  <br /> @Deprecated(since="4.2", forRemoval=true) <br />  <br /> Exceptions <br /> The members and methods of a deprecated class or interface are ignored by this rule. The classes and interfaces themselves are still subject to <br /> it. <br /> See Also <br />  <br />    S1123  <br /> |CODE_SMELL|MAJOR|2
Unnecessary imports should be removed|The imports part of a file should be handled by the Integrated Development Environment (IDE), not manually by the developer. <br /> Unused and useless imports should not occur if that is the case. <br /> Leaving them in reduces the code’s readability, since their presence can be confusing. <br /> Noncompliant Code Example <br />  <br /> package my.company; <br />  <br /> import java.lang.String;        // Noncompliant; java.lang classes are always implicitly imported <br /> import my.company.SomeClass;    // Noncompliant; same-package files are always implicitly imported <br /> import java.io.File;            // Noncompliant; File is not used <br />  <br /> import my.company2.SomeType; <br /> import my.company2.SomeType;    // Noncompliant; 'SomeType' is already imported <br />  <br /> class ExampleClass { <br />  <br />   public String someString; <br />   public SomeType something; <br />  <br /> } <br />  <br /> Exceptions <br /> Imports for types mentioned in Javadocs are ignored.|CODE_SMELL|MINOR|2
Unused local variables should be removed|If a local variable is declared but not used, it is dead code and should be removed. Doing so will improve maintainability because developers will <br /> not wonder what the variable is used for. <br /> Noncompliant Code Example <br />  <br /> public int numberOfMinutes(int hours) { <br />   int seconds = 0;   // seconds is never used <br />   return hours * 60; <br /> } <br />  <br /> Compliant Solution <br />  <br /> public int numberOfMinutes(int hours) { <br />   return hours * 60; <br /> } <br /> |CODE_SMELL|MINOR|2
"@Deprecated" code should not be used|Once deprecated, classes, and interfaces, and their members should be avoided, rather than used, inherited or extended. Deprecation is a warning <br /> that the class or interface has been superseded, and will eventually be removed. The deprecation period allows you to make a smooth transition away <br /> from the aging, soon-to-be-retired technology. <br /> Noncompliant Code Example <br />  <br /> /** <br />  * @deprecated  As of release 1.3, replaced by {@link #Fee} <br />  */ <br /> @Deprecated <br /> public class Fum { ... } <br />  <br /> public class Foo { <br />   /** <br />    * @deprecated  As of release 1.7, replaced by {@link #doTheThingBetter()} <br />    */ <br />   @Deprecated <br />   public void doTheThing() { ... } <br />  <br />   public void doTheThingBetter() { ... } <br /> } <br />  <br /> public class Bar extends Foo { <br />   public void doTheThing() { ... } // Noncompliant; don't override a deprecated method or explicitly mark it as @Deprecated <br /> } <br />  <br /> public class Bar extends Fum {  // Noncompliant; Fum is deprecated <br />  <br />   public void myMethod() { <br />     Foo foo = new Foo();  // okay; the class isn't deprecated <br />     foo.doTheThing();  // Noncompliant; doTheThing method is deprecated <br />   } <br /> } <br />  <br /> See <br />  <br />    MITRE, CWE-477 - Use of Obsolete Functions  <br />    CERT, MET02-J. - Do not use deprecated or obsolete classes or methods  <br /> |CODE_SMELL|MINOR|147
Avoid using boxed "Boolean" types directly in boolean expressions|When boxed type java.lang.Boolean is used as an expression to determine the control flow (as described in Java Language Specification §4.2.5 The boolean Type and <br /> boolean Values) it will throw a NullPointerException if the value is null (as defined in Java Language Specification §5.1.8 Unboxing Conversion). <br /> It is safer to avoid such conversion altogether and handle the null value explicitly. <br /> Note, however, that no issues will be raised for Booleans that have already been null-checked. <br /> Noncompliant Code Example <br />  <br /> Boolean b = getBoolean(); <br /> if (b) {  // Noncompliant, it will throw NPE when b == null <br />   foo(); <br /> } else { <br />   bar(); <br /> } <br />  <br /> Compliant Solution <br />  <br /> Boolean b = getBoolean(); <br /> if (Boolean.TRUE.equals(b)) { <br />   foo(); <br /> } else { <br />   bar();  // will be invoked for both b == false and b == null <br /> } <br />  <br />  <br /> Boolean b = getBoolean(); <br /> if(b != null){ <br />   String test = b ? "test" : ""; <br /> } <br />  <br /> See <br />  <br />    Java Language Specification §5.1.8 Unboxing Conversion <br />    <br /> |CODE_SMELL|MINOR|1


## Security Hotspots

### Security hotspots count by category and priority

Category / Priority|LOW|MEDIUM|HIGH
---|---|---|---
LDAP Injection|0|0|0
Object Injection|0|0|0
Server-Side Request Forgery (SSRF)|0|0|0
XML External Entity (XXE)|0|0|0
Insecure Configuration|0|0|0
XPath Injection|0|0|0
Authentication|0|0|0
Weak Cryptography|0|0|0
Denial of Service (DoS)|0|0|0
Log Injection|0|0|0
Cross-Site Request Forgery (CSRF)|0|0|1
Open Redirect|0|0|0
Permission|0|0|0
SQL Injection|0|0|0
Encryption of Sensitive Data|0|0|0
Traceability|0|0|0
Buffer Overflow|0|0|0
File Manipulation|0|0|0
Code Injection (RCE)|0|0|0
Cross-Site Scripting (XSS)|0|0|0
Command Injection|0|0|0
Path Traversal Injection|0|0|0
HTTP Response Splitting|0|0|0
Others|0|0|0


### Security hotspots

Category|Name|Priority|Severity|Count
---|---|---|---|---
Cross-Site Request Forgery (CSRF)|Disabling CSRF protections is security-sensitive|HIGH|CRITICAL|1
