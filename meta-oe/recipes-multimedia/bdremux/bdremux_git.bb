SUMMARY = "bdremux - a blu-ray movie stream remuxer"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=374d7e305a3d05bc98bec47c241f02af"
DEPENDS = "gstreamer gst-plugins-base"
PR = "r2"

SRCREV = "0e79ef388600acca139ee4f48ccfe02e5adcbc4a"

inherit autotools opendreambox-git
