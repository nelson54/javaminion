pipeline {
  agent any

  stages {
    stage('ansible redeploy') {
      steps {
        container('terraform') {
          sh 'ansible-playbook ./playbooks/redeploy.yml'
        }
      }
    }
  }
}
