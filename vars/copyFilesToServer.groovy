def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def utils = new com.CloudInfraWP.terraform.Utils()
    utils.copy("${config.configFile}")
}