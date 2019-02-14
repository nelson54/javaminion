pipeline {
  agent any
  
  stages {
    stage('') {
      steps {
        retry(count: 1)
      }
    }
    
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
