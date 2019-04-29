#!groovy

@Library("jenkins-shared-libraries")
import com.arkena.jenkins.JenkinsHelper

def jenkinsHelper = new JenkinsHelper()

Boolean isNotTriggeredByTimer = jenkinsHelper.triggeredBy() != "TIMER"
// Will we build the snapshot package or not? This is initialized to false.
Boolean SHOULD_PACKAGE = false

jenkinsHelper.prepareJob()

if (env.JOB_NAME.startsWith("releasemb-")) {
    node("stretch") {
        ansiColor("xterm") {
            wrap([$class: "MesosSingleUseSlave"]) {
                jenkinsHelper.checkout(true)
                PKG_VERSION = sh(returnStdout: true, script: "echo -n \$(dpkg-parsechangelog -S Version)")
                stage("Build") {
                    withMaven(maven: "maven", jdk: jenkinsHelper.getJavaFromPom(), mavenOpts: "-XX:MaxPermSize=512m") {
                        MVN_VERSION = jenkinsHelper.getMvnVersion()
                        sh("mvn clean install")
                    }
                }
                SHOULD_PACKAGE = env.BRANCH_NAME == "master" && PKG_VERSION.contains("~dev") && MVN_VERSION.endsWith("SNAPSHOT") && isNotTriggeredByTimer
                jenkinsHelper.sonarAnalysis()
                stage("Publish results") {
                    jenkinsHelper.archivePublishJUNIT("**/surefire-reports/*.xml")
                    step([$class: "JacocoPublisher", execPattern: "**/**.exec", classPattern: "**/classes", sourcePattern: "**/src/main/java"])
                }
                jenkinsHelper.waitSonarQG()

                if(SHOULD_PACKAGE) {
                    stash(name: "workspace", useDefaultExcludes: false)
                    jenkinsHelper.artifactoryBuild("-DskipTests")
                }
            }
        }
    }
    if (currentBuild.result == "SUCCESS" && SHOULD_PACKAGE) {
        println("Development version detected, snapshot creation will start.")
        stretch_dsc_file = jenkinsHelper.generateDebianSourcePackage("snapshot", "stretch")
        stage('Build and publish for stretch') {
            jenkinsHelper.buildAndPublishDebianPackage(stretch_dsc_file, "stretch", ["qa-cw"])
        }

    } else {
        println("As it is NOT a development version, SNAPSHOT WON'T BE CREATED")
    }
} else if (currentBuild.projectName.startsWith("pck-")) {
    node("stretch") {
        wrap([$class: "MesosSingleUseSlave"]) {
            ansiColor('xterm') {
                sshagent([jenkinsHelper.JENKINS_CREDENTIALS_ID]) {
                    jenkinsHelper.checkout(true)
                    jenkinsHelper.artifactoryBuild()
                    if (currentBuild.result == null) {
                        currentBuild.result = "SUCCESS"
                    }
                    stage ("Stash everything") {
                        stash(name: "workspace", useDefaultExcludes: false)
                    }
                }
            }
        }
    }
    if (currentBuild.result == "SUCCESS") {
        def packageFor = ["qa-cw", "cw"]
        stretch_dsc_file = jenkinsHelper.generateDebianSourcePackage("build_dsc", "stretch")
        stage('Build and publish for stretch') {
             jenkinsHelper.buildAndPublishDebianPackage(stretch_dsc_file, "stretch", packageFor)
        }

    }
} else if (currentBuild.projectName.startsWith("pr-")) {
    node("stretch") {
        wrap([$class: "MesosSingleUseSlave"]) {
            ansiColor("xterm") {
                jenkinsHelper.checkout(true)
                stage("Build") {
                    withMaven(maven: "maven", jdk: jenkinsHelper.getJavaFromPom(), mavenOpts: "-XX:MaxPermSize=512m") {
                        sh("mvn clean install")
                    }
                }
                jenkinsHelper.sonarMergeRequestAnalysis()
                stage("Results") {
                    jenkinsHelper.archivePublishJUNIT("**/surefire-reports/*.xml")
                    step([$class: "JacocoPublisher", execPattern: "**/**.exec", classPattern: "**/classes", sourcePattern: "**/src/main/java"])
                }
            }
        }
    }
} else if (env.JOB_NAME.startsWith("mut-")) {
    node("maven") {
        jenkinsHelper.checkout()
        jenkinsHelper.runMutationTests()
    }
}
