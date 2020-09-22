node {
    //agent any

    def app

    stage('Prepare') {

        git 'https://github.com/liangguo/javademo.git'

    }

    stage('Build') {
        sh "./mvnw clean package"
    }

    stage('Unit Test') {
        sh "./mvnw test"
    }

    stage('Build image') {
        app= docker.build "liangguo/javademo"
    }

    stage('Push image') {
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
            app.push("${env.BUILD_NUMBER}")
        //    app.push("latest")
        }
    }

    stage('Deploy javademo-img to k8s') {
        sh "kubectl apply -f javademo-img.yml"
    }

    stage('Deploy javademo to k8s') {
        //sh '''
        //    imgip=$(kubectl get svc javademo-img -n javademo |grep javademo-img|awk '{print $4;}')
        //    sed -e 's/BUILD/${env.BUILD_NUMBER}/' javademo.yml |kubectl apply -f -
        //'''
        IMGURL = sh (
            script: 'kubectl get svc javademo-img -n javademo |grep javademo-img|awk \'{print $4;}\'',
            returnStdout: true
        ).trim()
        sh "sed -e 's/BUILD/${env.BUILD_NUMBER}/' -e 's/URL/$(IMGURL)/' javademo.yml|kubectl apply -f -"
    }

    stage('User test 1: url exist') {
        sh '''
            javademoip=$(kubectl get svc javademo -n javademo |grep javademo|awk '{print $4;}')
            response=$(curl -s -o /dev/null -w "%{http_code}\n" http://${javademoip}/demo/ping)
            if [ "$response" != "200" ];
            then
                exit 1
            fi
        '''
    }

    stage('User test 2: return page contains javademo img ip') {
        sh '''
            imgip=$(kubectl get svc javademo-img -n javademo |grep javademo-img|awk '{print $4;}')
            javademoip=$(kubectl get svc javademo -n javademo |grep javademo|awk '{print $4;}')
            curl http://${javademoip}/hello | grep $imgip
            exit $?
        '''
    }

    // If test fail, latest tag should not be pushed to dockerhub. 
    stage('Push latest tag') {
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
            app.push("latest")
        }
    }

    stage('Remove deployed resource') {
        sh "kubectl delete namespace javademo"
    }


}