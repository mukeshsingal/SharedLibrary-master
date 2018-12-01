package com.ae.jenkins.executors;

class DockerExecutor {

 	def script
	def params
	def isUnix

 	DockerExecutor(script, params) {
     	this.script = script
   		this.params = params
		this.isUnix = script.isUnix()
  	}

	def init(String param) {
    	script.echo "params = $params"
		//params.get("registryAuthenticator").login()
	}

	def execute() {
		def dockerTag = params.get("image")
		script.echo "dockerTag = $dockerTag"

		script.catchErrorCustom("Failed to build docker image", "Successfully created docker image") {
			script.shellCustom("docker build -t $dockerTag .", isUnix)
		}
	}

	def push() {
		def dockerTag = params.get("image")
		def dockerRepo = params.get("repo")
		script.echo "dockerRepo = $dockerRepo"
		/*script.shellCustom("docker tag $dockerRepo:latest $dockerTag", isUnix)
		script.shellCustom("docker push $dockerTag", isUnix)
		script.shellCustom("docker rmi $dockerTag", isUnix)
		script.shellCustom("docker rmi $dockerRepo:latest", isUnix);*/
	}
}
