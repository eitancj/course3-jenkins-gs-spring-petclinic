pipeline {
    agent any
    triggers { pollSCM('* * * * *') } // poll github every minute
    // the actual git-repo url is defined in pipeline options in from the Jenkins console
    // from drop-down menu 'Pipeline script from SCM' under Definition under Pipeline

    stages {

        stage("build") {
            steps {
                sh "./mvnw -Dtest=\\!VetTests clean package"
            }
        }
        
        stage("jacoco") {
            steps {
                jacoco()
            }
        }
    }

    post {
        success {
            archiveArtifacts '**/target/*.jar'
        }
        success {
            junit '**/target/surefire-reports/TEST*.xml'
        }
        always {
            emailext body: "${env.BUILD_URL}\n${currentBuild.absoluteUrl}",
            	to: 'default@jenkins.mailhog', 
            	recipientProviders: [previous()], 
            	subject: "${currentBuild.currentResult}: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]"
        }
    }
}
