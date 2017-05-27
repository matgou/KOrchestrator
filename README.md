# KOrchestrator
API to execute flow from event

## Simple example
```sh
$ mkdir config.d
$ cat > config.d/example1.flow << EOF
on time 3am # event trigger
write "ping" /tmp/trigger.txt # write ping in file /tmp/trigger.txt 
EOF
$ java -jar korchestrator.jar config.d
```
