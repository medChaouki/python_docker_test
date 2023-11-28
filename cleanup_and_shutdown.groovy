pipeline {
    agent any

    stages {
        stage('Trigger remove containers') {
            steps {
                script {
                    // Trigger Remove all docker containers
                    build job: 'Remove all docker containers'
                }
            }
        }

        stage('Trigger remove images') {
            steps {
                script {
                    // Trigger Remove all docker images
                    build job: 'Remove all docker images'
                }
            }
        }
    }
}