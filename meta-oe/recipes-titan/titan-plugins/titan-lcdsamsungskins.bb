SUMMARY = "meta file for Titan LCD Samsung Skins"
inherit packagegroup

require conf/license/license-gplv2.inc

DEPENDS = "\
  titan-plugin-lcdsamsungskins-channel.analog.uhr \
  titan-plugin-lcdsamsungskins-channel.digital.uhr.gelb \
  titan-plugin-lcdsamsungskins-channel.digital.uhr.gelb.mod \
  titan-plugin-lcdsamsungskins-channel.digital.uhr.trikots \
  titan-plugin-lcdsamsungskins-digitaluhr.blau \
  titan-plugin-lcdsamsungskins-holzuhr.standby \
"

PR = "r0"
