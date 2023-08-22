FROM gradle:8.2.1-jdk11
WORKDIR /leasing-contract
COPY . .
RUN gradle build -x test

CMD gradle bootRun
