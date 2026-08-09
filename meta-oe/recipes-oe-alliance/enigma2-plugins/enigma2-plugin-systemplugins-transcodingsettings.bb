DESCRIPTION = "Transcoding settings of your Enigma2 Box"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "enigma2-live555 \
    ${@bb.utils.contains("MACHINE_FEATURES", "transcoding", "virtual-transtreamproxy", "", d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "encoder", "gst-plugin-dreamsource", "", d)} \
"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/TranscodingSettings.git;protocol=https;branch=main"


RCONFLICTS:${PN} += " \
    enigma2-plugin-systemplugins-transcodingsetup \
    enigma2-plugin-systemplugins-multitranscodingsetup \
"

RREPLACES:${PN} += " \
    enigma2-plugin-systemplugins-transcodingsetup \
    enigma2-plugin-systemplugins-multitranscodingsetup \
"
