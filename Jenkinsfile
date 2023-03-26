pipeline {
    agent any

    tools {
        maven 'Maven 3.9.0'
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

//                echo 'Building Docker image...'
//                sh 'docker build -t <docker_hub_username>/<image_name>:<image_tag> .'
//
//                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: 'https://index.docker.io/v1/']) {
//                    echo 'Pushing Docker image...'
//                    sh 'docker push <docker_hub_username>/<image_name>:<image_tag>'
//                }
//
//                sshagent(['remote-ssh-credentials']) {
//                    echo 'Pulling and running Docker image...'
//                    sh "ssh <remote_username>@<remote_ip_address> 'docker pull <docker_hub_username>/<image_name>:<image_tag>'"
//                    sh "ssh <remote_username>@<remote_ip_address> 'docker run -d --name <container_name> -p <host_port>:<container_port> <docker_hub_username>/<image_name>:<image_tag>'"
//                }
            }
        }
    }
}