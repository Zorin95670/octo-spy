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

            echo "CREATE DATABASE \${DBNAME} ENCODING = 'UTF8';" > jenkins_init.sql
            echo "CREATE USER \${DBUSER} WITH ENCRYPTED PASSWORD 'password';" >> jenkins_init.sql
            echo "GRANT CONNECT ON DATABASE \${DBNAME} TO \${DBUSER};" >> jenkins_init.sql
            echo "GRANT USAGE ON SCHEMA public TO \${DBUSER};" >> jenkins_init.sql
            echo "GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO \${DBUSER};" >> jenkins_init.sql
            echo "GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO \${DBUSER};" >> jenkins_init.sql

            echo "DROP DATABASE IF EXISTS \${DBNAME};" > jenkins_clean.sql
            echo "DROP OWNED BY \${DBUSER};" >> jenkins_clean.sql
            echo "DROP ROLE \${DBUSER};" >> jenkins_clean.sql

            echo "flyway.url=jdbc:postgresql://4tests.common.qa.tvvideoms.com:5432/\${DBNAME}" > flyway.conf
            echo "flyway.user=\${DBUSER}" >> flyway.conf
            echo "flyway.password=password" >> flyway.conf



            sed -i "s/octo_db/\${DBNAME}/g ; s/octo;/\${DBUSER};/g;" ./src/main/resources/db/**/*
            sed -i "s/octo_db/\${DBNAME}/g ; s/localhost/4tests.common.qa.tvvideoms.com/g; s/:octo}/:\${DBUSER}}/g;" ./src/test/resources/application-context.xml

            psql -h 4tests.common.qa.tvvideoms.com -U postgres -f ./jenkins_init.sql
        """)
    }
}

def build(jenkinsHelper) {
    stage("Build") {
        withMaven(maven: "maven", jdk: jenkinsHelper.getJavaFromPom(), mavenOpts: "-XX:MaxPermSize=512m") {
            MVN_VERSION = jenkinsHelper.getMvnVersion()
            sh("""
              mvn compile
              mvn flyway:clean -Dflyway.configFiles=./flyway.conf
              mvn flyway:migrate -Dflyway.configFiles=./flyway.conf
              mvn clean install
              mvn flyway:clean -Dflyway.configFiles=./flyway.conf
              psql -h 4tests.common.qa.tvvideoms.com -U postgres -f ./jenkins_clean.sql
            """)
        }
    }
}

if (env.JOB_NAME.startsWith("releasemb-")) {
    node("stretch") {
        ansiColor("xterm") {
            wrap([$class: "MesosSingleUseSlave"]) {
                jenkinsHelper.checkout(true, "postgresql-client")

                PKG_VERSION = sh(returnStdout: true, script: "echo -n \$(dpkg-parsechangelog -S Version)")

                prepareDB()

                build(jenkinsHelper)

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
} else if (currentBuild.projectName.startsWith("pr-")) {
    node("stretch") {
        wrap([$class: "MesosSingleUseSlave"]) {
            ansiColor("xterm") {
                jenkinsHelper.checkout(true, "postgresql-client")

                prepareDB()

                build(jenkinsHelper)

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
