package com.commandshiftn.hudson

import hudson.Extension
import hudson.Launcher
import hudson.model.AbstractBuild
import hudson.model.AbstractProject
import hudson.model.BuildListener

import hudson.tasks.BuildStepDescriptor
import hudson.tasks.Builder
import org.kohsuke.stapler.DataBoundConstructor

/**
 * Created by IntelliJ IDEA.
 * User: fjean
 * Date: 12/28/10
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
class AdHocOTABuilder extends Builder {
  def configuration = "Release"
  def provisioningProfilePath
  def codeSigningIdentity = "iPhone Distribution"
  def sdk = "iphoneos"

  @DataBoundConstructor AdHocOTABuilder(String configuration, String provisioningProfilePath) {
    this.configuration = configuration
    this.provisioningProfilePath = provisioningProfilePath
  }

  @Override
  boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) {
    envs = build.getEnvironment(listener)
    otaProperty = build.project.properties[OTAProperty.class]
    projectRootDir = build.project.workspace
    projectBuildDir = "${projectRootDir}/build/${configuration}-${sdk}"
    xcRunPath = otaProperty?.descriptor?.xcRunPath
    appName = otaProperty?.appName

    listener.logger.println("Packaging ${appName} for AdHoc OTA Deployment.")

    rc = launcher.launch().envs(envs).stdout(listener).pwd(projectRootDir).cmds(xcRunPath, "-sdk", sdk, "PackageApplication", "-v",
            "${projectBuildDir}/${appName}.app", "-o",
            "${projectBuildDir}/${appName}-${build.number}.ipa",
            "--sign", codeSigningIdentity, "--embed", provisioningProfilePath).join()
    return rc == 0
  }
}

@Extension
final class AdHocOTABuilderDescriptor extends BuildStepDescriptor<Builder> {
  AdHocOTABuilderDescriptor() {
    super(AdHocOTABuilder.class)
    load()
  }

  @Override
  boolean isApplicable(Class<? extends AbstractProject> aClass) {
    true
  }

  @Override
  String getDisplayName() {
    "Package application for Adhoc OTA distribution."
  }
}
