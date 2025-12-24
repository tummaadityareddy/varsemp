pipeline {
    agent any
    environment {
        DOCKERHUB_USERNAME    = 'tummaadityareddy'
        DOCKERHUB_CREDENTIALS = 'dockerhub-creds'
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
                withMaven(maven: 'Maven-3'){
                bat 'mvn clean package -DskipTests'
                }
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
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    docker push %DOCKERHUB_USERNAME%/authentication
                    docker push %DOCKERHUB_USERNAME%/employee
                    docker push %DOCKERHUB_USERNAME%/apigateway
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                bat '''
                docker compose down
                docker compose up -d
                '''
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
