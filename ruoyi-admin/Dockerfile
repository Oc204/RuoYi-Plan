## 依赖镜像
FROM ascdc/jdk8:latest

MAINTAINER oc204

RUN mkdir -p /home/project/ruoyi

WORKDIR /home/project/ruoyi

EXPOSE 8080

ADD ./target/ruoyi-admin.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]


