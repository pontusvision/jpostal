#!/bin/bash
TAG=${TAG:-1.13.2}
DIR="$( cd "$(dirname "$0")" ; pwd -P )"
cd $DIR/docker
docker build --rm . -t pontusvisiongdpr/pontus-jpostal-lib:${TAG}

docker push pontusvisiongdpr/pontus-jpostal-lib:${TAG}

