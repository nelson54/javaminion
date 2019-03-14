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

    stage('Build Node') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/node-build.yml')
      }
    }

    stage('Build Java') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/build.yml')
      }
    }

    stage('Test') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/test.yml')
        sh 'tar -zxvf archives/*/root/archives/test-results.tar.gz'
        sh 'tar -zxvf archives/*/root/archives/test-reports.tar.gz'

        archive 'test/*.html'

        junit 'TEST-*.xml'
      }
    }

    stage('Checkstyle') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/checkstyle.yml')
        sh 'mkdir ./archives/checkstyle'

        sh 'tar -zxvf archives/*/root/archives/checkstyle.tar.gz -C ./archives/checkstyle'

        publishHTML (target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir: 'archives/checkstyle',
                reportFiles: 'index.html',
                reportName: "Javadoc"
        ])
      }
    }

    stage('Javadoc') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/javadoc.yml')
        sh 'mkdir ./archives/javadoc'
        sh 'tar -zxvf archives/*/root/archives/javadoc.tar.gz  -C ./archives/javadoc'

        publishHTML (target: [
            allowMissing: false,
            alwaysLinkToLastBuild: false,
            keepAll: true,
            reportDir: 'archives/javadoc',
            reportFiles: 'index.html',
            reportName: "Javadoc"
          ])
      }
    }

    /*stage('Archive') {
      publishHTML([
        allowMissing: false,
        alwaysLinkToLastBuild: false,
        keepAll: true,
        reportDir: '/tests/test/',
        reportFiles: 'index.html',
        reportName: "Test report"
      ])
    }*/

    stage('Local Cleanup') {
      steps {
        sh 'rm -Rf archives/'
        sh 'rm -Rf test/'
        sh 'rm -Rf tests/'
      }
    }

    stage('Deploy') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/start.yml')
      }
    }
  }
}
