pipeline {
	agent any
	stages {
		stage('Build') {
			steps {
				sh 'echo "Build stage"'
				sh 'mvn build'
			}
		}
	}
	post {
		always {
			echo 'Always run line'
		}
		success {
			echo 'Success line'
		}
	}
}
