pipeline {
    agent any

    tools {
        git 'GIT'
        maven 'Maven3.9'
        jdk 'JDK17'
        nodejs 'NodeJS18'
        dockerTool 'Docker'
    }

    stages {
        stage('Clone') {
            steps {
                echo 'Cloning...'
            }
        }
        stage('Build') {
            steps {
                echo "${BUILD_URL}: Building ${BUILD_ID}..."

                echo '--- Java and Maven versions ---'
                sh 'java -version'
                sh 'mvn -version'

                echo 'Building Backend:'
                sh 'mvn -f backend/pom.xml clean install -DskipTests=true'

                echo 'Building CLI:'
                sh 'mvn -f cli/pom.xml clean install -DskipTests=true'

                echo '--- Node and NPM versions ---'
                sh 'node -v'
                sh 'npm -v'

                echo 'Building Bank:'
                sh 'npm --prefix bank install'
                sh 'npm --prefix bank run build'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing Backend:'
                sh 'mvn -f backend/pom.xml test'

                echo 'Testing CLI:'
                sh 'mvn -f cli/pom.xml test'

                echo 'Testing Bank:'
                sh 'npm --prefix bank test'
            }
        }
        stage('Test End to End') {
            agent {
                label 'Host'
            }
            steps {
                echo 'Running End to End Tests...'

                sh 'chmod +x ./*.sh && chmod +x ./**/*.sh'
                sh './build-all.sh'

                echo 'Testing E2E:'
                sh 'end2endTests/main.sh'
            }
        }
        stage('Code Analysis') {
            steps {
                withSonarQubeEnv('DevOpsSonarQube') {
                    echo 'Analyzing Backend:'
                    sh 'mvn -f backend/pom.xml clean verify sonar:sonar -Dsonar.projectKey=DevOpsCodeAnalysis-Backend -Pcoverage'

                    echo 'Analyzing CLI:'
                    sh 'mvn -f cli/pom.xml clean verify sonar:sonar -Dsonar.projectKey=DevOpsCodeAnalysis-CLI -Pcoverage'
                }
            }
        }
        stage('Package') {
            steps {
                echo 'BRANCH_NAME: ${BRANCH_NAME} - ${BRANCH_NAME == "main"}'

                echo 'Packaging Backend:'
                sh '''
                    if [ ${BRANCH_NAME} == "main" ]; then
                        REPO_ID="artifactoryReleases"
                    else
                        REPO_ID="artifactorySnapshots"
                    fi
                    mvn -f backend/pom.xml -s settings.xml deploy -Drepo.id=${REPO_ID}
                '''

                echo 'Packaging CLI:'
                sh '''
                    if [ ${BRANCH_NAME} == "main" ]; then
                        REPO_ID="artifactoryReleases"
                    else
                        REPO_ID="artifactorySnapshots"
                    fi
                    mvn -f cli/pom.xml -s settings.xml deploy -Drepo.id=${REPO_ID}
                '''
            }
        }
        stage('Deploy') {
            agent {
                label 'Host'
            }
            when {
                branch 'main'
            }
            steps {
                echo 'Deploying...'

                withCredentials([usernamePassword(credentialsId: 'DockerHubToken', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                    echo '$DOCKER_USERNAME logged in to DockerHub'

                    echo 'Building Backend Container'
                    sh 'docker build -t sswaz/multicard-backend:latest -f backend/Dockerfile backend'

                    echo 'Pushing Backend Container'
                    sh 'docker push sswaz/multicard-backend:latest'

                    echo 'Building CLI Container'
                    sh 'docker build -t sswaz/multicard-cli:latest -f cli/Dockerfile cli'

                    echo 'Pushing CLI Container'
                    sh 'docker push sswaz/multicard-cli:latest'
                }
            }
        }
    }
}
