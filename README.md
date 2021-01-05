# RuntimeTransformer

A tool allowing for easy class modification at runtime, when using a normal javaagent at startup would be too inconvenient.
Note, this method comes with disadvantages, for example method modifiers may not be altered, new methods can not be created and neither can class inheritance be changed.

## Usage

To install the artifact into your local maven repo execute the correct gradle wrapper with the "publishToMavenLocal" task, that is ```gradlew.bat publishToMavenLocal``` under Windows, and ```./gradlew publishToMavenLocal``` under *nix;

Let's assume we want to inject an event handler into the `setHealth` method of `EntityLiving`,
therefore the method should something like this after transformation:

```java
public void setHealth(float newHealth) {
    ImaginaryEvent event = ImaginaryEventBus.callEvent(new ImaginaryEvent(this, newHealth));
    
    if (event.isCancelled())
        return;
        
    newHealth = event.getNewHealth();
    
    // Minecraft Code
}
```
 
To get there, we first need to define a transformer, this should optimally be in its own class and look something like this:

```java
@Transform(EntityLiving.class) // The class we want to transform
public class EntityLivingTransformer extends EntityLiving { // Extending EntityLiving in our transformer makes things easier, but isn't required (Which, for example, allows you to transform final classes)
    
    @Inject(InjectionType.INSERT) // Our goal is to insert code at the beginning of the method, and leave everything else intact
    public void setHealth(float newHealth) { // Then just "override" the method as usual, if it is final add an _INJECTED to the method name
        ImaginaryEvent event = ImaginaryEventBus.callEvent(new ImaginaryEvent(this, newHealth)); // Our event handling code from above
            
        if (event.isCancelled())
            return;
            
        newHealth = event.getNewHealth();
        
        throw null; // Pass execution on to the rest of the method. This will be removed at runtime but is required for compilation (At least when the method doesn't return void, so it's not necessary in this case)
        
    }
    
} 
```

And that's pretty much it, now we just need to create our runtime transformer:

```java
new RuntimeTransformer(EntityLivingTransformer.class);
```

Lastly, you have to allow self attaching by adding this startup parameter, `-Djdk.attach.allowAttachSelf=true`, to your jvm startup arguments or by creating the `RuntimeTransformer` instance in a separate process.

And we're done.

You can find more examples in the example plugin.

## "Documentation"

There are three types of Injection:

- INSERT (Inserts your code at the beginning of the method)
- OVERWRITE (Overwrites the method with your code)
- APPEND (Adds code to the end of the method, only works on methods returning void)

## Compiling

Run the below command to build the project.
`./gradlew build`

## Installation

To install the api and agent jars into your local maven repo, run
`./gradlew publishToMavenLocal`

The library can then be included using one of the following dependency definitions:
### Maven
```xml
        <dependency>
            <groupId>net.hypercubemc.runtimetransformer</groupId>
            <artifactId>api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```
### Gradle
```groovy
    compile "net.hypercubemc.runtimetransformer:api:1.0-SNAPSHOT"
    compileOnly "net.hypercubemc.runtimetransformer:agent:1.0-SNAPSHOT"
```

Don't forget to actually include the artifact in your final jar, using the `maven-shade-plugin` or an equivalent alternative suiting your build system such as the below snippet for Gradle
```groovy
jar {
    from {
        configurations.compile.collect {
            it.isDirectory() ? it : zipTree(it)
        }
    }
}
```

## Compatibility
Only supports the latest Java release. AdoptOpenJDK (https://adoptopenjdk.net/) is recommended.

## License
For all code written or changed by Justsnoopy30 and future contributors, the code is licensed under the GPL, Copyright Justsnoopy30.
See below for more information.

    RuntimeTransformer
    Copyright (C) 2020  Justsnoopy30

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

The license and copyright for code written by MiniDigger and Yamakaja can be found in the ORIGINAL_LICENSE file.
