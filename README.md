# Proxy Test

## Deploy
```shell
./gradlew shadowJar
```

## Run 
```shell
cd build/libs
java -jar proxy-test.jar -h your.proxy.server -p 3128 -t https://example.com
```

## Options

### --disableSSL
       Disable SSL Verification
       Default: true
### --help
       Print usage instructions
       Default: false
### --user-agent
       User Agent
       Default: Mozilla/5.0

###  * -h
       Proxy Host
###  * -p
       Proxy Port
       Default: 3128
###    -u
       Proxy Username
### -P
       Proxy Password   
###  * -t
       Target URL to test

