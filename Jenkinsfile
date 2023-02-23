pipeline {
    agent any

    tools {
        maven 'Maven 3.9.0'
        sonnarqube 'SQS4.8'
    }

    stages {
        stage('Clone') {
            steps {
                echo "Cloning..."
            }
        }
        stage('Build') {
            steps {
                echo "${BUILD_URL}: Building ${BUILD_ID}..."

                echo "--- Java and Maven versions ---"
                sh 'java -version'
                sh 'mvn -version'

                echo "Building Backend:"
                sh 'mvn -f backend/pom.xml clean install -DskipTests=true'

                echo "Building CLI:"
                sh 'mvn -f cli/pom.xml clean install -DskipTests=true'

                echo "--- Node and NPM versions ---"
                sh 'node -v'
                sh 'npm -v'

                echo "Building Bank:"
                sh 'npm --prefix bank install'
                sh 'npm --prefix bank run build'
            }
        }
        stage('Test') {
            steps {
                echo "Testing Backend:"
                sh 'mvn -f backend/pom.xml test'

                echo "Testing CLI:"
                sh 'mvn -f cli/pom.xml test'

                echo "Testing Bank:"
                sh 'npm --prefix bank test'
            }
        }
        stage('Code Analysis') {
            steps {
                echo "Analyzing Backend:"
                sh 'mvn -f backend/pom.xml sonar:sonar -Dsonar.projectKey=DevOpsCodeAnalysis'

                echo "Analyzing CLI:"
                sh 'mvn -f cli/pom.xml sonar:sonar -Dsonar.projectKey=DevOpsCodeAnalysis'
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