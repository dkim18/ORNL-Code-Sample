# Getting Started


This is code sample that I wrote and this is related in some way to the position(Research Software Engineer for Web Applications-Requisition Id 3117) that I am applying. That is why I decided to go with Spring boot framework with data visualization application.

This sample displays growth chart (child's stature percentile based on age.) based on the following data:
https://www.cdc.gov/growthcharts/data/zscore/statage.csv

This sample demonstrates:
* Implementing data visualization application using J2EE technologies
* Use of classes
* Use of thymeleaf and Bootstrap
* Input and output operations
* Unit testing
* Use of Javadoc for documentation
* Use of Maven build
* Use of Git for version control
* Use of READMEs for out of source docs


Queries may be sent to the author at tkkscf at g m a i l dot com.

## Build Instructions

* compile the project and generate target folder<br>
mvn compile


* Tests<br>
mvn clean install


* Cleaning<br>
mvn clean


* Execution (triggers the download of Apache Tomcat and initializes the startup of Tomcat.)<br>
mvn spring-boot:run

When the log shows the line containing ‘Started Application', this web application is ready to be queried via the browser at the address http://localhost:8080/
This default URL displays my daughter's height for age. Other input can be entered by the following:
http://localhost:8080/chart/{ageInMonth}/{height}/{sex}
* {sex} -> 1:boy, 2:girl

## Documentation

All classes are documented using Javadoc annotations. 

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.7.BUILD-SNAPSHOT/maven-plugin/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-template-engines)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)

