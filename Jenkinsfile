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

    stage('Build Docker') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/build.yml')
      }
    }

    stage('Build Jar') {
      steps {
        sh 'echo "build jar"'
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

    stage('Jacoco Test Coverage') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/jacoco.yml')
        sh 'mkdir ./archives/jacoco'

        sh 'tar -zxvf archives/*/root/archives/jacoco.tar.gz -C ./archives/jacoco'
      }
    }

    stage('Deploy') {
      steps {
        ansiblePlaybook(playbook: './playbooks/stages/start.yml')
      }
    }

    stage('Archiving') {
      steps {

        publishHTML(target: [
                allowMissing         : false,
                alwaysLinkToLastBuild: true,
                keepAll              : true,
                reportDir            : 'archives/jacoco/test/html/',
                reportFiles          : 'index.html',
                reportName           : "Test Coverage"
        ])

        publishHTML(target: [
                allowMissing         : false,
                alwaysLinkToLastBuild: false,
                keepAll              : true,
                reportDir            : 'archives/checkstyle',
                reportFiles          : 'main.html',
                reportName           : "Checkstyle"
        ])

        publishHTML(target: [
                allowMissing         : false,
                alwaysLinkToLastBuild: false,
                keepAll              : true,
                reportDir            : 'archives/javadoc',
                reportFiles          : 'index.html',
                reportName           : "Javadoc"
        ])

        publishCoverage adapters: [jacocoAdapter('archives/jacoco/test/jacocoTestReport.xml')]

        recordIssues (
                enabledForFailure: false,
                aggregatingResults : false,
                tool: checkStyle(pattern: 'archives/checkstyle/main.xml')
        )

        recordIssues (
          enabledForFailure: false,
          aggregatingResults : false,
          tool: spotBugs(pattern: 'archives/spotbugs/main.xml')
        )
      }
    }
  }

  post {
    always {
      sh 'rm -Rf archives/'
      sh 'rm -Rf test/'
      sh 'rm -Rf tests/'
    }
  }
}
