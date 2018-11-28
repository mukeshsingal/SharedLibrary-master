package com.ae.jenkins.executors;

class TerraformExecutor {

    def script
    def isUnix

    TerraformExecutor(script) {
        this.script = script
        this.isUnix = script.isUnix;
    }

    def apply(String name, params) {
        def region = params.get("region")
        def image = params.get("image")
        def stateName = params.get("name")

        script.shellCustom("terraform --version", isUnix)

        script.shellCustom("terraform init " +
                "-backend=true " +
                "-backend-config \"bucket=jenkins-terraform-bucket\" " +
                "-backend-config \"region=$region\" " +
                "-backend-config \"key=$stateName-terraform.tfstate\"", isUnix);
        script.shellCustom("terraform apply " + "-auto-approve -var jenkins_image=$image", isUnix)
    }
}
