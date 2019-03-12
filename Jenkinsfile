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
        sh 'cd ./src/main/js; npm install'
        sh 'cd ./src/main/js/; bower install'
        sh 'rm -Rf /build/*'

        sh 'cd ./src/main/js/; mkdir dist'
        sh 'cd ./src/main/js/; mkdir ./dist/styles'
        sh 'cd ./src/main/js/; ./node_modules/grunt-cli/bin/grunt build'
        sh 'cd ./src/main/js/; sass ./app/styles/main.scss ./dist/styles/main.css'

        sh 'cd ./src/main/js/; cp -R ./app/images ./dist/images'
        sh 'cd ./src/main/js/; cp -R .tmp/concat/scripts ./dist/scripts'
        sh 'cd ./src/main/js/; tar -zcvf /build/front-end.tar.gz ./dist'
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

        archive '**/test/*.html'

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
