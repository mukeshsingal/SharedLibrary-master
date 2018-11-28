package com.ae.jenkins.executors;

class DockerExecutor {

 	def script
	def params

 	DockerExecutor(script, params) {
     	this.script = script
   		this.params = params
  	}

	def init(String param) {
    	script.echo "params = $params"
		params.get("registryAuthenticator").login()
	}

	def execute() {
		def dockerTag = params.get("image")
		def dockerRepo = params.get("repo")
		script.echo "dockerRepo = $dockerRepo"
		script.sh "docker build -t $dockerRepo ."
	}

	def push() {
		def dockerTag = params.get("image")
		def dockerRepo = params.get("repo")
		script.echo "dockerRepo = $dockerRepo"
		script.sh "docker tag $dockerRepo:latest $dockerTag"
		script.sh "docker push $dockerTag"
		script.sh "docker rmi $dockerTag"
		script.sh "docker rmi $dockerRepo:latest"
	}
}
