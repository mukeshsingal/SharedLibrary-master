package com.ae.jenkins.executors;

class TerraformExecutor {

 	def script

 	TerraformExecutor(script) {
     	this.script = script
  	}

	def apply(String name, params) {
		def region = params.get("region")
		def image = params.get("image")
		def stateName = params.get("name")
		
		script.sh "terraform --version"
		script.sh "terraform init -backend=true -backend-config \"bucket=jenkins-terraform-bucket\" -backend-config \"region=$region\" -backend-config \"key=$stateName-terraform.tfstate\""
		script.sh "terraform apply -auto-approve -var jenkins_image=$image"
	}
}
