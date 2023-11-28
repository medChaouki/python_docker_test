pipeline {
    agent any

    stages {
        stage('Remove all Docker containers') {
            steps {
                script {
                    // List all Docker containers
                    def dockerContainers = sh(script: 'docker ps -aq', returnStdout: true).trim()

                    // If there are any Docker containers, remove them
                    if (dockerContainers) {
                        sh 'docker rm -f $(docker ps -aq)'
                        echo 'All Docker containers have been removed'
                    } else {
                        echo 'No Docker containers to remove'
                    }
                }
            }
        }
    }
}