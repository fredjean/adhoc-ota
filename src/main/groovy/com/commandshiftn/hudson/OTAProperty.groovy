package com.commandshiftn.hudson

import hudson.model.AbstractProject
import hudson.model.JobProperty
import hudson.model.JobPropertyDescriptor
import net.sf.json.JSONObject
import org.kohsuke.stapler.StaplerRequest
import static hudson.Util.fixEmptyAndTrim
import org.kohsuke.stapler.DataBoundConstructor
import hudson.Extension

/**
 * Created by IntelliJ IDEA.
 * User: fjean
 * Date: 12/28/10
 * Time: 10:56 PM
 * To change this template use File | Settings | File Templates.
 */
class OTAProperty extends JobProperty<AbstractProject<?, ?>> {
  def appName
  def projectDirectory

  @DataBoundConstructor
  OTAProperty(String appName, String projectDirectory) {
    this.appName = appName
    this.projectDirectory = projectDirectory
  }
}

@Extension
class OTAPropertyDescriptor extends JobPropertyDescriptor {
  def xcRunPath = "/usr/bin/xcrun"
  def xcodebuildPath = "/usr/bin/xcodebuild"
  def agvtoolPath = "/usr/bin/agvtool"

  OTAPropertyDescriptor() {
    super(OTAProperty.class)
    load()
  }

  @Override
  String getDisplayName() {
    "AdHoc Over The Air Deployment"
  }

  @Override
  boolean configure(StaplerRequest req, JSONObject json) {
    xcRunPath = fixEmptyAndTrim(json.getString("xcRunPath"))
    xcodebuildPath = fixEmptyAndTrim(json.getString("xcodebuildPath"))
    agvtoolPath = fixEmptyAndTrim(json.getString("agvtoolPath"))
    save()
    true
  }
}