pipeline {
    agent any
    triggers { pollSCM('* * * * *') } // poll github every minute
    // the actual git-repo url is defined in pipeline options in from the Jenkins console
    // from drop-down menu 'Pipeline script from SCM' under Definition under Pipeline

    stages {
        stage("Build") {
            steps {
                sh "./mvnw -Dtest=\\!VetTests clean package"
            }
        }
        
        stage("JUnit") {
            steps {
                junit '**/target/surefire-reports/TEST*.xml'
            }
        }
    }

    post {
        always {
            emailext body: "\n${currentBuild.absoluteUrl}",
                from: 'jenkins',
            	to: 'default@jenkins.mailhog', 
            	recipientProviders: [previous()], 
            	subject: "${currentBuild.currentResult}: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]"
        }
        success {
            archiveArtifacts '**/target/*.jar'
        }
    }
}
