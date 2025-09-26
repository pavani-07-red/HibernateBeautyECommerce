 pipeline {
    agent any

    environment {
        JAR_NAME = 'HibernateBeautyECommerce-1.0.0.jar'
        APP_PATH = 'C:\\Users\\ARAVIND REDDY\\eclipse-workspace\\HibernateBeautyECommerce\\target\\'
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

        stage('Run Locally') {
            steps {
                echo 'Stopping previous HibernateBeautyECommerce app if running...'

                bat """
                powershell -Command "
                \$processes = Get-CimInstance Win32_Process | Where-Object { \$_.'CommandLine' -like '*${JAR_NAME}*' };
                foreach (\$p in \$processes) { Stop-Process -Id \$p.ProcessId -Force }
                "
                """

                bat "start cmd /c java -jar ${APP_PATH}${JAR_NAME}"
            }
        }
    }

    post {
        success {
            echo 'Build, test, and local run completed successfully!'
        }
        failure {
            echo 'Build or test failed!'
        }
    }
}


