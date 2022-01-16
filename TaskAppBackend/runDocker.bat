@echo on
docker run --network task-app -d -p 8080:8080 --name task-app-back-container task-app/backend

cls

docker logs -f task-app-back-container  