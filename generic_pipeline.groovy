import hudson.tasks.test.AbstractTestResultAction
import hudson.model.Actionable


def common
pipeline
{
    agent {
        label "${env.HOSTNAME}"
    }
    stages
    {
        stage("Set  Build Name")
        {
            steps
            {
                script
                {
						  // load common functions
                    common = load "./common.groovy"
                    //constants
						  env.script_passed = true
                    env.directory = "/git/testing"
						             }
            }
        }

        stage("clone a git dir")
        {
            steps
            {
               script
               {
                  common.clone_standalone_dir("${env.directory}","${env.credendtials}")
               }
            }
        }
		stage("call a shell script that returns a boolean")
        {
           steps
           {
              script
              {
                 cmd_passed = common.check_passed("${env.directory}")
                 if ( !cmd_passed ){
							// this fails pipeline stage
                    error( "Bigsql Service is NOT Ready" )
              }
           }
        }




