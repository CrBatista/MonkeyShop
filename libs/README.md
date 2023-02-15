# Libs

We will end up having multiples libraries within this folder and maybe even different libraries languages. The important thing to understand is that the current flow for a library is: 
1. code changes
2. upload artifact to AWS Code Artifact
3. a service will use the library as a dependency 

For this to work you need to configure the repositories (like a mvn repository) for both the service and the library.
