pipeline {
  agent any

  stages {
    stage('Clean') {
      steps {
        sh 'ansible-playbook ./playbooks/stages/clean.yml -v'
      }
    }
    stage('Clone') {
      steps {
        sh 'ansible-playbook ./playbooks/stages/clone.yml -v'
      }
    }
    stage('Build') {
      steps {
            sh 'ansible-playbook ./playbooks/stages/build.yml -v'
      }
    }
    stage('Test') {
      steps {
            sh 'ansible-playbook ./playbooks/stages/test.yml -v'
      }
    }
    stage('Start') {
      steps {
            sh 'ansible-playbook ./playbooks/stages/start.yml'
      }
    }
  }
}
