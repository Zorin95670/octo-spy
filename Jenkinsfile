#!groovy

@Library("jenkins-shared-libraries")
import com.arkena.jenkins.JenkinsHelper

def jenkinsHelper = new JenkinsHelper()

Boolean isNotTriggeredByTimer = jenkinsHelper.triggeredBy() != "TIMER"
// Will we build the snapshot package or not? This is initialized to false.
Boolean SHOULD_PACKAGE = false

jenkinsHelper.prepareJob()

def prepareDB() {
    stage("Prepare tests") {
        sh("""
            export TS=\$(date +%s)
            export DBNAME=test_octo_db_\${TS}_${BUILD_NUMBER}
            export DBUSER=test_octo_user_\${TS}_${BUILD_NUMBER}
            sed -i "s/octo_db/\${DBNAME}/g" ./octo-db/scripts/*
            sed -i -E "/_locks/!s/ octo/ \${DBUSER}/g" ./octo-db/scripts/*
            sed -i -E "/_locks/!s/ octo;/ \${DBUSER}/g" ./octo-db/scripts/*

            sed -i "s/octo_db/\${DBNAME}/g ; s/localhost/4tests.ci.c4m.qa.arkena.com/g; s/username=.*/username=\${DBUSER}/g; s/password=.*/password=password/g" ./src/test/resources/application.properties
            sed -i "s/octo_db/\${DBNAME}/g ; s/localhost/4tests.ci.c4m.qa.arkena.com/g; s/:octo}/:\${DBUSER}}/g;" ./src/test/resources/application-context.xml

            psql -h 4tests.ci.c4m.qa.arkena.com -U postgres \$(find ./octo-db/scripts/ -name "*.sql"  ! -name "*clean*.sql" -print | sort -n | sed  's/\\.\\//-f /')
        """)
    }
}

def cleanDB() {
    stage("Clean DB") {
        sh("""
            psql -h 4tests.ci.c4m.qa.arkena.com -U postgres \$(find ./octo-db/scripts/ -name "*clean*.sql" -print | sort -n | sed  's/\\.\\//-f /')
        """)
    }
}

if (env.JOB_NAME.startsWith("releasemb-")) {
    node("stretch") {
        ansiColor("xterm") {
            wrap([$class: "MesosSingleUseSlave"]) {
                jenkinsHelper.checkout(true, "postgresql-client")

                checkout([
                    $class: "GitSCM",
                    branches: [[name: "master"]],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [
                        [
                            $class: "RelativeTargetDirectory",
                            relativeTargetDir: "octo-db"
                        ],
                        [
                            $class: "SubmoduleOption",
                            disableSubmodules: false,
                            parentCredentials: true,
                            recursiveSubmodules: true,
                            reference: "",
                            trackingSubmodules: false
                        ]
                    ],
                    submoduleCfg: [],
                    userRemoteConfigs: [[
                        credentialsId: jenkinsHelper.JENKINS_CREDENTIALS_ID,
                        url: "git@git.arkena.net:vmoittie/octo-db.git"
                    ]]
                ])

                PKG_VERSION = sh(returnStdout: true, script: "echo -n \$(dpkg-parsechangelog -S Version)")

                prepareDB()

                stage("Build") {
                    withMaven(maven: "maven", jdk: jenkinsHelper.getJavaFromPom(), mavenOpts: "-XX:MaxPermSize=512m") {
                        MVN_VERSION = jenkinsHelper.getMvnVersion()
                        sh("mvn clean install")
                    }
                }

                cleanDB()

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
                    jenkinsHelper.checkout()
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
                jenkinsHelper.checkout(true, "postgresql-client")

                checkout([
                    $class: "GitSCM",
                    branches: [[name: "master"]],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [
                        [
                            $class: "RelativeTargetDirectory",
                            relativeTargetDir: "octo-db"
                        ],
                        [
                            $class: "SubmoduleOption",
                            disableSubmodules: false,
                            parentCredentials: true,
                            recursiveSubmodules: true,
                            reference: "",
                            trackingSubmodules: false
                        ]
                    ],
                    submoduleCfg: [],
                    userRemoteConfigs: [[
                        credentialsId: jenkinsHelper.JENKINS_CREDENTIALS_ID,
                        url: "git@git.arkena.net:vmoittie/octo-db.git"
                    ]]
                ])

                prepareDB()

                stage("Build") {
                    withMaven(maven: "maven", jdk: jenkinsHelper.getJavaFromPom(), mavenOpts: "-XX:MaxPermSize=512m") {
                        sh("mvn clean install")
                    }
                }

                cleanDB()

                jenkinsHelper.sonarMergeRequestAnalysis()
                stage("Results") {
                    jenkinsHelper.archivePublishJUNIT("**/surefire-reports/*.xml")
                    step([$class: "JacocoPublisher", execPattern: "**/**.exec", classPattern: "**/classes", sourcePattern: "**/src/main/java"])
                }
            }
        }
    }
} else if (env.JOB_NAME.startsWith("mut-")) {
    node("stretch") {
        jenkinsHelper.checkout()
        jenkinsHelper.runMutationTests("-DtargetClasses=com.octo*")
    }
}
