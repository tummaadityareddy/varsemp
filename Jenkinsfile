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

        stage('Build JARs') {
            steps {
                bat 'mvn -version'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                bat '''
                docker build -t %DOCKERHUB_USERNAME%/authentication ./authentication
                docker build -t %DOCKERHUB_USERNAME%/employee ./employee
                docker build -t %DOCKERHUB_USERNAME%/apigateway ./apigateway
                '''
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: DOCKERHUB_CREDENTIALS,
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat '''
                    echo %DOCKER_PASS% | docker login -u %D_
