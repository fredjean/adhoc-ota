package com.commandshiftn.hudson

import hudson.Extension
import hudson.model.AbstractProject
import hudson.model.Descriptor
import hudson.tasks.BuildStepDescriptor
import hudson.tasks.Builder
import org.kohsuke.stapler.DataBoundConstructor

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
  Descriptor<Builder> getDescriptor() {
    super.descriptor
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
