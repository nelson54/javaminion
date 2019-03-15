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

        archive 'tests/**/*.html'

        junit 'test/TEST-*.xml'
      }
    }

    stage('Checkstyle') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/checkstyle.yml')
        sh 'mkdir ./archives/checkstyle'

        sh 'tar -zxvf archives/*/root/archives/checkstyle.tar.gz -C ./archives/checkstyle'
      }
    }

    stage('Javadoc') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/javadoc.yml')
        sh 'mkdir ./archives/javadoc'
        sh 'tar -zxvf archives/*/root/archives/javadoc.tar.gz  -C ./archives/javadoc'
      }
    }

    stage('Spotbugs') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/spotbugs.yml')
        sh 'mkdir ./archives/spotbugs'

        sh 'tar -zxvf archives/*/root/archives/spotbugs.tar.gz -C ./archives/spotbugs'
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

    stage('Deploy') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/start.yml')
      }
    }
  }

  post {
    always {
      publishHTML (target: [
              allowMissing: false,
              alwaysLinkToLastBuild: false,
              keepAll: true,
              reportDir: 'archives/checkstyle',
              reportFiles: 'index.html',
              reportName: "Javadoc"
      ])

      publishHTML (target: [
              allowMissing: false,
              alwaysLinkToLastBuild: false,
              keepAll: true,
              reportDir: 'archives/javadoc',
              reportFiles: 'index.html',
              reportName: "Javadoc"
      ])

      recordIssues enabledForFailure: false,
              tools: [[tool: [$class: 'Java']],
                      [tool: [$class: 'JavaDoc']]]

      recordIssues tools: [[tool: [$class: 'SpotBugs'], pattern: 'archives/spotbugs/main.xml']]

      sh 'rm -Rf archives/'
      sh 'rm -Rf test/'
      sh 'rm -Rf tests/'
    }
  }
}
