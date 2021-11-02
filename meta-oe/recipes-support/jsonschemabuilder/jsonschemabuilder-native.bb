DESCRIPTION = "dependency for Kodi"
SECTION = "console/utils"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=a8119afbffe1b8b7cd2e751480b80ad8"
DEPENDS = ""
PV = "1.0"

SRCREV = "ffb8504d08ec3d17560ba0d66ed935eb30b2cea0"
SRC_URI = "git://github.com/mx3L/JsonSchemaBuilder;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit native autotools
