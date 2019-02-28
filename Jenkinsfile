pipeline {
  agent any

  stages {
    stage('ansible redeploy') {
      steps {
        sh 'ansible-playbook ./playbooks/stages/clean.yml'
        sh 'ansible-playbook ./playbooks/stages/clone.yml'
        sh 'ansible-playbook ./playbooks/stages/build.yml'
        sh 'ansible-playbook ./playbooks/stages/test.yml'
        sh 'ansible-playbook ./playbooks/stages/start.yml'
      }
    }
  }
}
