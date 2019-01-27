# PostBarServer
![license](https://img.shields.io/github/license/postbar/PostBarServer.svg)

（fake）百度贴吧，SpringBoot BackEnd

## Requirements
+ `JDK 1.8`
+ `MySQL 5.7`

## Releases
+ 0.0.1

## Deploy

### Deploy From Source
+ `IDEA`
+ `lombok` IDEA Plugin

``` bash
git clone https://github.com/postbar/PostBarServer.git
cd PostBarServer
# In IDEA
maven install
```
Generated jar package: `PostBarServer/target/pbserver-0.0.1-SNAPSHOT.jar`

### Or Download [`Release`](https://github.com/postbar/PostBarServer/releases)

### Configure DataSource

`MySQL 5.7` is required for PostBarServer

Build your configure with an application.properties file

``` bash
# in  application.properties

spring.datasource.driver-class-name = com.mysql.jdbc.Driver

server.port = 8088 # default server port

spring.jpa.database = MYSQL
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto = update
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect

spring.datasource.username = [your mysql username]
spring.datasource.password = [your mysql password]

spring.datasource.url = jdbc:mysql://localhost:3306/[your database name]?useUnicode=true&characterEncoding=utf8
spring.datasource.type = com.zaxxer.hikari.HikariDataSource
spring.datasource.hikari.connection-init-sql = SET NAMES utf8mb4

logging.level.org.springframework.web=ERROR

```

### Run

``` bash
java jar -Dspring.config.location=./application.properties pbserver-0.0.1-SNAPSHOT.jar
```




## License

GPL-3.0
