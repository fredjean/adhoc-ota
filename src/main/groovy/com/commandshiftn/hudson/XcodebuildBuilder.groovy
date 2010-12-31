package com.commandshiftn.hudson

import hudson.Extension
import hudson.model.AbstractProject

import hudson.tasks.BuildStepDescriptor
import hudson.tasks.Builder
import org.kohsuke.stapler.DataBoundConstructor
import hudson.model.AbstractBuild
import hudson.Launcher
import hudson.model.BuildListener

/**
 * Created by IntelliJ IDEA.
 * User: fjean
 * Date: 12/28/10
 * Time: 10:27 PM
 * To change this template use File | Settings | File Templates.
 */
class XcodebuildBuilder extends Builder {
  def configuration
  def target
  def sdk

  @DataBoundConstructor
  XcodebuildBuilder(String configuration, String target, String sdk) {
    this.configuration = configuration
    this.target = target
    this.sdk = sdk
  }

  @Override
  boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) {
    envs = build.getEnvironment(listener)
    otaProperty = build.project.properties[OTAProperty.class]
    projectRootDir = "${build.workspace}/${otaProperty.projectDirectory}"
    xcodebuildPath = otaProperty?.descriptor?.xcodebuildPath

    cmds = [xcodebuildPath]
    if (target) {
      cmds << "-target" << target
    }
    if (sdk) {
      cmds << "-sdk" << sdk
    }
    if (configuration) {
      configuration << "-configuration" << configuration
    }

    rc = launcher.launch().envs(envs).pwd(projectRootDir).cmds(cmds).join()

    rc == 0
  }


}

/**
 * Created by IntelliJ IDEA.
 * User: fjean
 * Date: 12/28/10
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */

@Extension
class XcodebuildBuilderDescriptor extends BuildStepDescriptor<Builder> {

  XcodebuildBuilderDescriptor() {
    super(XcodebuildBuilder.class)
    load()
  }

  @Override
  boolean isApplicable(Class<? extends AbstractProject> aClass) {
    true
  }

  @Override
  String getDisplayName() {
    "Xcode Builder"
  }
}
