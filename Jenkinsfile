pipeline {
  agent any

  stages {
    stage('Clean') {
      steps {
        sh 'ansible-playbook -vvv ./playbooks/stages/clean.yml'
      }
    }
    stage('Clone') {
      steps {
        sh 'ansible-playbook -vvv ./playbooks/stages/clone.yml'
      }
    }
    stage('Build') {
      steps {
        sh 'ansible-playbook -vvv ./playbooks/stages/build.yml'
      }
    }
    stage('Test') {
      steps {
        sh 'ansible-playbook -vvv ./playbooks/stages/test.yml'
      }
    }
    stage('Start') {
      steps {
        sh 'ansible-playbook ./playbooks/stages/start.yml'
      }
    }
  }
}
