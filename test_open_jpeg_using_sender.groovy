pipeline {
    agent any

    stages {

        stage('Run Python Script') {
            steps {
                // Run your Python script
                script {
                    sh 'python /home/chawki/Downloads/test_jenkins/sender.py'
                }
            }
        }

    }


}