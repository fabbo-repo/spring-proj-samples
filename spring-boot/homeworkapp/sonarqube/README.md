## SonarQube Credentials:
* Default user: admin
* Default password: admin

## Command:
~~~
mvn clean verify sonar:sonar \
-Dsonar.projectKey=homeworkapp \
-Dsonar.projectName='homeworkapp' \
-Dsonar.host.url=http://localhost:48010 \
-Dsonar.token=<token>
~~~