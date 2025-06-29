# Stage 1: ใช้ Base Image ที่เป็น Java Development Kit (JDK) เพื่อ Build
FROM maven:3.9.6-eclipse-temurin-21 AS build

# ตั้งค่า Working Directory ภายใน Image
WORKDIR /app

# Copy ไฟล์ pom.xml ก่อนเพื่อใช้ประโยชน์จาก Docker Layer Caching
# ถ้า pom.xml ไม่เปลี่ยน Docker จะไม่ต้องโหลด dependencies ใหม่ทุกครั้ง
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy Source Code ทั้งหมด
COPY src ./src

# สั่ง Build project และให้ข้ามการ test (เพราะเราจะ test ใน CI stage แยก)
RUN mvn clean package -DskipTests

# Stage 2: ใช้ Base Image ที่มีแค่ Java Runtime Environment (JRE) ซึ่งเล็กกว่ามากสำหรับ Production
FROM eclipse-temurin:21-jre

# ตั้งค่า Working Directory
WORKDIR /app

# Copy เฉพาะไฟล์ .jar ที่ build เสร็จแล้วจาก Stage 1 มาใช้งาน
COPY --from=build /app/target/*.jar app.jar

# บอก Docker ว่าแอปพลิเคชันของเราจะทำงานที่ Port 8080
EXPOSE 8080

# คำสั่งที่จะรันเมื่อ Container เริ่มทำงาน
ENTRYPOINT ["java", "-jar", "app.jar"]
