pipeline {
  agent any
  
  stages { 
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
  }
}
