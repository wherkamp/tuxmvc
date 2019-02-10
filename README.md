# TuxMVC

## TuxMVC thought process explained
Alright TuxMVC is a MVC. At the moment the model part is not supported. 
Their are two parts. TuxMVC and implantation. The implantation takes care of all the fun server parts and view. 

# Only Current implementation
[TuxMVC-Simple](https://github.com/wherkamp/tuxmvc-simple)

## Coding and crap
Javadocs: https://docs.kingtux.me/tuxmvc/
### Maven
```xml

   <repository>
      <id>kingtux-repo</id>
      <url>http://repo.kingtux.me/repository/maven-public/</url>
    </repository>
    <dependency>
      <groupId>me.kingtux</groupId>
      <artifactId>tuxmvc-core</artifactId>
      <!---Make sure you use Latest Version!-->
      <version>1.0</version>
      <scope>compile</scope>
    </dependency>
```
### Gradle
```
repositories {
  maven { url 'http://repo.kingtux.me/repository/maven-public/' }
}

dependencies {
   compile "me.kingtux:SimpleAnnotation:1.3"
}
```
## Examples:
https://github.com/wherkamp/tuxshortener

## Planned Features: 
##### Version 1.0:
- Website#getRoute("my/cool/route") = {"https", "http"}+"://"+ host + {String provided}
- CORS(I honestly know nothing)
- Default Template Variables
- Error Handlers {! URGANT }
- Path#template="" (Add that) to make it simpler
-  RequestParam.Type.SESSION and RequestParam.Type.COOKIE
- README.md
- Javadoc
##### Version 1.1:
-  RequestParam#addToTemplate() default  true
- RequestParam#required() implentation
##### Version: 1.2:
- Email Library
- Probably fix a lot of crap
##### Version 1.3 - Some things might get postponed:
- Hopefully ORM
- Hopefully Redis Support



Feel free to donate at https://www.paypal.me/wherkamp