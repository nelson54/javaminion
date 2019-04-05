pipeline {
  agent any

  options {
    ansiColor('xterm')
  }

  stages {
    stage('Clean') {
      steps {
        ansiblePlaybook(playbook: '/var/jenkins_home/workspace/dominion-playbooks_master/stages/clean.yml', colorized: true)
      }
    }

    stage('Clone') {
      steps {
        ansiblePlaybook(playbook: '/var/jenkins_home/workspace/dominion-playbooks_master/stages/clone.yml', colorized: true)
      }
    }

    stage('Build Docker') {
      steps {
        ansiblePlaybook(playbook: '/var/jenkins_home/workspace/dominion-playbooks_master/stages/build.yml', colorized: true)
      }
    }

    stage('Build Java') {
      steps {
        ansiblePlaybook(playbook: '/var/jenkins_home/workspace/dominion-playbooks_master/stages/build-java.yml', colorized: true)
      }
    }

    stage('Checkstyle') {
      when {
        expression { env.BRANCH_NAME == 'production' }
      }

      steps {
        sh 'mkdir ./archives/'
        sh 'mkdir ./archives/checkstyle'

        ansiblePlaybook(
                playbook: "/var/jenkins_home/workspace/dominion-playbooks_master/stages/checkstyle.yml",
                colorized: true,
                extraVars: [ base_dir: pwd() ]
        )

        sh 'tar -zxvf archives/*/root/archives/checkstyle.tar.gz -C ./archives/checkstyle'

        publishHTML(target: [
                allowMissing         : false,
                alwaysLinkToLastBuild: false,
                keepAll              : true,
                reportDir            : 'archives/checkstyle',
                reportFiles          : 'main.html',
                reportName           : 'Checkstyle'
        ])

        recordIssues (
                enabledForFailure: false,
                aggregatingResults : false,
                tool: checkStyle(pattern: 'archives/checkstyle/main.xml')
        )
      }
    }

    stage('Javadoc') {
      when {
        expression { env.BRANCH_NAME == 'production' }
      }

      steps {
        ansiblePlaybook(
                playbook: "/var/jenkins_home/workspace/dominion-playbooks_master/stages/javadoc.yml",
                colorized: true,
                extraVars: [ base_dir: pwd() ]
        )
        sh 'mkdir ./archives/javadoc'
        sh 'tar -zxvf archives/*/root/archives/javadoc.tar.gz  -C ./archives/javadoc'

        publishHTML(target: [
                allowMissing         : false,
                alwaysLinkToLastBuild: false,
                keepAll              : true,
                reportDir            : 'archives/javadoc',
                reportFiles          : 'index.html',
                reportName           : "Javadoc"
        ])
      }
    }

    stage('Spotbugs') {
      when {
        expression { env.BRANCH_NAME == 'production' }
      }

      steps {
        ansiblePlaybook(
                playbook: "/var/jenkins_home/workspace/dominion-playbooks_master/stages/spotbugs.yml",
                colorized: true,
                extraVars: [ base_dir: pwd() ]
        )

        sh 'mkdir ./archives/spotbugs'
        sh 'tar -zxvf archives/*/root/archives/spotbugs.tar.gz -C ./archives/spotbugs'

        recordIssues (
                enabledForFailure: false,
                aggregatingResults : false,
                tool: spotBugs(pattern: 'archives/spotbugs/main.xml')
        )
      }
    }

    stage('Test') {
      when {
        expression { env.BRANCH_NAME == 'production' }
      }

      steps {
        ansiblePlaybook(
                playbook: "/var/jenkins_home/workspace/dominion-playbooks_master/stages/test.yml",
                colorized: true,
                extraVars: [ base_dir: pwd() ]
        )

        sh 'tar -zxvf ../archives/*/root/archives/test-results.tar.gz'
        sh 'tar -zxvf ../archives/*/root/archives/test-reports.tar.gz'

        sh 'touch -c ./test/*'

        archive 'tests/**/*.html'
        junit 'test/TEST-*.xml'
      }
    }

    stage('Jacoco Test Coverage') {
      when {
        expression { env.BRANCH_NAME == 'production' }
      }

      steps {
        ansiblePlaybook(
                playbook: "/var/jenkins_home/workspace/dominion-playbooks_master/stages/jacoco.yml",
                colorized: true,
                extraVars: [ base_dir: pwd() ]
        )

        sh 'mkdir ./archives/jacoco'
        sh 'tar -zxvf archives/*/root/archives/jacoco.tar.gz -C ./archives/jacoco'

        publishHTML(target: [
                allowMissing         : false,
                alwaysLinkToLastBuild: true,
                keepAll              : true,
                reportDir            : 'archives/jacoco/test/html/',
                reportFiles          : 'index.html',
                reportName           : 'Test Coverage'
        ])

        publishCoverage adapters: [jacocoAdapter('archives/jacoco/test/jacocoTestReport.xml')]
      }
    }

    stage('Copy Jar') {
      steps {
        ansiblePlaybook(playbook: '/var/jenkins_home/workspace/dominion-playbooks_master/stages/jar.yml', colorized: true)
      }
    }

    stage('Deploy') {
      steps {
        ansiblePlaybook(playbook: '/var/jenkins_home/workspace/dominion-playbooks_master/stages/start.yml', colorized: true)
      }
    }

    stage('Cleanup') {
      steps {
        sh 'rm -Rf archives/'
        sh 'rm -Rf test/'
        sh 'rm -Rf tests/'
      }
    }

    stage('Waiting for server') {
      steps {
        waitUntil {
          def r = sh 'timeout 300 bash -c \'while [[ "$(curl -s -o /dev/null -w \'\'%{http_code}\'\' 206.189.224.80)" != "200" ]]; do sleep 1; done;\' || false'
          return (r == 0)
        }
      }
    }
  }
}
