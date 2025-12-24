pipeline {
    agent any

    environment {
        DOCKERHUB_USERNAME    = 'tummaadityareddy'
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
                bat 'docker build -t %DOCKERHUB_USERNAME%/authentication ./authentication'
                bat 'docker build -t %DOCKERHUB_USERNAME%/employee ./employee'
                bat 'docker build -t %DOCKERHUB_USERNAME%/apigateway ./apigateway'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: DOCKERHUB_CREDENTIALS,
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat 'echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin'
                    bat 'docker push %DOCKERHUB_USERNAME%/authentication'
                    bat 'docker push %DOCKERHUB_USERNAME%/employee'
                    bat 'docker push %DOCKERHUB_USERNAME%/apigateway'
                }
            }
        }

        stage('Deploy') {
            steps {
                bat 'docker compose down'
                bat 'docker compose up -d'
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