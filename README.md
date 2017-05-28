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

## Webservice example
```sh
$ cat > config.d/example2.flow << EOF
on */5 * * * *
get_json_data https://api.ipify.org?format=json > ipData
log mon ip est @ipData.ip
write /tmp/monIp.txt @ipData.ip
EOF
$ java -jar korchestrator.jar -e "run_flow example2.flow"
```
