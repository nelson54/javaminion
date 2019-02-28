pipeline {
  agent any

  stages {
    stage('ansible redeploy') {
      steps {
        sh 'ansible-playbook ./playbooks/redeploy.yml'
      }
    }
  }
}
