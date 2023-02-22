pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                echo "Cloning..."
            }
        }
        stage('Build') {
            steps {
                echo "${BUILD_URL}: Building ${BUILD_ID}..."

                echo "Building Backend:"
                sh 'mvn -f backend/pom.xml clean install -DskipTests=true'

                echo "Building Bank:"
                sh 'npm --prefix bank install'
                sh 'npm --prefix bank run build'

                echo "Building CLI:"
                sh 'mvn -f cli/pom.xml clean install -DskipTests=true'
            }
        }
        stage('Test') {
            steps {
                echo "Testing Backend:"
                sh 'mvn -f backend/pom.xml test'

                echo "Testing Bank:"
                sh 'npm --prefix bank test'

                echo "Testing CLI:"
                sh 'mvn -f cli/pom.xml test'
            }
        }
        stage('Package') {
            steps {
                echo "Packaging..."
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying..."
            }
        }
    }
}