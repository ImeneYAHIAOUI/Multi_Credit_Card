pipeline {
    agent any

    tools {
        maven 'Maven3.9'
        jdk 'JDK17'
        nodejs 'NodeJS18'
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
                withSonarQubeEnv('DevOpsSonarQube') {
                    echo "Analyzing Backend:"
                    sh 'mvn -f backend/pom.xml clean verify sonar:sonar -Dsonar.projectKey=DevOpsCodeAnalysis-Backend'

                    echo "Analyzing CLI:"
                    sh 'mvn -f cli/pom.xml clean verify sonar:sonar -Dsonar.projectKey=DevOpsCodeAnalysis-CLI'
                }
            }
        }
        stage('Package') {
            when {
                branch 'main'
            }
            steps {
                echo "Packaging Backend:"
                sh 'mvn -f backend/pom.xml -s settings.xml deploy -Drepo.id=snapshots'

                echo "Packaging CLI:"
                sh 'mvn -f cli/pom.xml -s settings.xml deploy -Drepo.id=snapshots'
            }
        }
        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo "Deploying..."
                sh "ls"
                sh "ls project"
//                sh 'docker tag backend/tcf-spring-backend sswaz/multicard-tcf-spring-backend:latest'
//                sh 'docker push sswaz/tcf-spring-backend:latest'


//                sh 'docker push sswaz/multicard:latest'
            }
        }
    }
}