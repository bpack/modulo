OWNER ?= bpack
REPO ?= modulo
VERSION ?= latest

M_BUILD_DIR ?= modulo-application/target/
G_BUILD_DIR ?= modulo-application/build/

build: gbuild mbuild

gbuild:
	./gradlew clean test jar modules

mbuild:
	./mvnw clean package -Dmaven.test.skip=true

clean:
	./gradlew clean
	./mvnw clean

run: build
	./gradlew bootrun

docker.build: build
	docker build -t $(OWNER)/$(REPO)-gradle:$(VERSION) -f docker/dockerfile.gradle .
	docker build -t $(OWNER)/$(REPO)-maven:$(VERSION) -f docker/dockerfile.maven .

docker.run: docker.build
	docker-compose -f docker/docker-compose.yml up

cleanall: clean
	docker-compose -f docker/docker-compose.yml down --remove-orphans
	docker-compose -f docker/docker-compose.yml rm
	docker rmi -f $(OWNER)/$(REPO)-gradle
	docker rmi -f $(OWNER)/$(REPO)-maven

.PHONY: build clean gbuild mbuild run
