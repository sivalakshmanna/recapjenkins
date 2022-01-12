pipeline{
    agent any
    environment {
        BRANCH = "${env.BRANCH_NAME}"
    }
    stages{
        stage("chekout code"){
            steps{
                println "clone our code to our repository"
                sh "ls -l"
                sh "ls -lart ./*"
                git branch: "${BRANCH_NAME}",
                url: 'https://github.com/KuruvaSomaSekhar/boxfuse-sample-java-war-hello.git'
                

            }
        }
        stage("build code"){
            steps{
                println "mvn clean package"
                sh "mvn clean package"
                sh "ls -l target/"
            }
        }
        stage("upload artifacts to s3"){
            steps{
                println "uploading artifacts to s3 bucket"
                sh "echo $BUILD_NUMBER"
               sh "aws s3 cp target/hello-${BUILD_NUMBER}.war s3://sivabandela/${BRANCH}/${BUILD_NUMBER}/"
            }
        }
    }
}