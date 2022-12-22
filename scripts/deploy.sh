#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i C:/Users/25tsc/.ssh/id_ed25519 \
  target/jdm-0.0.1-SNAPSHOT.jar \
  barrista@192.168.1.75:/home/barrista/

echo 'Restart server...'

ssh -i C:/Users/25tsc/.ssh/id_ed25519 barrista@192.168.1.75 << EOF

pgrep java | xargs kill -9
nohup java -jar jdm-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo 'Bye'