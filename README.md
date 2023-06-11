# AWS Credentials support for Simba Athena JDBC Driver

## How to use?

### Build Jar package

```bash
mvn package
```

### Use pre-built jar package
You can download the pre-built jar file from the latest release [here](https://github.com/neitomic/simba-athena-driver-sso-support/releases)


### Supporting profile credentials types

Basically all types of credentials supported in `software.amazon.awssdk.auth.credentials.internal.ProfileCredentialsUtils`:
- WebIdentityTokenCredentialsProvider
- SsoProfileCredentialsProvider
- StsProfileCredentialsProvider
- ProcessCredentialsProvider
- StaticCredentialsProvider


### Config athena driver

1. Add extra jar into Athena Driver
   1. Database -> Driver manager
   2. Select AWS/Athena -> Edit...
   3. Libraries -> Add File -> Select `simba-athena-driver-sso-support-1.0-jar-with-dependencies.jar` file (packaged from previous step)

2. Configure connection driver properties
   1. `AwsCredentialsProviderClass=com.github.neitomic.aws.AwsProfileCredentialsProvider`
   2. `AwsCredentialsProviderArguments=<profile>`
   3. `<profile> := <aws_config_file_path>,<profile_name> | <profile_name>`

Examples:
1. Use `dev` profile from default config file (~/.aws/config)
```
AwsCredentialsProviderClass=com.github.neitomic.aws.AwsProfileCredentialsProvider
AwsCredentialsProviderArguments=dev
```
2. Use `dev` profile from custom config file (~/org/aws/config)
```
AwsCredentialsProviderClass=com.github.neitomic.aws.AwsProfileCredentialsProvider
AwsCredentialsProviderArguments=~/org/aws/config,dev
```

## What next?
It would be perfect if we could add this into the driver itself.


## Contribution
Any comment, suggestion, fix,... are welcomed. Feel free to create issue, PR. Thanks!

Thank @sjincho for the idea of using ProfileCredentialsProvider to support different kinds of credentials (in a [fork](https://github.com/sjincho/simba-athena-driver-sso-support/)).