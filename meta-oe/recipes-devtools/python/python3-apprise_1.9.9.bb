SUMMARY = "Push Notifications that work with just about every platform!"
HOMEPAGE = "https://github.com/caronc/apprise"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d1700c468c259a17fcf7f51af33a4a2e"

DEPENDS = "python3-babel-native"

RDEPENDS:${PN} = "python3-pyyaml python3-markdown python3-click"

SRC_URI[md5sum] = "8573162cf39be8fe929469a9e8f6768a"
SRC_URI[sha256sum] = "fd622c0df16bdc79ed385539735573488cafe2405d25747e87eebd6b09b26012"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
