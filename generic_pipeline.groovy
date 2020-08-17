import hudson.tasks.test.AbstractTestResultAction
import hudson.model.Actionable

// add comment
// add comment
// add comment
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
        } // End Build Stage

        stage("clone a git dir")
        {
            steps
            {
               script
               {
                  common.clone_git_repository("${env.directory}","${env.credendtials}")
               }
            }
        } // End Clone Stage
	    
	stage("call a shell script that returns a boolean")
        {
           steps
           {
              script
              {
                 cmd_passed = common.check_passed("${env.directory}")
                 if ( !cmd_passed ){
							// this fails pipeline stage
                    error( "Service is NOT Ready" )
              }
           }
        } // End script stage
		
    } // End stages

} // Ends pipeline



