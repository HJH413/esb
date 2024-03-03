#!/bin/sh
echo "Stopping running Docker containers..."
docker stop $(docker ps -a -q)
docker rm $(docker ps -aq)
echo "Rebuilding Docker images..."
docker-compose -f /esb/esb-compose.yml build
echo "Starting Docker containers..."
docker-compose -f /esb/esb-compose.yml up -d
echo "Update process completed."