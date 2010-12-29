package com.commandshiftn.hudson

import hudson.tasks.Builder
import org.kohsuke.stapler.DataBoundConstructor
import hudson.model.AbstractBuild
import hudson.Launcher
import hudson.model.BuildListener
import hudson.model.Descriptor

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
  def appName = "TBD"

  @DataBoundConstructor
  AdHocOTABuilder(String configuration, String sdk, String appName, String provisioningProfilePath) {
    this.configuration = configuration
    this.sdk = sdk
    this.appName = appName
    this.provisioningProfilePath = provisioningProfilePath
  }

  @Override
  boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) {
    envs = build.getEnvironment(listener)
    projectRoot = build.project.workspace
    projectBuildDir = "${projectRoot}/build/${configuration}-${sdk}"

    launcher.launch().envs(envs).stdout(listener).pwd(projectRoot).cmds(descriptor.xcRunPath, "-sdk", sdk, "PackageApplication", "-v",
            "${projectBuildDir}/${appName}.app", "-o", "${projectBuildDir}/${appName}.ipa",
            "--sign", codeSigningIdentity, "--embed", provisioningProfilePath).join()
  }

  @Override
  Descriptor<Builder> getDescriptor() {
    return super.descriptor
  }
}
