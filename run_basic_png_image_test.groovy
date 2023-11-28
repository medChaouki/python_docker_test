pipeline {
    agent any

    stages {
        stage('Check if the docker image is available') {
            steps {
                script {
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
                    sh 'sudo docker run -d --name my_container --env="DISPLAY" --volume="/tmp/.X11-unix:/tmp/.X11-unix:rw" my_docker_img'
                }
            }
        }

        stage('Start python app'){
            steps {
                script {
                    sh 'sudo docker exec my_container ls'
                    sh 'sudo ls'
                    sh 'export DISPLAY=:0'
                    sh 'sudo docker exec my_container python py_image_test.py'
                    
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