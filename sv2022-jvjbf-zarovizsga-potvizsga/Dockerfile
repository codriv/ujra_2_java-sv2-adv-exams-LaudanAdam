FROM eclipse-temurin:17 as builder
WORKDIR /opt/app
COPY target/sv2022-jvjbf-zarovizsga-potvizsga-0.0.1-SNAPSHOT.jar sv2022-jvjbf-zarovizsga-potvizsga.jar
RUN java -Djarmode=layertools -jar sv2022-jvjbf-zarovizsga-potvizsga.jar extract

FROM eclipse-temurin:17
WORKDIR /opt/app
COPY --from=builder /opt/app/dependencies/ ./
COPY --from=builder /opt/app/spring-boot-loader/ ./
COPY --from=builder /opt/app/snapshot-dependencies/ ./
COPY --from=builder /opt/app/application/ ./

CMD ["java", "org.springframework.boot.loader.JarLauncher"]