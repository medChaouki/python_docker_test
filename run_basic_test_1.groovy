pipeline {
    agent any

    stages {
        stage('Check if the docker image is available') {
            steps {
                script {
                    
                    sh 'echo $DISPLAY'
                    sh 'export DISPLAY=localhost:12.0'
                    sh 'echo $DISPLAY'
                    
                    def dockerImageExists = sh(script: 'sudo docker images -q my_docker_img', returnStdout: true).trim()
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
                    sh 'sudo docker run -d --name my_container my_docker_img'
                }
            }
        }

        stage('Start python app'){
            steps {
                script {
                    sh 'sudo docker exec my_container python basic_test.py 3'
                }
            }
        }

    }

    // post clean up the docker image and container
    post {
        always {
            // shutdonw the container
            script {  
                sh 'sudo docker ps -a'
                sh 'sudo docker stop my_container'
                sh 'sudo docker ps -a'
                sh 'sudo docker rm my_container'
                sh 'sudo docker ps -a'
                
            }
        }
    }
}
