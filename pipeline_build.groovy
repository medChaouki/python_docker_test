pipeline {
    agent any

    stages {
        stage('Clone Git Repository') {
            steps {
                // Clone the Git repository
                git branch: 'main', url: 'https://github.com/medChaouki/python_docker_test'
            }
        }

        stage('Clean up and build Docker image Locally') {
            steps {
                script {    

                    // Remove all Docker images
                    sh 'docker image prune -a -f'

                    // Build Docker image locally using the Dockerfile from the cloned repository
                    def goExitCode = sh(script:  'docker build -t my_docker_img .', returnStatus: true)
                    if (goExitCode != 0) {
                        sh 'echo hello'
                    }

                }
            }
        }
    }
    
    post {
        always {
            // check if the Docker image exists locally
            script {
                def dockerImageExists = sh(script: 'docker images -q my_docker_img', returnStdout: true).trim()
                if (dockerImageExists) {
                    //Print log
                    echo 'Docker image exists locally'
                    //Return success
                    currentBuild.result = 'SUCCESS'
                } else {
                    //Print log
                    echo 'Docker image does not exist locally'
                    //Return failure
                    currentBuild.result = 'FAILURE'
                }
            }
        }
    }

}