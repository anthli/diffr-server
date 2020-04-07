pipeline {
  agent any

  stages {
    stage("Clone source") {
      steps {
        checkout([
          $class: "GitSCM",
          branches: [[name: "master"]],
          extensions: [[$class: "WipeWorkspace"]],
          userRemoteConfigs: [
            [
              credentialsId: "a981e4af-b2bf-489c-aa3f-2871d663d60c",
              url: "https://github.com/anthli/diffr-server"
            ]
          ]
        ])
      }
    }

    stage("Gradle Clean and Assemble") {
      steps {
        if (isUnix()) {
          sh "./gradlew clean assemble"
        }
        else {
          bat "gradlew clean assemble"
        }
      }
    }

    stage("Run Tests") {
      steps {
        if (isUnix()) {
          sh "./gradlew test"
        }
        else {
          bat "gradlew test"
        }
      }
    }

    post {
      success {
        setBuildStatus("Build succeeded", "SUCCESS")
      }

      failure {
        setBuildStatus("Build failed", "FAILURE")
      }
    }
  }
}

def repoUrl = scm.getUserRemoteConfigs()[0].getUrl()

void setBuildStatus(String message, String state) {
  step([
    $class: "GitHubCommitStatusSetter",
    reposSource: [
      $class: "ManuallyEnteredRepositorySource",
      url: repoUrl
    ],
    contextSource: [
      $class: "ManuallyEnteredCommitContextSource",
      context: "ci/jenkins/build-status"
    ],
    errorHandlers: [
      [
        $class: "ChangingBuildStatusErrorHandler",
        result: "UNSTABLE"
      ]
    ],
    statusResultSource: [
      $class: "ConditionalStatusResultSource",
      results: [
        [
          $class: "AnyBuildResult",
          message: message,
          state: state
        ]
      ]
    ]
  ])
}