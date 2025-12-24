pipeline {
    agent any
    environment {
        IMAGE_VERSION = "1.0.${BUILD_NUMBER}"
        DOCKER_USER   = "tummaadityareddy"
        DOCKERHUB_CREDENTIALS = 'dockerhub-creds'
    }

    tools {
        maven 'Maven-3'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-creds',
                    url: 'https://github.com/tummaadityareddy/varsemp.git'
            }
        }

        stage('Build Authentication Service') {
            steps {
                dir('authentication') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Employee Service') {
            steps {
                dir('employee') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build API Gateway Service') {
            steps {
                dir('apigateway') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Docker Build') {
            steps {
                bat """
                docker build -t tummaadityareddy/authentication:${IMAGE_VERSION} authentication
                docker build -t tummaadityareddy/employee:${IMAGE_VERSION} employee
                docker build -t tummaadityareddy/apigateway:${IMAGE_VERSION} apigateway
                """
            }
        }
        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: DOCKERHUB_CREDENTIALS,
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat """
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin

                    docker push %DOCKER_USER%/authentication:%IMAGE_VERSION%
                    docker push %DOCKER_USER%/employee:%IMAGE_VERSION%
                    docker push %DOCKER_USER%/apigateway:%IMAGE_VERSION%
                    """
                }
            }
        }

        stage('Deploy') {
            steps {
                bat """
                 set IMAGE_VERSION=${IMAGE_VERSION}
                  docker compose down
                  docker compose up -d
                 """
            }
        }
    }

    post {
        success {
            echo 'CI/CD Pipeline completed successfully'
        }
        failure {
            echo 'CI/CD Pipeline failed'
        }
    }
}