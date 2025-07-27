SUMMARY = "Push Notifications that work with just about every platform!"
HOMEPAGE = "https://github.com/caronc/apprise"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=74aac17862618045268cd493914a5b51"

DEPENDS = "python3-babel-native"

RDEPENDS:${PN} = "python3-pyyaml python3-markdown python3-click"

SRC_URI[md5sum] = "65f4f04411c6697ef776dc7fe03297aa"
SRC_URI[sha256sum] = "f583667ea35b8899cd46318c6cb26f0faf6a4605b119174c2523a012590c65a6"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
