# MonkeyShop

# IAM

There is a userAggregate created in AWS named "monkey". This userAggregate has a PermissionPolicy attached named "FullAccessMonkeyArtifact" with the next JSON:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "VisualEditor1",
            "Effect": "Allow",
            "Action": "sts:GetServiceBearerToken",
            "Resource": "*"
        },
        {
            "Sid": "VisualEditor2",
            "Effect": "Allow",
            "Action": [
                "codeartifact:ListRepositories",
                "codeartifact:GetAuthorizationToken",
                "codeartifact:ListDomains"
            ],
            "Resource": "arn:aws:codeartifact:eu-west-1:970817753053:domain/monkeyshop"
        }
    ]
}
```

This policy allows, through Command Line, to execute get-authorization-token action, read and write artifacts to the Monkey domain:
CodeArtifact Domain: `arn:aws:codeartifact:eu-west-1:970817753053:domain/monkeyshop`

# Code Artifact
In order to download/push artifacts, we need to execute the next command:

```bash
export AWS_ACCESS_KEY_ID=AKIA6ECJ7J7OQ7QW4CF2
export AWS_SECRET_ACCESS_KEY=XXX.YYY.ZZZ
export CODEARTIFACT_AUTH_TOKEN=`aws codeartifact get-authorization-token --domain monkeyshop --domain-owner 970817753053 --region eu-west-1 --query authorizationToken --output text`
```

