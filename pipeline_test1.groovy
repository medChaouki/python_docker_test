pipeline {
    agent any

    stages {
        stage('Check if the docker image is available') {
            steps {
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

        stage('Start docker image'){
            steps {
                script {
                    sh 'docker run -d --name my_container my_docker_img'
                }
            }
        }

        stage('Start python app'){
            steps {
                script {
                    sh 'docker exec my_container python basic_test.py 3'
                }
            }
        }

    }

    // post clean up the docker image and container
    post {
        always {
            // shutdonw the container
            script {   
                sh 'docker stop my_container'
                sh 'docker rm my_container'
            }
        }
    }
}