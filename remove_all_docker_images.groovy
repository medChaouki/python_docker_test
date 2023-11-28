pipeline {
    agent any

    stages {
        stage('Remove all Docker images') {
            steps {
                script {
                    sh 'sudo docker image prune -a -f'
                }
            }
        }
    }
}