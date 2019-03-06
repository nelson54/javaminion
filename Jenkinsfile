pipeline {
  agent any

  options {
    ansiColor('xterm')
  }

  stages {
    stage('Clean') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/clean.yml')
      }
    }
    stage('Clone') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/clone.yml')
      }
    }
    stage('Build') {

      steps {
        ansiblePlaybook(playbook: './playbooks/stages/build.yml')
      }
    }
    stage('Test') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/test.yml')
        sh 'tar -zxvf archives/*/root/archives/test-results.tar.gz'
        sh 'tar -zxvf archives/*/root/archives/test-reports.tar.gz'

        archive '**/test/*.html'
        archive '**/packages/*.html'
        archive '**/classes/*.html'

        publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: '/tests/test/',
                        reportFiles: 'index.html',
                        reportName: "Test report"
                      ])

          junit '**/TEST-*.xml'
        }
      }
      stage('Local Cleanup') {
        steps {
          sh 'rm -Rf archives/'
          sh 'rm -Rf test/'
          sh 'rm -Rf tests/'
        }
      }
      stage('Start') {
        steps {
          ansiblePlaybook(playbook: './playbooks/stages/start.yml')
        }
      }
    }
  }