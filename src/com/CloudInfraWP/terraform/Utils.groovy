package com.CloudInfraWP.terraform

def copy(String configFile) {

    def props = readProperties  file: configFile
    def Servers=  props['Servers'].split(',')
    def Directory= props['Directory']
    def Exceptions= props['Exceptions']
    def User= props['User']
    def Key= props['Key']

    echo "Directory=${Directory}"
    echo "Exceptions=${Exceptions}"
    echo "User=${User}"
    echo "Key=${Key}"
    sh "chmod 600 ${Key}"
    for ( int i = 0; i < Servers.size(); i++ ) {
        //sh "scp -o StrictHostKeyChecking=no -i ${Key} testFile ${User}@${Servers[i]}:${Directory}"
        echo "scp -o StrictHostKeyChecking=no -i ${Key} testFile ${User}@${Servers[i]}:${Directory}"
    }
}

def checkoutRepositoryCode(String repository, credentialsId) {
    git url: repository, credentialsId: credentialsId
}

return this