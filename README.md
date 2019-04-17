# TuxMVC

## TuxMVC thought process explained
Alright TuxMVC is a MVC (model view controller). At the moment, the model component isn't integrated. There are two constituents, TuxMVC and implementation. The implementation takes care of all the fun server parts and view. 

## Only Current Implementation
[TuxMVC-Simple](https://github.com/wherkamp/tuxmvc-simple)

## Documentation
#### Javadocs
https://docs.kingtux.me/tuxmvc/
#### Wiki
https://github.com/wherkamp/tuxmvc

## Installation
#### Maven
```xml
<repository>
   <id>kingtux-repo</id>
   <url>http://repo.kingtux.me/repository/maven-public/</url>
</repository>

<dependency>
   <groupId>me.kingtux</groupId>
   <artifactId>tuxmvc-core</artifactId>
   <version>1.0</version>
   <scope>compile</scope>
</dependency>
```
#### Gradle
```java
repositories {
  maven { url 'http://repo.kingtux.me/repository/maven-public/' }
}

dependencies {
   compile "me.kingtux:tuxmvc-core:1.0"
}
```
## Examples:
https://github.com/wherkamp/tuxshortener

## Features
#### Version 1.0:
- `Website.getRoute("my/cool/route") = {"https", "http"}+"://"+ host + {String provided}`.
- CORS.
- Default Template Variables.
- Error Handlers.
- Added `Path#template=""`.
- Added `RequestParam.Type.SESSION` and `RequestParam.Type.COOKIE`.
- Javadocs.
#### Version 1.1
- `RequestParam#addToTemplate()`, default is true.
- Added `RequestParam#required()`.
#### Version: 1.2
- Email Library.
- Bug Fixes.

## Planned Features 
#### Version 1.3 - Some things may be postponed.
- ORM Integration.
- Hopefully Redis Support.

Feel free to donate at https://www.paypal.me/wherkamp. All this code is free and developed on my own time, support me to make sure they continue to be developed.
