# java21基础镜像
FROM m.daocloud.io/docker.io/eclipse-temurin:21.0.4_7-jdk-alpine

# 作者信息
MAINTAINER daxpay@daxpay.cn

# 时区设置
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 生产环境
ENV SPRING_PROFILES_ACTIVE=prod

# 工作目录
WORKDIR /

# 端口暴露
EXPOSE 9999

# 添加执行 jar 包
ADD daxpay-open-client/target/daxpay-open-server.jar daxpay-server.jar

# 执行启动命令
CMD java -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=UTF-8 -jar daxpay-server.jar
