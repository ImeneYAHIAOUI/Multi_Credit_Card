pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                sh 'echo "Cloning..."'
            }
        }
        stage('Build') {
            steps {
                sh 'echo "${BUILD_URL}: Building ${BUILD_ID}..."'
            }
        }
        stage('Test') {
            steps {
                sh 'echo "Testing..."'
            }
        }
        stage('Package') {
            steps {
                sh 'echo "Testing..."'
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo "Deploying..."'
            }
        }
    }
}