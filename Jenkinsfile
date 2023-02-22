pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                git branch: 'master'
                url: 'https://github.com/pns-isa-devops/isa-devops-22-23-team-f-23.git'
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