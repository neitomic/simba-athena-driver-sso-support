package com.github.neitomic.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.simba.athena.amazonaws.auth.BasicAWSCredentials;
import com.simba.athena.amazonaws.auth.BasicSessionCredentials;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.profiles.Profile;
import software.amazon.awssdk.profiles.ProfileFile;

import java.nio.file.Paths;
import java.util.Optional;

public class AwsProfileCredentialsProvider implements AWSCredentialsProvider {

    private final AwsCredentialsProvider credentialsProviderV2;

    public AwsProfileCredentialsProvider(String profilesConfigFilePath, String profileName) {
        this(ProfileFile.builder().content(Paths.get(profilesConfigFilePath)).type(ProfileFile.Type.CONFIGURATION).build(), profileName);
    }

    public AwsProfileCredentialsProvider(String profileName) {
        this(ProfileFile.defaultProfileFile(), profileName);
    }

    public AwsProfileCredentialsProvider(ProfileFile profileFile, String profileName) {
        Optional<Profile> profile = profileFile.profile(profileName);
        if (!profile.isPresent()) {
            throw new IllegalArgumentException(String.format("Profile %s not found", profileName));
        }

        credentialsProviderV2 = ProfileCredentialsProvider.builder().profileFile(profileFile).profileName(profileName).build();
    }

    @Override
    public AWSCredentials getCredentials() {
        AwsCredentials awsCredentialsV2 = credentialsProviderV2.resolveCredentials();
        if (awsCredentialsV2 instanceof AwsSessionCredentials) {
            return new BasicSessionCredentials(awsCredentialsV2.accessKeyId(), awsCredentialsV2.secretAccessKey(), ((AwsSessionCredentials) awsCredentialsV2).sessionToken());
        } else if (awsCredentialsV2 instanceof AwsBasicCredentials) {
            return new BasicAWSCredentials(awsCredentialsV2.accessKeyId(), awsCredentialsV2.secretAccessKey());
        } else {
            throw new IllegalStateException("Unsupported V2 Credentials type " + awsCredentialsV2.getClass().getName());
        }
    }

    @Override
    public void refresh() {

    }
}
