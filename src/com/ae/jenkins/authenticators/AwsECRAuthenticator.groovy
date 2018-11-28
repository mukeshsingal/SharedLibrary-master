package com.ae.jenkins.authenticators;

class AwsECRAuthenticator {

	def script
	def region

	AwsECRAuthenticator(script, region) {
		this.script = script
		this.region = region
	}

	def login() {
		script.echo '$(/usr/bin/aws ecr get-login --no-include-email --region ' + region + ')'
		script.sh '$(/usr/bin/aws ecr get-login --no-include-email --region ' + region + ')'
	}
}
