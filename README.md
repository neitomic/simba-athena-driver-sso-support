# SSO Credential support for Simba Athena JDBC Driver

## How to use?

### Build Jar package

```bash
mvn package
```

### Config athena driver

1. Add extra jar into Athena Driver
   1. Database -> Driver manager
   2. Select AWS/Athena -> Edit...
   3. Libraries -> Add File -> Select `simba-athena-driver-sso-support-1.0-jar-with-dependencies.jar` file (packaged from previous step)

2. Configure connection driver properties
   1. `AwsCredentialsProviderClass=com.github.neitomic.aws.SsoCredentialsProvider`
   2. `AwsCredentialsProviderArguments=<profile>`
   3. `<profile> := <aws_config_file_path>,<profile_name> | <profile_name>`

Examples:
1. Use `dev` profile from default config file (~/.aws/config)
```
AwsCredentialsProviderClass=com.github.neitomic.aws.SsoCredentialsProvider
AwsCredentialsProviderArguments=dev
```
2. Use `dev` profile from custom config file (~/org/aws/config)
```
AwsCredentialsProviderClass=com.github.neitomic.aws.SsoCredentialsProvider
AwsCredentialsProviderArguments=~/org/aws/config,dev
```

## What next?
It would be perfect if we could add this into the driver itself.


## Contribution
Any comment, suggestion, fix,... are welcomed. Feel free to create issue, MR. Thanks!