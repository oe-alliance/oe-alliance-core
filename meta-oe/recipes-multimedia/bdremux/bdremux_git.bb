SUMMARY = "bdremux - a blu-ray movie stream remuxer"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=374d7e305a3d05bc98bec47c241f02af"
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

SRCREV = "8bf413af5673ca77dee4b077562176eddc15e668"
SRC_URI_append = ";branch=gst_1.0"

inherit autotools pkgconfig opendreambox-git
