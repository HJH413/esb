# 자바 버전
FROM openjdk:17-jdk-slim

# 파일 복사
COPY ./*.jar app.jar

# 시간대 설정
ENV JAVA_OPTS="-Duser.timezone=Asia/Seoul"

# 자바 실행 명령 및 프로퍼티 설정 지정 (포트 포함)
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar --spring.profiles.active=prod --server.port=11830"]