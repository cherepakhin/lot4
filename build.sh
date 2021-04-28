mvn clean package
cd ./front-app/ && docker build -t front-app . && cd ..
docker-compose up -d