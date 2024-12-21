pipeline {
    agent any

    environment {
        // Define version using BUILD_NUMBER and an optional custom suffix
        NEW_VERSION = "8.2.${BUILD_NUMBER}"
    }


    stages {
        stage('Git Checkout') {
            steps {
                git 'https://github.com/HarshalPatil-Repo/Jenkins-Pipeline-to-deploy-application-into-EKS.git'
            }
        }
        stage('Version Change') {
            steps {
                sh """
                        mvn versions:set -DnewVersion=${NEW_VERSION}
                    """
                // Confirm the version has been updated
                sh "cat pom.xml"
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Nexus Artifact Upload') {
            steps {
                nexusArtifactUploader artifacts: [[artifactId: 'myweb', classifier: '', file: "target/myweb-${NEW_VERSION}.war", type: 'war']], credentialsId: 'nexus3', groupId: 'in.javahome', nexusUrl: '44.201.105.200:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'maven-releases', version: "${NEW_VERSION}"
            }
        }
        stage('Docker Build') {
            steps {
                sh'''
                    docker build . -t appimage
                '''
            }
        }
        stage('Docker Image Upload') {
            steps {
                sh'''
                    docker tag appimage harshalppatil98/appimage
                    docker push harshalppatil98/appimage
                '''
            }
        }
        stage('Kubernetes Deployment') {
            steps {
                sh'''
                    # Uncomment below line from 2nd build
                    #kubectl delete -f deployment.yaml
                    kubectl apply -f deployment.yaml
                '''
            }
        }
    }
}


