pipeline {
    agent any
    triggers { pollSCM('* * * * *') }

    stages {
        stage('Checkout') {
            steps {
                // Fetch latest spring-clinic git repo
                git branch: 'main', url: 'https://github.com/eitancj/course3-jenkins-gs-spring-petclinic.git'
            }
        }
        stage('Build') {
            steps {
                // Avoid JaCoCo reporting error by runnin clean-verify
                //sh './mvnw -Dtest=\\!VetTests clean verify'

                // Create JAR file
                sh './mvnw -Dtest=\\!VetTests clean compile'
            }
        }
    }
    
    post {
        // always collect JUnit test results
        always {
            //echo "\nALWAYYYYYYS!\n"
            junit '**/target/surefire-reports/TEST-*.xml'
        }
        // following an unsuccessful build, send a mail using mailhog
        unsuccessful {
            emailext body: 'Build $BUILD_DISPLAY_NAME failed for job $JOB_NAME', \
            subject: 'Jenkins: error in job!', to: 'dmpumve341@oxomail.org', \
            recipientProviders: [culprits(), brokenBuildSuspects()], from: 'Jim'
        }
        // following successful build, archive the JAR file
        success {
            //archiveArtifacts 'target/*.jar'
            echo "\nJob Succeeded!\n"
        }
    }
}