pipeline {
  agent any

  stages {
    stage('ansible redeploy') {
      steps {
        container('terraform') {
          sh 'd-playbook ./playbooks/redeploy.yml'
        }
      }
    }
  }
}
