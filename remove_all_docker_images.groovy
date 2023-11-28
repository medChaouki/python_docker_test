pipeline {
    agent any

    stages {
        stage('Remove all Docker images') {
            steps {
                script {
                    // List all Docker images
                    def dockerImages = sh(script: 'docker images -q', returnStdout: true).trim()

                    // If there are any Docker images, remove them
                    if (dockerImages) {
                        sh 'docker rmi -f $(docker images -q)'
                        echo 'All Docker images have been removed'
                    } else {
                        echo 'No Docker images to remove'
                    }
                }
            }
        }
    }
}