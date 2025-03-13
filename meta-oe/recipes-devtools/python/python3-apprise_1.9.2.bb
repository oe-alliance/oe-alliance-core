SUMMARY = "Push Notifications that work with just about every platform!"
HOMEPAGE = "https://github.com/caronc/apprise"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=74aac17862618045268cd493914a5b51"

DEPENDS = "python3-babel-native"

RDEPENDS:${PN} = "python3-pyyaml python3-markdown python3-click"

SRC_URI[md5sum] = "1982bb49e5543273e51005a408a588f0"
SRC_URI[sha256sum] = "290eb1217028dd5040802371494d70c1bcab907e6634a77c263489dbe4fd73a8"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
