import com.ae.jenkins.executors.DockerExecutor
import com.ae.jenkins.authenticators.AwsECRAuthenticator
import com.ae.jenkins.executors.TerraformExecutor


def commitHash
def version
def buildExecutor

pipeline {
	
	agent { label 'default'}
	
	options {
		timestamps()
		ansiColor('xterm')
	}
	
	stages {

		stage("Init") {
			steps {
				script {
					// global jenkins vars
					shellCustom('SET', isUnix())
					def branch = env.BRANCH_NAME
					def environment = env.ENVIRONMENT

					// meta vars
					version = 1.0
					//commitHash = shellCustom("git rev-parse HEAD", isUnix(), [returnStdout: true])
					commitHash = 12345

					buildExecutor = new DockerExecutor(this, [
						repo: "ae/infra/jenkins/agent/linux",
						image: "artifactory.aesansun.com/dockervirtualappcicdl/elytestappl:75832",
						region: "us-east-1",
						registryAuthenticator: new AwsECRAuthenticator(this, "us-east-1"),
						buildArgs: [
						  BUILD_NUMBER: "${env.JOB_NAME}#${env.BUILD_NUMBER}",
						  COMMIT_HASH: commitHash
						]
					])
				}
			}
		}
		
		stage("Initialize") {
			steps {
				script {
					buildExecutor.init("docker")
				}
			}
		}

		stage("Build") {
			steps {
				script {
					buildExecutor.execute()
				}
			}
		}

		stage("Publish") {
			steps {
				script {
					buildExecutor.push()
				}
			}
		}

		stage("Deploy") {
			steps {
				script {
                    def artifactoryServer
                    def rtDocker
				    catchErrorCustom("Failed to initiate Artifactory and Docker") {
					     artifactoryServer = Artifactory.server "my-onprem-artifactory"
					     rtDocker = Artifactory.docker server: artifactoryServer, username: 'admin', password: 'password'
					}

					def buildInfo = Artifactory.newBuildInfo()
					def dockerTag = "artifactory.aesansun.com/dockervirtualappcicdl/elytestappl:75832"

                    catchErrorCustom("Failed to push image to Artifactory", "Successfully uploaded image to Artifactory") {
					    buildInfo = rtDocker.push(dockerTag, "docker-repo")
					}
				}
			}
		}
	}
}
