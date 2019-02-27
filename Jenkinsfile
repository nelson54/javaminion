pipeline {
  agent any
  
  /*stages {
    stage('Compile Java') {
      steps {
        sh './gradlew compileJava'
      }
    }
    
    stage('Test') {
      steps {
        sh './gradlew test'
      }
    }
  }*/

  stages {
      stage('Example Build') {
          steps {
              sh 'terraform apply   -var "do_token=465994833809b2c66659f6c71bd1c922080046b8da16cc53a56da15edd3bbdf6"   -var "pub_key=$HOME/.ssh/id_rsa.pub"   -var "pvt_key=$HOME/.ssh/id_rsa"   -var "ssh_fingerprint=ce:d5:0d:f3:53:6f:3d:03:07:b4:67:42:a3:e3:b4:d8"'
          }
      }
  }
}
