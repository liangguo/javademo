node {
    //agent any

    def app

    stage('Prepare') {

        git 'https://github.com/liangguo/javademo.git'

    }

    stage('Build') {
        sh "./mvnw clean package"
    }

    stage('Build image') {
        app= docker.build "liangguo/javademo"
    }

    stage('Push image') {
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
            app.push("${env.BUILD_NUMBER}")
            app.push("latest")
        }
    }
}