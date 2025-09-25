pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'Java17'
    }

    environment {
        JAR_NAME = 'HibernateBeautyECommerce-1.0.0.jar'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/pavani-07-red/HibernateBeautyECommerce.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit '**\\target\\surefire-reports\\*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
                archiveArtifacts artifacts: "target\\${JAR_NAME}", fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Build, test, and package completed successfully!'
        }
        failure {
            echo 'Build or test failed!'
        }
    }
}
