# KOrchestrator
API to execute flow from event

## Simple example
```sh
$ mkdir config.d
$ cat > config.d/example1.flow << EOF
on time 3am
write /tmp/trigger.txt ping 
EOF
$ java -jar korchestrator.jar -e "run_flow example1.flow"
```
